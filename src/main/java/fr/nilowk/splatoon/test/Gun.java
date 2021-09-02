package fr.nilowk.splatoon.test;

import fr.nilowk.splatoon.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Snowable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Gun implements Listener {

    private Main instance;

    public Gun(Main instance) {

        this.instance = instance;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        event.getPlayer().setLevel(0);

    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {

        if (event.getEntity() instanceof Snowball) {

            Snowball snow = (Snowball) event.getEntity();

            if (snow.getShooter() instanceof Player) {

                Player player = (Player) snow.getShooter();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getInventory().setItem(0, new ItemStack(Material.SNOWBALL, 1));
                    }
                }.runTaskLater(instance, 10);
                if (player.getExp() < 0.065f) {

                    event.setCancelled(true);
                    return;

                }

            }

        }

    }

    @EventHandler
    public void onShot(ProjectileHitEvent event) {

        if(event.getEntity() instanceof Snowball) {

            Snowball snow = (Snowball) event.getEntity();

            if(snow.getShooter() instanceof Player) {

                Player player = (Player) snow.getShooter();
                Block block = event.getHitBlock();

                if (block.getType() == Material.CYAN_TERRACOTTA || block.getType() == Material.ORANGE_WOOL && event.getHitBlockFace() == BlockFace.UP) {

                    block.setType(Material.ORANGE_WOOL);
                    player.setExp(player.getExp() - 0.013f);
                    if (block.getRelative(BlockFace.EAST).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.EAST).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.WEST).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.WEST).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.SOUTH).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.SOUTH).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.NORTH).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.NORTH).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }

                } else if (block.getType() == Material.CYAN_TERRACOTTA || block.getType() == Material.ORANGE_WOOL) {


                    block.setType(Material.ORANGE_WOOL);

                    if (block.getRelative(BlockFace.UP).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.UP).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.DOWN).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.DOWN).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.EAST).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.EAST).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.WEST).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.WEST).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.SOUTH).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.SOUTH).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }
                    if (block.getRelative(BlockFace.NORTH).getType() == Material.CYAN_TERRACOTTA) {

                        block.getRelative(BlockFace.NORTH).setType(Material.ORANGE_WOOL);
                        player.setExp(player.getExp() - 0.013f);

                    }

                }

            }

        }

    }

}
