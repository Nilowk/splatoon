package fr.nilowk.splatoon.task;

import fr.nilowk.splatoon.Gstate;
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

        if (timer == 0) {

            instance.setState(Gstate.FINISH);
            cancel();

        }
        timer--;

    }

}
