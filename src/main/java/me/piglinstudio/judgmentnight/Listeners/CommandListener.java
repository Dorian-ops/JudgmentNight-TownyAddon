package me.piglinstudio.judgmentnight.Listeners;

import me.piglinstudio.judgmentnight.NightSystem.NightPhase;
import me.piglinstudio.judgmentnight.util.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String commandName = command.getName();
        Player player = (Player) commandSender;
            if(commandName.toLowerCase().equals("startnight")){
                if(commandSender.hasPermission("startNight.permission")){
                    new NightPhase().nightStart();
                    return true;
                } else {
                    player.sendMessage(ChatColor.getMsg("&cНедостаточно прав"));
                }
            } else if (commandName.toLowerCase().equals("stopnight")){
                if(commandSender.hasPermission("stopNight.permission")){
                    new NightPhase().nightStop();
                    return true;
                } else {
                    player.sendMessage(ChatColor.getMsg("&cНедостаточно прав"));
                }
            }
        return false;
    }
}
