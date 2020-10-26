package com.gmail.samueler53.progettofw.listener;

import com.gmail.samueler53.progettofw.ProgettoFW;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final ProgettoFW plugin;

    public PlayerListener(ProgettoFW plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuidPlayer = player.getUniqueId();
        if (!(plugin.getData().getPreviouslyPlayersCurse().containsKey(uuidPlayer))) {
            plugin.getData().setPlayerCurse(uuidPlayer, false);
            plugin.getData().saveData();
        }
        switch (plugin.getData().getRestart()) {
            case 1: {
                player.sendTitle(plugin.getPluginConfig().getConfig().getString("title"), plugin.getPluginConfig().getConfig().getString("subtitle"), 10, 70, 20);
                break;
            }
            case 2: {
                World world = player.getWorld();
                Location location = new Location(world, 100, player.getWorld().getHighestBlockYAt(100, 100), 100);
                //allora ho provato a crearlo io il bioma ma non funziona dato che spigot è buggato https://github.com/PaperMC/Paper/issues/2791 https://hub.spigotmc.org/jira/browse/SPIGOT-5823
                location.getBlock().setBiome(Biome.JUNGLE);
                player.teleport(location);
                player.getInventory().clear();
                break;
            }
            case 3: {
                player.sendMessage("Hai accesso al comando /easter egg 3");
                break;
            }
            case 6: {
                player.sendMessage("Al prossimo riavvio del server cio' che hai nell'inventario rimarra' li per sempre. Scegli bene cosa tenere");
                break;
            }
        }
    }

    //5° restart ->
    @EventHandler
    public void dropChange(BlockBreakEvent event) {
        if (plugin.getData().getRestart() == 5) {
            if (event == null) {
                return;
            }
            if (event.getBlock().getType().toString().endsWith("LOG")) {
                event.getBlock().setType(Material.AIR);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.DIRT, 1));
            }
        }
    }

    //7° restart ->
    @EventHandler
    public void inventoryInteract(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getWhoClicked().getUniqueId())) {
            Player player = (Player) event.getWhoClicked();
            if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()) {
                event.setCancelled(true);
                player.sendMessage("Sei maledetto muahauahah");
            }
        }
    }

    @EventHandler
    public void stopDrop(PlayerDropItemEvent event) {
        if (plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getPlayer().getUniqueId())) {
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void stopCollect(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        if (plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void keepInventory(PlayerDeathEvent event) {
        if (plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getEntity().getUniqueId())) {
            event.setKeepInventory(true);
        }
    }

    @EventHandler
    public void stopPlace(BlockPlaceEvent event) {
        if (plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
