package fr.nilowk.splatoon.task;

import fr.nilowk.splatoon.Main;
import fr.nilowk.splatoon.utils.Gstate;
import fr.nilowk.splatoon.utils.Kit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Finish extends BukkitRunnable {

    private Main instance;
    private FileConfiguration config;
    private int timer;

    public Finish(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.timer = config.getInt("timer.finish-time");

    }

    @Override
    public void run() {

        int[] time = new int[] {10, 5, 4, 3, 2, 1};
        for (int t : time) {
            if (timer == t) {
                Bukkit.broadcastMessage(config.getString("message.relaunch").replace("{TIME}", timer+""));
            }
        }

        if (timer == 0) {

            for (Player player : instance.getServer().getOnlinePlayers()) {

                clear();
                instance.setState(Gstate.WAITING);
                player.setGameMode(GameMode.ADVENTURE);
                player.teleport(instance.getSpawn());
                player.getInventory().clear();
                player.setLevel(0);
                instance.getPlayers().add(player);

                ItemStack it = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta im = it.getItemMeta();
                im.setDisplayName("§2Choisis Ton Kit");
                it.setItemMeta(im);

                player.getInventory().setItem(0, it);
                instance.setTeamPlayer(player);
                instance.getKits().put(player, Kit.DEFAULT);

            }
            if (instance.getPlayers().size() >= 2) {
                System.out.println("test");
                Starting starting = new Starting(instance);
                instance.setState(Gstate.STARTING);
                starting.runTaskTimer(instance, 0, 20);
            }
            cancel();

        }

        timer--;

    }

    private void clear() {

        for (Block block : instance.getBlockToRegenOrange()) {

            block.setType(Material.CYAN_TERRACOTTA);

        }
        for (Block block : instance.getBlockToRegenBlue()) {

            block.setType(Material.CYAN_TERRACOTTA);

        }

        instance.getPlayers().clear();
        instance.getBlockToRegenOrange().clear();
        instance.getBlockToRegenBlue().clear();
        instance.getBlue().clear();
        instance.getOrange().clear();
        instance.getNoColor().clear();
        instance.getKits().clear();

    }

}
