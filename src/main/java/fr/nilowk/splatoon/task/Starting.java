package fr.nilowk.splatoon.task;

import fr.nilowk.splatoon.Gstate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import fr.nilowk.splatoon.Main;

import java.awt.*;

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
        instance.getPlayers().forEach(player -> player.setExp(1.0f));
        playing.runTaskTimer(instance, 0, 20);
        cancel();

    }

}
