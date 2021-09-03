package fr.nilowk.splatoon.weapon;

import fr.nilowk.splatoon.Main;
import java.util.*;

import fr.nilowk.splatoon.utils.Gstate;
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

import java.util.ArrayList;

public class Gun implements Listener {

    private Main instance;

    public Gun(Main instance) {

        this.instance = instance;

    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        if (event.getEntity() instanceof Snowball) {

            Snowball snow = (Snowball) event.getEntity();

            if (snow.getShooter() instanceof Player) {

                if (!snow.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Liquidateur")) return;

                Player player = (Player) snow.getShooter();

                if (player.getExp() < 0.065f) {

                    event.setCancelled(true);
                    return;

                }

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

            }

        }

    }

    @EventHandler
    public void onShot(ProjectileHitEvent event) {

        if (!instance.isState(Gstate.PLAYING)) return;
        if(event.getEntity() instanceof Snowball) {

            Snowball snow = (Snowball) event.getEntity();

            if(snow.getShooter() instanceof Player) {

                if (!snow.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Liquidateur")) return;
                if (event.getHitEntity() != null) {

                    Player player = (Player) event.getHitEntity();
                    if (instance.getColor(player) == instance.getOpo(instance.getColor((Player) snow.getShooter()))) player.setHealth(player.getHealth() - 4.0);
                    return;

                }

                Player player = (Player) snow.getShooter();
                Block hitBlock = event.getHitBlock();
                BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
                List<Block> check = new ArrayList<>();

                for (int i = 0; i < faces.length; i++) {

                    check.add(hitBlock.getRelative(faces[i]));
                    check.add(hitBlock.getRelative(faces[i]).getRelative(BlockFace.DOWN));

                }
                check.add(hitBlock);

                for (Block block : check) {

                    if (block.getType() == Material.CYAN_TERRACOTTA || block.getType() == instance.getOpo(instance.getColor(player))) {

                        setBlockColor(block, player);
                        player.setExp(player.getExp() - 0.013f);

                    }

                }

            }

        }

    }

    private void setBlockColor(Block block, Player player) {

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

    }

}
