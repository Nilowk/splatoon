package fr.nilowk.splatoon;

import fr.nilowk.splatoon.listener.GameManager;
import fr.nilowk.splatoon.listener.StartManager;
import fr.nilowk.splatoon.weapon.Gun;
import fr.nilowk.splatoon.weapon.Rouleau;
import fr.nilowk.splatoon.weapon.Sniper;
import fr.nilowk.splatoon.utils.Gstate;
import fr.nilowk.splatoon.utils.Kit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {

    private List<Player> players = new ArrayList<>();
    private List<Block> blockToRegenOrange = new ArrayList<>();
    private List<Block> blockToRegenBlue = new ArrayList<>();
    private List<Player> blue = new ArrayList<>();
    private List<Player> orange = new ArrayList<>();
    private Map<Player, Kit> kits = new HashMap<>();
    private Gstate state;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        setState(Gstate.WAITING);

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Gun(this), this);
        pm.registerEvents(new Rouleau(this), this);
        pm.registerEvents(new Sniper(this), this);
        //
        pm.registerEvents(new StartManager(this), this);
        pm.registerEvents(new GameManager(this), this);

    }

    public Location getSpawn() {

        return null;

    }

    public Location getBlueSpawn() {

        return null;

    }

    public Location getOrangeSpawn() {

        return null;

    }

    public List<Player> getBlue() {

        return blue;

    }

    public List<Player> getOrange() {

        return this.orange;

    }

    public Material getColor(Player player) {

        if (getOrange().contains(player)) return Material.ORANGE_WOOL;
        return Material.BLUE_WOOL;

    }

    public Map<Player, Kit> getKits() {

        return kits;

    }

    public boolean isCalamar(Player player) {

        if (player.isInvisible()) {

            return true;

        }
        return false;

    }

    public List<Player> getPlayers() {

        return players;

    }

    public List<Block> getBlockToRegenOrange() {

        return this.blockToRegenOrange;

    }

    public List<Block> getBlockToRegenBlue() {

        return this.blockToRegenBlue;

    }


    public void setTeamPlayer(Player player) {

        if (getBlue().size() <= getOrange().size()) {

            getBlue().add(player);

        } else {

            getOrange().add(player);

        }

    }

    public Material getWinner() {

        if (getBlockToRegenBlue().size() < getBlockToRegenOrange().size()) {

            return Material.ORANGE_WOOL;

        } else if (getBlockToRegenBlue().size() > getBlockToRegenOrange().size()) {

            return Material.BLUE_WOOL;

        }
        return null;

    }

    public boolean isState(Gstate state) {

        return this.state == state;

    }

    public void setState(Gstate state) {

        this.state = state;

    }

}
