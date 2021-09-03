package fr.nilowk.splatoon.listener;

import fr.nilowk.splatoon.utils.Gstate;
import fr.nilowk.splatoon.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager implements Listener {

    private Main instance;
    private FileConfiguration config;

    public GameManager(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SPECTATOR);
        event.setJoinMessage(config.getString("message.game-join").replace("{PLAYER}", player.getDisplayName()));
        player.teleport(instance.getBlueSpawn());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        Player player = event.getPlayer();
        event.setQuitMessage(config.getString("message.game-quit").replace("{PLAYER}", player.getDisplayName()));

    }

///////////// CALAMAR START

    @EventHandler
    public void playerJump(PlayerMoveEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        Player player = event.getPlayer();
        if (instance.isCalamar(player)) {

            if (event.getFrom().getY() != event.getTo().getY()){

                Entity squid = player.getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.SQUID);

                new BukkitRunnable() {

                    @Override
                    public void run() {

                        squid.remove();

                    }

                }.runTaskLater(instance, 1);

            }

        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;

        Player player = event.getPlayer();

        if (instance.getNoColor().contains(player)) return;

        if (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == instance.getOpo(instance.getColor(player))) {

            instance.getNoColor().add(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != instance.getOpo(instance.getColor(player))) {
                        instance.getNoColor().remove(player);
                        cancel();
                    }
                    player.setHealth(player.getHealth() - 2.0);
                }
            }.runTaskTimer(instance, 0, 10);

        }

    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        Player player = event.getPlayer();
        Block pb = player.getLocation().getBlock();

        if (pb.getRelative(BlockFace.DOWN).getType() == instance.getColor(player)) {

            if (instance.isCalamar(player)) {

                BlockData fallingDustData = instance.getColor(player).createBlockData();
                player.getWorld().spawnParticle(Particle.FALLING_DUST, player.getLocation().add(0, 0.1, 0), 1, fallingDustData);

            }

            if (player.isSneaking()) {

                if (!instance.isCalamar(player)) {

                    setCalamar(player, true);

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            if (instance.isCalamar(player)) {

                                if ((1 - event.getPlayer().getExp()) <= 0.0125f) {

                                    event.getPlayer().setExp(event.getPlayer().getExp() + (1 - event.getPlayer().getExp()));
                                    cancel();

                                } else {

                                    player.setExp(event.getPlayer().getExp() + 0.0125f);

                                }


                            } else {

                                cancel();

                            }

                        }

                    }.runTaskTimer(instance, 0, 1);

                }

            } else {

                if (instance.isCalamar(player)) {

                    setCalamar(player, false);

                }

            }

        }

        if (pb.getRelative(BlockFace.DOWN).getType() != instance.getColor(player) && pb.getRelative(BlockFace.DOWN).getType() != Material.AIR) {

            if (instance.isCalamar(player)) {

                setCalamar(player, false);

            }

        }

    }

    private void setCalamar(Player player, boolean active) {

        if (active) {

            player.setInvisible(true);
            player.setCollidable(false);
            player.addPotionEffect(PotionEffectType.SPEED.createEffect(100000, 25));

        } else {

            player.setInvisible(false);
            player.setCollidable(true);
            player.removePotionEffect(PotionEffectType.SPEED);

        }

    }

///////// CALAMAR END

    @EventHandler
    public void onTakeDamage(EntityDamageEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        if (event.getEntityType() == EntityType.PLAYER) {

            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.FALL) {

                event.setCancelled(true);

            }

        }

    }

}
