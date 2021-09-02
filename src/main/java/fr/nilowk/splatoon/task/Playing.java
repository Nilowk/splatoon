package fr.nilowk.splatoon.task;

import fr.nilowk.splatoon.utils.Gstate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import fr.nilowk.splatoon.Main;

public class Playing extends BukkitRunnable {

    private Main instance;
    private FileConfiguration config;
    private int timer;


    public Playing(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.timer = config.getInt("manager.timer.playing-time");

    }

    @Override
    public void run() {

        instance.getPlayers().forEach(player -> player.setLevel(timer));

        if (instance.getPlayers().size() == 1) {

            if (instance.getColor(instance.getPlayers().get(0)) == Material.ORANGE_WOOL) {

                Bukkit.broadcastMessage(config.getString("message.win").replace("{TEAM}", "orange"));

            } else {

                Bukkit.broadcastMessage(config.getString("message.win").replace("{TEAM}", "bleu"));

            }
            Finish finish = new Finish(instance);
            instance.setState(Gstate.FINISH);
            finish.runTaskTimer(instance, 0, 20);
            cancel();

        }

        if (timer == 0) {

            if (instance.getWinner() == Material.ORANGE_WOOL) {

                Bukkit.broadcastMessage(config.getString("message.win").replace("{TEAM}", "orange"));

            } else if (instance.getWinner() == Material.BLUE_WOOL) {

                Bukkit.broadcastMessage(config.getString("message.win").replace("{TEAM}", "bleu"));

            } else {

                Bukkit.broadcastMessage(config.getString("message.no-match"));

            }
            Finish finish = new Finish(instance);
            instance.setState(Gstate.FINISH);
            finish.runTaskTimer(instance, 0, 20);
            cancel();

        }
        timer--;

    }

}
