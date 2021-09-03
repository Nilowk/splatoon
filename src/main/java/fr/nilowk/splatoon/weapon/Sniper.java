package fr.nilowk.splatoon.weapon;

import fr.nilowk.splatoon.Main;
import fr.nilowk.splatoon.utils.Gstate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Sniper implements Listener {

    private Main instance;

    public Sniper(Main instance) {

        this.instance = instance;

    }

    @EventHandler
    public void chargeBow(EntityShootBowEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        if (event.getEntityType() == EntityType.PLAYER) {

            Player player = (Player) event.getEntity();

            if (event.getForce() < 1.0f) {

                event.setCancelled(true);
                return;

            }

            if (player.getExp() < 0.72f) {

                event.setCancelled(true);
                return;

            }

            player.setExp(player.getExp() - 0.72f);

            new BukkitRunnable() {

                @Override
                public void run() {

                    Block block = event.getProjectile().getLocation().getBlock().getRelative(BlockFace.DOWN);
                    while (block.getType() == Material.AIR) {

                        block = block.getRelative(BlockFace.DOWN);

                    }
                    if (block.getType() == Material.CYAN_TERRACOTTA) {

                        block.setType(Material.ORANGE_WOOL);

                    }
                }

            }.runTaskTimer(instance, 0 , 1);

        }

    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        if (event.getEntity() instanceof Arrow) {

            Arrow arrow = (Arrow) event.getEntity();

            if (arrow.getShooter() instanceof Player) {

                if (event.getHitEntity() != null) {

                    if (event.getHitEntity().getType() != EntityType.PLAYER) return;
                    Player hitPlayer = (Player) event.getHitEntity();

                    if (instance.getColor(hitPlayer) == instance.getOpo(instance.getColor((Player) arrow.getShooter()))) hitPlayer.setHealth(0.0);

                }

            }

        }

    }

}
