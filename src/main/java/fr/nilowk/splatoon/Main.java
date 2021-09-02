package fr.nilowk.splatoon;

import fr.nilowk.splatoon.listener.GameManager;
import fr.nilowk.splatoon.listener.StartManager;
import java.util.*;
import fr.nilowk.splatoon.test.Gun;
import fr.nilowk.splatoon.test.Rouleau;
import fr.nilowk.splatoon.test.Sniper;
import fr.nilowk.splatoon.test.Test;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private List<Player> players = new ArrayList<>();
    private Gstate state;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        setState(Gstate.WAITING);

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Test(this), this);
        pm.registerEvents(new Gun(this), this);
        pm.registerEvents(new Rouleau(this), this);
        pm.registerEvents(new Sniper(this), this);
        //
        pm.registerEvents(new StartManager(this), this);
        pm.registerEvents(new GameManager(this), this);

    }

    public List<Player> getPlayers() {

        return players;

    }

    public boolean isState(Gstate state) {

        return this.state == state;

    }

    public void setState(Gstate state) {

        this.state = state;

    }

}
