package fr.nilowk.splatoon.task;

import fr.nilowk.splatoon.utils.Gstate;
import fr.nilowk.splatoon.utils.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import fr.nilowk.splatoon.Main;

public class Starting extends BukkitRunnable {

    private Main instance;
    private FileConfiguration config;
    private int timer;

    public Starting(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.timer = config.getInt("manager.timer.starting-time");

    }

    @Override
    public void run() {

        if (instance.getPlayers().size() < config.getInt("manager.min-size")) {

            instance.setState(Gstate.WAITING);
            instance.getPlayers().forEach(player -> player.sendMessage(config.getString("message.stop-starting")));
            cancel();

        }
        if (instance.getPlayers().size() == config.getInt("manager.max-size")) launch();
        int[] time = new int[] {10, 5, 4, 3, 2, 1};
        instance.getPlayers().forEach(player -> player.setLevel(timer));
        for (int t : time) {
            if (timer == t) {
                Bukkit.broadcastMessage(config.getString("message.starting").replace("{TIME}", timer+""));
            }
        }
        if (timer == 0) launch();
        timer--;

    }

    private void launch() {

        Bukkit.broadcastMessage(config.getString("message.start"));
        Playing playing = new Playing(instance);
        instance.setState(Gstate.PLAYING);
        instance.getPlayers().forEach(player -> {
            player.setExp(1.0f);
            if (instance.getColor(player) == Material.ORANGE_WOOL) {
                player.teleport(instance.getOrangeSpawn());
            } else {
                player.teleport(instance.getBlueSpawn());
            }
            giveKit(player);
        });
        playing.runTaskTimer(instance, 0, 20);
        cancel();

    }

    private void giveKit(Player player) {

        if (instance.getKits().get(player) == Kit.DEFAULT) {
            ItemStack it = new ItemStack(Material.SNOWBALL, 1);
            ItemMeta im = it.getItemMeta();
            im.setDisplayName("§4Liquidateur");
            it.setItemMeta(im);
            player.getInventory().setItem(0, it);
        } else  if (instance.getKits().get(player) == Kit.EXTRACEUR) {
            ItemStack it = new ItemStack(Material.BOW, 1);
            ItemMeta im = it.getItemMeta();
            im.setDisplayName("§4Extraceur");
            im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            it.setItemMeta(im);
            player.getInventory().setItem(0, it);
            player.getInventory().setItem(9, new ItemStack(Material.ARROW));
        } else {
            ItemStack it = new ItemStack(Material.WOODEN_HOE, 1);
            ItemMeta im = it.getItemMeta();
            im.setDisplayName("§4Rouleau");
            it.setItemMeta(im);
            player.getInventory().setItem(0, it);
        }

    }

}
