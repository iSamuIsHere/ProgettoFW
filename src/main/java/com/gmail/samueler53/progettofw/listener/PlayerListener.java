package com.gmail.samueler53.progettofw.listener;

import com.gmail.samueler53.progettofw.Data;
import com.gmail.samueler53.progettofw.ProgettoFW;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.awt.dnd.DropTarget;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){//quando il player entra nel server
        Player player = event.getPlayer();

        if(!(player.hasPlayedBefore())){
            UUID uuidPlayer = player.getUniqueId();
            HashMap<UUID,Boolean> newPlayerCurse = ProgettoFW.instance.getData().getPreviouslyPlayersCurse();;
            newPlayerCurse.put(uuidPlayer,false);//new player
            ProgettoFW.instance.getData().saveData("Saved.data");//save data
        }





        //1° restart ->
        if(ProgettoFW.instance.getData().getRestart() == 7) { //send title
            player.sendTitle(ProgettoFW.instance.config.getConfig().getString("title"),ProgettoFW.instance.config.getConfig().getString("subtitle"),10,70,20); //title
        }


        //2° restart ->
        if(ProgettoFW.instance.getData().getRestart() == 7) { //teleport to jungle biome and reset of the inventory

            //allora ho provato a crearlo io il bioma ma non funziona dato che spigot è buggato https://github.com/PaperMC/Paper/issues/2791 https://hub.spigotmc.org/jira/browse/SPIGOT-5823
            player.getWorld().setBiome(100, player.getWorld().getHighestBlockYAt(100, 100), 100, Biome.JUNGLE);
            Block block = player.getWorld().getBlockAt(100, player.getWorld().getHighestBlockYAt(100, 100), 100);
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

        //6° restart ->
        if(ProgettoFW.instance.getData().getRestart() == 7) { //simple message
            player.sendMessage("Al prossimo riavvio del server cio' che hai nell'inventario rimarra' li per sempre. Scegli bene cosa tenere");
        }
    }

    //5° restart ->
    @EventHandler
    public void dropChange (BlockBreakEvent event){//change the log drop in to a dirt drop
        if(ProgettoFW.instance.getData().getRestart() == 7) {
            if (!(event instanceof BlockBreakEvent)) {
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
        if(ProgettoFW.instance.getData().getRestart() == 7 && ProgettoFW.instance.getData().getPreviouslyPlayersCurse().get(event.getWhoClicked())== false) {
            if (!(event.getWhoClicked() instanceof Player)) return;
            Player player = (Player) event.getWhoClicked();
            if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()) {
                event.setCancelled(true);
                player.sendMessage("Sei maledetto muahauahah");
            }
        }

    }
    @EventHandler
    public void stopDrop(PlayerDropItemEvent event){ //cant drop
        if(ProgettoFW.instance.getData().getRestart() == 7 && ProgettoFW.instance.getData().getPreviouslyPlayersCurse().get(event.getPlayer())== false) {
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void stopCollect(EntityPickupItemEvent event){ //cant collect
        if(!(event.getEntity() instanceof Player))
            return;
        if(ProgettoFW.instance.getData().getRestart() == 7 && ProgettoFW.instance.getData().getPreviouslyPlayersCurse().get(event.getEntity())== false) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void keepInventory(PlayerDeathEvent event){ //keep inventory
        if(!(event.getEntity() instanceof Player))
            return;
        if(ProgettoFW.instance.getData().getRestart() == 7 && ProgettoFW.instance.getData().getPreviouslyPlayersCurse().get(event.getEntity())== false){
            event.setKeepInventory(true);
        }
    }

}
