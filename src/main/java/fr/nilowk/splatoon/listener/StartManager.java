package fr.nilowk.splatoon.listener;

import fr.nilowk.splatoon.utils.Gstate;
import fr.nilowk.splatoon.utils.Kit;
import fr.nilowk.splatoon.Main;
import fr.nilowk.splatoon.task.Starting;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        player.teleport(instance.getSpawn());
        player.getInventory().clear();
        player.setLevel(0);
        instance.getPlayers().add(player);
        String count = (" (min/max)").replace("min", ""+instance.getPlayers().size()).replace("max", config.getInt("manager.max-size")+"");
        event.setJoinMessage(config.getString("message.join").replace("{PLAYER}", player.getDisplayName()) + count);

        if (instance.getPlayers().size() == 2) {
            Starting waiting = new Starting(instance);
            instance.setState(Gstate.STARTING);
            waiting.runTaskTimer(instance, 0, 20);
        }

        ItemStack it = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName("§2Choisis Ton Kit");
        it.setItemMeta(im);

        player.getInventory().setItem(0, it);
        instance.setTeamPlayer(player);
        instance.getKits().put(player, Kit.DEFAULT);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        if (!instance.isState(Gstate.WAITING) && !instance.isState(Gstate.STARTING)) return;
        Player player = event.getPlayer();
        instance.getPlayers().remove(player);
        String count = (" (min/max)").replace("min", ""+instance.getPlayers().size()).replace("max", config.getInt("manager.max-size")+"");
        event.setQuitMessage(config.getString("message.quit").replace("{PLAYER}", player.getDisplayName()) + count);

    }

    @EventHandler
    public void TakeDamage(EntityDamageEvent event) {

        if (!instance.isState(Gstate.WAITING) && !instance.isState(Gstate.STARTING)) return;
        if (event.getEntity().getType() == EntityType.PLAYER) {

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void openKitSelector(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();
        if (!instance.isState(Gstate.WAITING) && !instance.isState(Gstate.STARTING)) return;
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR) {

            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Choisis Ton Kit")) {

                Inventory inv = Bukkit.createInventory(null, 9, "Kits");

                ItemStack de = new ItemStack(Material.SNOWBALL, 1);
                ItemMeta dem = de.getItemMeta();
                dem.setDisplayName("Liquidateur");
                if (instance.getKits().get(player) == Kit.DEFAULT) dem.addEnchant(Enchantment.DURABILITY, 1, true);
                de.setItemMeta(dem);

                ItemStack sn = new ItemStack(Material.BOW, 1);
                ItemMeta snm = de.getItemMeta();
                snm.setDisplayName("Extraceur");
                if (instance.getKits().get(player) == Kit.DEFAULT) snm.addEnchant(Enchantment.DURABILITY, 1, true);
                sn.setItemMeta(snm);

                ItemStack ro = new ItemStack(Material.WOODEN_HOE, 1);
                ItemMeta rom = de.getItemMeta();
                rom.setDisplayName("Rouleau");
                if (instance.getKits().get(player) == Kit.DEFAULT) rom.addEnchant(Enchantment.DURABILITY, 1, true);
                ro.setItemMeta(rom);

                inv.setItem(2, de);
                inv.setItem(4, sn);
                inv.setItem(6, ro);

                player.openInventory(inv);

            }

        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!instance.isState(Gstate.WAITING) && !instance.isState(Gstate.STARTING)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack it = event.getCurrentItem();
        if (it != null) {

            if (event.getView().getTitle().equalsIgnoreCase("Kits")) {

                if (it.getItemMeta().getDisplayName().equalsIgnoreCase("Liquidateur")) instance.getKits().replace(player, instance.getKits().get(player), Kit.DEFAULT);
                if (it.getItemMeta().getDisplayName().equalsIgnoreCase("Extraceur")) instance.getKits().replace(player, instance.getKits().get(player), Kit.EXTRACEUR);
                if (it.getItemMeta().getDisplayName().equalsIgnoreCase("Rouleau")) instance.getKits().replace(player, instance.getKits().get(player), Kit.ROULEAU);
                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage(config.getString("message.kit-selected"));

            }

        }

    }

}
