package fr.nilowk.splatoon.test;

import fr.nilowk.splatoon.utils.Direction;
import fr.nilowk.splatoon.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rouleau implements Listener {

    private Main instance;
    private Map<Direction, int[]> patterns = new HashMap<>();
    private int[][] cor;

    public Rouleau(Main instance) {

        this.instance = instance;

        this.cor = new int[][] {

            {-2, -2}, {-1, -2}, {0, -2}, {1, -2}, {2, -2},

            {-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {2, -1},

            {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0},

            {-2, 1}, {-1, 1}, {0, 1}, {1, 1}, {2, 1},

            {-2, 2}, {-1, 2}, {0, 2}, {1, 2}, {2, 2}

        };

        this.patterns.put(Direction.NORTH, new int[] {

               0, 0, 0, 0, 0,
               0, 1, 1, 1, 0,
               1, 1, 1, 1, 1,
               0, 0, 0, 0, 0,
               0, 0, 0, 0, 0

        });
        this.patterns.put(Direction.NORTH_EAST, new int[] {

                0, 0, 1, 0, 0,
                0, 1, 1, 1, 0,
                0, 0, 1, 1, 1,
                0, 0, 0, 1, 0,
                0, 0, 0, 0, 0

        });
        this.patterns.put(Direction.EAST, new int[] {

                0, 0, 1, 0, 0,
                0, 0, 1, 1, 0,
                0, 0, 1, 1, 0,
                0, 0, 1, 1, 0,
                0, 0, 1, 0, 0

        });
        this.patterns.put(Direction.SOUTH_EAST, new int[] {

                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0,
                0, 0, 1, 1, 1,
                0, 1, 1, 1, 0,
                0, 0, 1, 0, 0

        });
        this.patterns.put(Direction.SOUTH, new int[] {

                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                1, 1, 1, 1, 1,
                0, 1, 1, 1, 0,
                0, 0, 0, 0, 0

        });
        this.patterns.put(Direction.SOUTH_WEST, new int[] {

                0, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                1, 1, 1, 0, 0,
                0, 1, 1, 1, 0,
                0, 0, 1, 0, 0

        });
        this.patterns.put(Direction.WEST, new int[] {

                0, 0, 1, 0, 0,
                0, 1, 1, 0, 0,
                0, 1, 1, 0, 0,
                0, 1, 1, 0, 0,
                0, 0, 1, 0, 0

        });
        this.patterns.put(Direction.NORTH_WEST, new int[] {

                0, 0, 1, 0, 0,
                0, 1, 1, 1, 0,
                1, 1, 1, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 0, 0, 0

        });

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE) {

            Player player = event.getPlayer();
            if (player.getExp() < 0.082f) {

                player.removePotionEffect(PotionEffectType.SLOW);
                return;

            }
            player.addPotionEffect(PotionEffectType.SLOW.createEffect(100000, 1));
            setBlocks(player);

        } else {

            event.getPlayer().removePotionEffect(PotionEffectType.SLOW);

        }

    }

    private int[] parse(Direction dir) {

        return this.patterns.get(dir);

    }

    private void setBlocks(Player player) {

        List<Block> replace = new ArrayList<>();

        for (int i = 0; i < this.cor.length; i++) {

            int[] pattern = parse(getDirection(player));

            if (pattern[i] == 1) {

                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation().add(cor[i][0], 0, cor[i][1]).getBlock();

                if (block.getType() == Material.CYAN_TERRACOTTA) {

                    replace.add(block);

                }

            }

        }

        for (Block block : replace) {

            if (player.getExp() >= 0.016f) {
                block.setType(Material.ORANGE_WOOL);
                player.setExp(player.getExp() - 0.016f);
            }

        }

    }

    private Direction getDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getCardinalDirection(rot);
    }

    private Direction getCardinalDirection(double rot) {
        if (0 <= rot && rot < 22.5) {
            return Direction.WEST;
        } else if (22.5 <= rot && rot < 67.5) {
            return Direction.NORTH_WEST;
        } else if (67.5 <= rot && rot < 112.5) {
            return Direction.NORTH;
        } else if (112.5 <= rot && rot < 157.5) {
            return Direction.NORTH_EAST;
        } else if (157.5 <= rot && rot < 202.5) {
            return Direction.EAST;
        } else if (202.5 <= rot && rot < 247.5) {
            return Direction.SOUTH_EAST;
        } else if (247.5 <= rot && rot < 292.5) {
            return Direction.SOUTH;
        } else if (292.5 <= rot && rot < 337.5) {
            return Direction.SOUTH_WEST;
        } else if (337.5 <= rot && rot < 360.0) {
            return Direction.WEST;
        } else {
            return null;
        }
    }

}
