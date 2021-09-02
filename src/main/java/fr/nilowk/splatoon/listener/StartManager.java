package fr.nilowk.splatoon.listener;

import fr.nilowk.splatoon.Gstate;
import fr.nilowk.splatoon.Main;
import fr.nilowk.splatoon.task.Starting;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StartManager implements Listener {

    private Main instance;
    private FileConfiguration config;

    public StartManager(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (!instance.isState(Gstate.WAITING) && !instance.isState(Gstate.STARTING)) return;
        player.setGameMode(GameMode.ADVENTURE);
        player.setLevel(0);
        String count = (" (min/max)").replace("min", ""+instance.getPlayers().size()).replace("max", config.getInt("manager.max-size")+"");
        event.setJoinMessage(config.getString("message.join").replace("{PLAYER}", player.getDisplayName()) + count);
        instance.getPlayers().add(player);

        if (instance.getPlayers().size() == 2) {
            Starting waiting = new Starting(instance);
            instance.setState(Gstate.STARTING);
            waiting.runTaskTimer(instance, 0, 20);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (!instance.isState(Gstate.WAITING) && !instance.isState(Gstate.STARTING)) return;
        Player player = event.getPlayer();
        String count = (" (min/max)").replace("min", ""+instance.getPlayers().size()).replace("max", config.getInt("manager.max-size")+"");
        event.setQuitMessage(config.getString("message.quit").replace("{PLAYER}", player.getDisplayName()) + count);
        instance.getPlayers().remove(player);

    }

    @EventHandler
    public void TakeDamage(EntityDamageEvent event) {

        if (!instance.isState(Gstate.WAITING) && !instance.isState(Gstate.STARTING)) return;
        if (event.getEntity().getType() == EntityType.PLAYER) {

            event.setCancelled(true);

        }

    }

}
