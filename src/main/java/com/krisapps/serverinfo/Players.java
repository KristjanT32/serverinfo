package com.krisapps.serverinfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Players implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            StringBuilder sb = new StringBuilder();
            sb.append("\n&e============================================");
            sb.append(String.format("\n&eThis server has a total of &a%d&e out of &c%d&e players online.", Bukkit.getServer().getOnlinePlayers().size(), Bukkit.getServer().getMaxPlayers()));
            sb.append("\n&e============================================");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', sb.toString()));
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cToo many parameters provided. (try none)"));
        }
        return true;
    }
}
