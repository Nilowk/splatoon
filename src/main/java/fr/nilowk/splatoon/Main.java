package fr.nilowk.splatoon;

import fr.nilowk.splatoon.test.Gun;
import fr.nilowk.splatoon.test.Rouleau;
import fr.nilowk.splatoon.test.Test;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Test(this), this);
        pm.registerEvents(new Gun(this), this);
        pm.registerEvents(new Rouleau(this), this);

    }

}
