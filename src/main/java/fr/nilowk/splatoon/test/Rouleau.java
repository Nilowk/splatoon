package fr.nilowk.splatoon.test;

import fr.nilowk.splatoon.Main;
import org.bukkit.Bukkit;
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

    public Rouleau(Main instance) {

        this.instance = instance;

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
            player.addPotionEffect(PotionEffectType.SLOW.createEffect(100000, 1));
            Bukkit.broadcastMessage(""+player.getLocation().getDirection().toString());
            if (player.getLocation().getYaw() > -60 && player.getLocation().getYaw() < -30) {

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block east = block.getRelative(BlockFace.EAST);
                Block south = block.getRelative(BlockFace.SOUTH);
                Block face = block.getRelative(BlockFace.SOUTH_EAST);
                Block faceRight = face.getRelative(BlockFace.SOUTH_WEST);
                Block faceLeft = face.getRelative(BlockFace.NORTH_EAST);
                Block right = block.getRelative(BlockFace.SOUTH_WEST);
                Block left = block.getRelative(BlockFace.NORTH_EAST);

                blocks.add(block);
                blocks.add(east);
                blocks.add(south);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);
                blocks.add(right);
                blocks.add(left);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            } else if (player.getLocation().getYaw() > (-30f) && player.getLocation().getYaw() < 30) {

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block right = block.getRelative(BlockFace.WEST);
                Block left = block.getRelative(BlockFace.EAST);
                Block r = right.getRelative(BlockFace.WEST);
                Block l = left.getRelative(BlockFace.EAST);
                Block face = block.getRelative(BlockFace.SOUTH);
                Block faceRight = face.getRelative(BlockFace.WEST);
                Block faceLeft = face.getRelative(BlockFace.EAST);

                blocks.add(block);
                blocks.add(right);
                blocks.add(left);
                blocks.add(r);
                blocks.add(l);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            } else if (player.getLocation().getYaw() > 30 && player.getLocation().getYaw() < 60) {

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block west = block.getRelative(BlockFace.WEST);
                Block south = block.getRelative(BlockFace.SOUTH);
                Block face = block.getRelative(BlockFace.SOUTH_WEST);
                Block faceRight = face.getRelative(BlockFace.NORTH_WEST);
                Block faceLeft = face.getRelative(BlockFace.SOUTH_EAST);
                Block right = block.getRelative(BlockFace.NORTH_WEST);
                Block left = block.getRelative(BlockFace.SOUTH_EAST);

                blocks.add(block);
                blocks.add(west);
                blocks.add(south);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);
                blocks.add(right);
                blocks.add(left);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            } else if (player.getLocation().getYaw() > 60 && player.getLocation().getYaw() < 120) {

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block right = block.getRelative(BlockFace.NORTH);
                Block left = block.getRelative(BlockFace.SOUTH);
                Block r = right.getRelative(BlockFace.NORTH);
                Block l = left.getRelative(BlockFace.SOUTH);
                Block face = block.getRelative(BlockFace.WEST);
                Block faceRight = face.getRelative(BlockFace.NORTH);
                Block faceLeft = face.getRelative(BlockFace.SOUTH);

                blocks.add(block);
                blocks.add(right);
                blocks.add(left);
                blocks.add(r);
                blocks.add(l);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            } else if (player.getLocation().getYaw() > 120 && player.getLocation().getYaw() < 150) {

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block west = block.getRelative(BlockFace.WEST);
                Block north = block.getRelative(BlockFace.NORTH);
                Block face = block.getRelative(BlockFace.NORTH_WEST);
                Block faceRight = face.getRelative(BlockFace.NORTH_EAST);
                Block faceLeft = face.getRelative(BlockFace.SOUTH_WEST);
                Block right = block.getRelative(BlockFace.NORTH_EAST);
                Block left = block.getRelative(BlockFace.SOUTH_WEST);

                blocks.add(block);
                blocks.add(west);
                blocks.add(north);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);
                blocks.add(right);
                blocks.add(left);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            } else if (player.getLocation().getYaw() > 150 && player.getLocation().getYaw() < (-150f)) {

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block right = block.getRelative(BlockFace.EAST);
                Block left = block.getRelative(BlockFace.WEST);
                Block r = right.getRelative(BlockFace.EAST);
                Block l = left.getRelative(BlockFace.WEST);
                Block face = block.getRelative(BlockFace.NORTH);
                Block faceRight = face.getRelative(BlockFace.EAST);
                Block faceLeft = face.getRelative(BlockFace.WEST);

                blocks.add(block);
                blocks.add(right);
                blocks.add(left);
                blocks.add(r);
                blocks.add(l);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            } else if (player.getLocation().getYaw() > -150 && player.getLocation().getYaw() < -120) {

                System.out.println("ok");

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block east = block.getRelative(BlockFace.EAST);
                Block north = block.getRelative(BlockFace.NORTH);
                Block face = block.getRelative(BlockFace.NORTH_EAST);
                Block faceRight = face.getRelative(BlockFace.SOUTH_EAST);
                Block faceLeft = face.getRelative(BlockFace.NORTH_WEST);
                Block right = block.getRelative(BlockFace.SOUTH_EAST);
                Block left = block.getRelative(BlockFace.NORTH_WEST);

                blocks.add(block);
                blocks.add(east);
                blocks.add(north);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);
                blocks.add(right);
                blocks.add(left);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            }
            else if (player.getLocation().getYaw() > (-120f) && player.getLocation().getYaw() < (-60f)) {

                List<Block> blocks = new ArrayList<>();
                Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Block right = block.getRelative(BlockFace.SOUTH);
                Block left = block.getRelative(BlockFace.NORTH);
                Block r = right.getRelative(BlockFace.SOUTH);
                Block l = left.getRelative(BlockFace.NORTH);
                Block face = block.getRelative(BlockFace.EAST);
                Block faceRight = face.getRelative(BlockFace.SOUTH);
                Block faceLeft = face.getRelative(BlockFace.NORTH);

                blocks.add(block);
                blocks.add(right);
                blocks.add(left);
                blocks.add(r);
                blocks.add(l);
                blocks.add(face);
                blocks.add(faceRight);
                blocks.add(faceLeft);

                for (Block bloc : blocks) {

                    if (bloc.getType() == Material.CYAN_TERRACOTTA) {

                        bloc.setType(Material.ORANGE_WOOL);

                    }

                }

            }

        } else {

            event.getPlayer().removePotionEffect(PotionEffectType.SLOW);

        }

    }

    private int[] parse(Direction dir) {

        return this.patterns.get(dir);

    }

    public Direction getDirection(Player player) {

        double rotation = (player.getLocation().getYaw() - 90) % 360;

        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return Direction.NORTH;
        } else if (22.5 <= rotation && rotation < 67.5) {
            return Direction.NORTH_EAST;
        } else if (67.5 <= rotation && rotation < 112.5) {
            return Direction.EAST;
        } else if (112.5 <= rotation && rotation < 157.5) {
            return Direction.SOUTH_EAST;
        } else if (157.5 <= rotation && rotation < 202.5) {
            return Direction.SOUTH;
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SW";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "W";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "NW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }

    }

}
