package fr.nilowk.splatoon.weapon;

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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Gun implements Listener {

    private Main instance;

    public Gun(Main instance) {

        this.instance = instance;

    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {

        if (event.getEntity() instanceof Snowball) {

            Snowball snow = (Snowball) event.getEntity();

            if (snow.getShooter() instanceof Player) {

                Player player = (Player) snow.getShooter();
                if (!snow.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Liquidateur")) return;

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ItemStack it = new ItemStack(Material.SNOWBALL, 1);
                        ItemMeta im = it.getItemMeta();
                        im.setDisplayName("§4Liquidateur");
                        it.setItemMeta(im);
                        player.getInventory().setItem(0, it);
                    }
                }.runTaskLater(instance, 5);
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

                if (event.getHitEntity() != null) {

                    Player player = (Player) event.getHitEntity();
                    player.setHealth(player.getHealth() - 4.0);
                    return;

                }

                Player player = (Player) snow.getShooter();
                if (!snow.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Liquidateur")) return;
                Block block = event.getHitBlock();

                if (block.getType() == Material.CYAN_TERRACOTTA || block.getType() == instance.getOpo(instance.getColor(player))) {

                    if (instance.getColor(player) == Material.ORANGE_WOOL) {
                        if (block.getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block);
                            instance.getBlockToRegenBlue().remove(block);
                        } else {
                            instance.getBlockToRegenOrange().add(block);
                        }
                    } else {
                        if (block.getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block);
                            instance.getBlockToRegenBlue().remove(block);
                        } else {
                            instance.getBlockToRegenBlue().add(block);
                        }
                    }
                    block.setType(instance.getColor(player));
                    player.setExp(player.getExp() - 0.013f);

                }
                if (block.getRelative(BlockFace.EAST).getType() == Material.CYAN_TERRACOTTA || block.getRelative(BlockFace.EAST).getType() == instance.getOpo(instance.getColor(player))) {

                    if (instance.getColor(player) == Material.ORANGE_WOOL) {
                        if (block.getRelative(BlockFace.EAST).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.EAST));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.EAST));
                        } else {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.EAST));
                        }
                    } else {
                        if (block.getRelative(BlockFace.EAST).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.EAST));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.EAST));
                        } else {
                            instance.getBlockToRegenBlue().add(block.getRelative(BlockFace.EAST));
                        }
                    }
                    block.getRelative(BlockFace.EAST).setType(instance.getColor(player));
                    player.setExp(player.getExp() - 0.013f);

                }
                if (block.getRelative(BlockFace.WEST).getType() == Material.CYAN_TERRACOTTA || block.getRelative(BlockFace.WEST).getType() == instance.getOpo(instance.getColor(player))) {

                    if (instance.getColor(player) == Material.ORANGE_WOOL) {
                        if (block.getRelative(BlockFace.WEST).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.WEST));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.WEST));
                        } else {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.WEST));
                        }
                    } else {
                        if (block.getRelative(BlockFace.WEST).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.WEST));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.WEST));
                        } else {
                            instance.getBlockToRegenBlue().add(block.getRelative(BlockFace.WEST));
                        }
                    }
                    block.getRelative(BlockFace.WEST).setType(instance.getColor(player));
                    player.setExp(player.getExp() - 0.013f);

                }
                if (block.getRelative(BlockFace.SOUTH).getType() == Material.CYAN_TERRACOTTA || block.getRelative(BlockFace.SOUTH).getType() == instance.getOpo(instance.getColor(player))) {

                    if (instance.getColor(player) == Material.ORANGE_WOOL) {
                        if (block.getRelative(BlockFace.SOUTH).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.SOUTH));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.SOUTH));
                        } else {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.SOUTH));
                        }
                    } else {
                        if (block.getRelative(BlockFace.SOUTH).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.SOUTH));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.SOUTH));
                        } else {
                            instance.getBlockToRegenBlue().add(block.getRelative(BlockFace.SOUTH));
                        }
                    }
                    block.getRelative(BlockFace.SOUTH).setType(instance.getColor(player));
                    player.setExp(player.getExp() - 0.013f);

                }
                if (block.getRelative(BlockFace.NORTH).getType() == Material.CYAN_TERRACOTTA || block.getRelative(BlockFace.SOUTH).getType() == instance.getOpo(instance.getColor(player))) {

                    if (instance.getColor(player) == Material.ORANGE_WOOL) {
                        if (block.getRelative(BlockFace.NORTH).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.NORTH));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.NORTH));
                        } else {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.NORTH));
                        }
                    } else {
                        if (block.getRelative(BlockFace.NORTH).getType() != Material.CYAN_TERRACOTTA) {
                            instance.getBlockToRegenOrange().add(block.getRelative(BlockFace.NORTH));
                            instance.getBlockToRegenBlue().remove(block.getRelative(BlockFace.NORTH));
                        } else {
                            instance.getBlockToRegenBlue().add(block.getRelative(BlockFace.NORTH));
                        }
                    }
                    block.getRelative(BlockFace.NORTH).setType(instance.getColor(player));
                    player.setExp(player.getExp() - 0.013f);

                }

            }

        }

    }

}
