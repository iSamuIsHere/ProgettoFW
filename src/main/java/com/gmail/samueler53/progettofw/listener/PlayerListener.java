package com.gmail.samueler53.progettofw.listener;

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

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){//quando il player entra nel server
        Player player = event.getPlayer();



        //1° restart ->
        if(ProgettoFW.riavvio==1) { //send title
            player.sendTitle(ProgettoFW.instance.config.getConfig().getString("title"),ProgettoFW.instance.config.getConfig().getString("subtitle"),10,70,20); //title
            ProgettoFW.maledizione = false; //per resettarlo al primo riavvio
        }


        //2° restart ->
        if(ProgettoFW.riavvio==2) { //teleport to jungle biome and reset of the inventory

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
        if(ProgettoFW.riavvio==6) { //simple message
            player.sendMessage("Al prossimo riavvio del server cio' che hai nell'inventario rimarra' li per sempre. Scegli bene cosa tenere");
        }
    }

    //5° restart ->
    @EventHandler
    public void dropChange (BlockBreakEvent event){//change the log drop in to a dirt drop
        if(ProgettoFW.riavvio == 5) {
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
    public void interazioneInventario(InventoryClickEvent event){ //unable to use the inventory
        if(ProgettoFW.riavvio == 7 && ProgettoFW.maledizione == false) {
            if (!(event.getWhoClicked() instanceof Player)) return;
            Player player = (Player) event.getWhoClicked();
            if (event.isLeftClick() || event.isRightClick() || event.isShiftClick()) {
                event.setCancelled(true);
                player.sendMessage("Sei maledetto muahauahah");
            }
        }

    }
    @EventHandler
    public void bloccaDrop(PlayerDropItemEvent event){ //cant drop
        if(ProgettoFW.riavvio == 7 && ProgettoFW.maledizione == false) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void bloccaCollect(EntityPickupItemEvent event){ //cant collect
        if(ProgettoFW.riavvio == 7 && ProgettoFW.maledizione == false) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void keepInventory(PlayerDeathEvent event){ //keep inventory
        if(ProgettoFW.riavvio == 7 && ProgettoFW.maledizione == false){
            event.setKeepInventory(true);
        }
    }

}
