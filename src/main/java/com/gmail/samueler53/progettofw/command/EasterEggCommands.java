package com.gmail.samueler53.progettofw.command;

import com.gmail.samueler53.progettofw.ProgettoFW;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EasterEggCommands implements CommandExecutor {

    private final ProgettoFW plugin;

    public EasterEggCommands(ProgettoFW plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (!(args.length == 2)) {
                sender.sendMessage("Devi inserire due argomenti");
            } else if (plugin.getData().getRestart() == 3) { //3° restart ->
                if (args[0].equalsIgnoreCase("egg") && args[1].equalsIgnoreCase("3")) { //spawn horse
                    Player player = (Player) sender;
                    World world = player.getWorld();
                    Horse horse = (Horse) world.spawnEntity(player.getLocation(), EntityType.HORSE);
                    horse.setColor(Horse.Color.WHITE);
                    horse.setTamed(true);
                    horse.setOwner(player);
                    horse.setCustomName("Napoleone");
                    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
                } else{
                    sender.sendMessage("Parametri sbagliati");
                }
            } else if (plugin.getData().getRestart() == 6) { //6° restart ->
                if (args[0].equalsIgnoreCase("egg") && args[1].equalsIgnoreCase("6")) {
                    UUID uuidPlayer = ((Player) sender).getUniqueId();
                    plugin.getData().setPlayerCurse(uuidPlayer, true);
                    plugin.getData().saveData();//save data
                }
            }
        }
        return true;
    }
}
