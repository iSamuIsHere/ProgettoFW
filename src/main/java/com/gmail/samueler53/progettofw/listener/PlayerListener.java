package com.gmail.samueler53.progettofw.listener;

import com.gmail.samueler53.progettofw.ProgettoFW;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final ProgettoFW plugin;

    public PlayerListener(ProgettoFW plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){//quando il player entra nel server
        Player player = event.getPlayer();

        if(!(player.hasPlayedBefore())){
            UUID uuidPlayer = player.getUniqueId();
            HashMap<UUID,Boolean> newPlayerCurse = plugin.getData().getPreviouslyPlayersCurse();
            plugin.getData().setPlayerCurse(uuidPlayer,false);//new player
            plugin.getData().saveData("Saved.data");//save data
        }





        //1° restart ->
        if(plugin.getData().getRestart() == 1) { //send title
            player.sendTitle(plugin.getConfig().getString("title"),plugin.getConfig().getString("subtitle"),10,70,20); //title
        }


        //2° restart ->
        if(plugin.getData().getRestart() == 2) { //teleport to jungle biome and reset of the inventory

            //allora ho provato a crearlo io il bioma ma non funziona dato che spigot è buggato https://github.com/PaperMC/Paper/issues/2791 https://hub.spigotmc.org/jira/browse/SPIGOT-5823
            player.getWorld().setBiome(100, player.getWorld().getHighestBlockYAt(100, 100), 100, Biome.JUNGLE);
           // Block block = player.getWorld().getBlockAt(100, player.getWorld().getHighestBlockYAt(100, 100), 100);
            player.teleport(new Location(player.getWorld(), 100, player.getWorld().getHighestBlockYAt(100, 100), 100));
            //in alternativa ho provato questa soluzione laggosa
//            Bukkit.getScheduler().runTaskAsynchronously(ProgettoFW.instance,()->{
//                        Random rand = new Random(); //farebbe laggare troppo.
//                        int upperbound = 100000;
//                        int randomx = rand.nextInt(upperbound);
//                        int randomz = rand.nextInt(upperbound);
//                        Block block = player.getLocation().getBlock();
//                        while(block.getBiome()!= Biome.JUNGLE) {
//                            if (block.getBiome() == Biome.JUNGLE) {
//                                player.teleport(block.getLocation());
//                                player.sendMessage("prova");
//                            }
//                        }
//            });
            player.getInventory().clear();//svuotare l'inventario
        }

        if(plugin.getData().getRestart() == 3){
            player.sendMessage("Hai accesso al comando /easter egg 3");
        }

        //6° restart ->
        if(plugin.getData().getRestart() == 6) { //simple message
            player.sendMessage("Al prossimo riavvio del server cio' che hai nell'inventario rimarra' li per sempre. Scegli bene cosa tenere");
        }
    }

    //5° restart ->
    @EventHandler
    public void dropChange (BlockBreakEvent event){//change the log drop in to a dirt drop
        if(plugin.getData().getRestart() == 5) {
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
    public void inventoryInteract(InventoryClickEvent event){ //unable to use the inventory
        if(!(event.getWhoClicked() instanceof  Player)) return;
        if(plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getWhoClicked())) {
            if (!(event.getWhoClicked() instanceof Player)) return;
            Player player = (Player) event.getWhoClicked();
            if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()) {
                event.setCancelled(true);
                player.sendMessage("Sei maledetto muahauahah");
            }
        }

    }
    @EventHandler
    public void stopDrop(PlayerDropItemEvent event){//cant
        if(plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getPlayer())) {
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void stopCollect(EntityPickupItemEvent event){ //cant collect
        if(!(event.getEntity() instanceof Player))
            return;
        if(plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getEntity())) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void keepInventory(PlayerDeathEvent event){ //keep inventory
        event.getEntity();
        if(plugin.getData().getRestart() == 7 && !plugin.getData().getPreviouslyPlayersCurse().get(event.getEntity())){
            event.setKeepInventory(true);
        }
    }

}
