package fr.nilowk.splatoon.test;

import fr.nilowk.splatoon.Main;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Test implements Listener {

    private Main instance;
    private List<Player> list = new ArrayList<>();

    public Test(Main instance) {

        this.instance = instance;

    }

    @EventHandler
    public void playerJump(PlayerMoveEvent event) {

        if (isCalamar(event.getPlayer())) {

            if (event.getFrom().getY() != event.getTo().getY()){

                Entity squid = event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.SQUID);

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
    public void playerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        Block pb = player.getLocation().getBlock();

        if (pb.getRelative(BlockFace.DOWN).getType() == Material.ORANGE_WOOL) {

            if (isCalamar(player)) {

                BlockData fallingDustData = Material.ORANGE_WOOL.createBlockData();
                player.getWorld().spawnParticle(Particle.FALLING_DUST, player.getLocation().add(0, 0.1, 0), 1, fallingDustData);

            }

            if (player.isSneaking()) {

                if (!isCalamar(player)) {

                    player.setInvisible(true);
                    player.setCollidable(false);

                    new BukkitRunnable() {

                        @Override
                        public void run() {

                            if (isCalamar(player)) {

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
                    player.addPotionEffect(PotionEffectType.SPEED.createEffect(100000, 25));

                }

            } else {

                if (isCalamar(player)) {

                    player.setInvisible(false);
                    player.setCollidable(true);
                    player.removePotionEffect(PotionEffectType.SPEED);

                }

            }

        }

        if (pb.getRelative(BlockFace.DOWN).getType() != Material.ORANGE_WOOL && pb.getRelative(BlockFace.DOWN).getType() != Material.AIR) {

              if (isCalamar(player)) {

                  player.setInvisible(false);
                  player.setCollidable(true);
                  player.removePotionEffect(PotionEffectType.SPEED);

              }

        } else if (pb.getRelative(BlockFace.DOWN).getType() == Material.AIR) {



        }

    }

    @EventHandler
    public void onTakeDamage(EntityDamageEvent event) {

        if (event.getEntityType() == EntityType.PLAYER) {

            event.setCancelled(true);

        }

    }

    public boolean isCalamar(Player player) {

        if (player.isInvisible()) {

            return true;

        }
        return false;

    }

}
