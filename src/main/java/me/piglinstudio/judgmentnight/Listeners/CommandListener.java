package me.piglinstudio.judgmentnight.Listeners;

import me.piglinstudio.judgmentnight.NightSystem.NightPhase;
import me.piglinstudio.judgmentnight.util.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String commandName = command.getName();
        if(commandName.toLowerCase().equals("night")){
            if(args[0].equalsIgnoreCase("start")){
                if(commandSender.hasPermission("startNight.permission")){
                    new NightPhase().nightStart();
                    return true;
                } else {
                    if(commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        player.sendMessage(ChatColor.getMsg("&cНедостаточно прав"));
                    }
                }
            } else if (args[0].equalsIgnoreCase("stop")) {
                if(commandSender.hasPermission("stopNight.permission")){
                    new NightPhase().nightStop();
                    return true;
                } else {
                    if(commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        player.sendMessage(ChatColor.getMsg("&cНедостаточно прав"));
                    }
                }
            }
        }
        return false;
    }
}
