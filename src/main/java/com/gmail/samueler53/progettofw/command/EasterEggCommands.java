package com.gmail.samueler53.progettofw.command;

import com.gmail.samueler53.progettofw.Data;
import com.gmail.samueler53.progettofw.ProgettoFW;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class EasterEggCommands implements CommandExecutor {

    private final ProgettoFW plugin;

    public EasterEggCommands(ProgettoFW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //easteregg
        if(sender instanceof Player){


            //3° restart ->
            if(ProgettoFW.instance.getData().getRestart() == 7) {
                if (args[0].equalsIgnoreCase("3")) { //spawn horse
                    Player player = (Player) sender;
                    World world = player.getWorld();
                    Horse horse = (Horse) world.spawnEntity(player.getLocation(), EntityType.HORSE);
                    horse.setColor(Horse.Color.WHITE);
                    horse.setTamed(true);
                    horse.setOwner(player);
                    horse.setCustomName("Napoleone");
                    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                }
            }

            //6° restart ->
            if(ProgettoFW.instance.getData().getRestart() == 7) {
                if (args[0].equalsIgnoreCase("6")) {
                    UUID uuidPlayer = ((Player) sender).getUniqueId();
                    HashMap<UUID,Boolean> newPlayerCurse = ProgettoFW.instance.getData().getPreviouslyPlayersCurse();
                    newPlayerCurse.put(uuidPlayer,true);//new player
                    ProgettoFW.instance.getData().saveData("Saved.data");//save data
                }
            }
        }


        return true;
    }
}
