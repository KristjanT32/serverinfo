package com.krisapps.serverinfo;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class BanInfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /baninfo [player]

        StringBuilder sb = new StringBuilder();
        Server server = sender.getServer();
        sender.sendMessage(ChatColor.YELLOW + "===========================================");
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aShowing all banned players"));
            if (server.getBannedPlayers().size() != 0) {
                for (OfflinePlayer offlinePlayer : server.getBannedPlayers()) {
                    sb.append("&b-----\n");
                    sb.append("&a" + offlinePlayer.getName() + " &e(&bUUID: " + offlinePlayer.getUniqueId() + "&e)&r\n");
                    sb.append("&aReason: &e" + server.getBanList(BanList.Type.NAME).getBanEntry(offlinePlayer.getName()).getReason() + "\n");
                    sb.append("&aBy: &b" + server.getBanList(BanList.Type.NAME).getBanEntry(offlinePlayer.getName()).getSource() + "\n");
                    if (server.getBanList(BanList.Type.NAME).getBanEntry(offlinePlayer.getName()).getExpiration() != null) {
                        sb.append("&aExpiration: &e&l" + server.getBanList(BanList.Type.NAME).getBanEntry(offlinePlayer.getName()).getExpiration() + "\n&b-----&r\n");
                    } else {
                        sb.append("&aExpiration: &c&lNever\n&b-----");
                    }
                }
                sb.append("\n&eA total of &a&l" + Bukkit.getBanList(BanList.Type.NAME).getBanEntries().size() + "&e ban entries.");
            } else {
                sb.append("&cThere are no banned players on your server.\nGood for you!");
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', sb.toString()));
        }else if (args.length == 1){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aShowing info for &e" + args[0]));
            if (Bukkit.getBanList(BanList.Type.NAME).getBanEntry(args[0]) != null){
                sb.append("&b-----\n");
                sb.append("&a" + Bukkit.getOfflinePlayer(args[0]).getName() + " &e(&bUUID: " + Bukkit.getOfflinePlayer(args[0]).getUniqueId() + "&e)&r\n");
                sb.append("&aReason: &e" + server.getBanList(BanList.Type.NAME).getBanEntry(Bukkit.getOfflinePlayer(args[0]).getName()).getReason() + "\n");
                sb.append("&aBy: &b" + server.getBanList(BanList.Type.NAME).getBanEntry(Bukkit.getOfflinePlayer(args[0]).getName()).getSource() + "\n");
                if (server.getBanList(BanList.Type.NAME).getBanEntry(Bukkit.getOfflinePlayer(args[0]).getName()).getExpiration() != null) {
                    sb.append("&aExpiration: &e&l" + server.getBanList(BanList.Type.NAME).getBanEntry(Bukkit.getOfflinePlayer(args[0]).getName()).getExpiration() + "\n&b-----&r\n");
                } else {
                    sb.append("&aExpiration: &c&lNever\n&b-----&r\n");
                }
                sb.append("&b-----\n");
            }else{
                sender.sendMessage(ChatColor.RED + "That player does not have a ban entry.");
            }

        }else{
            sender.sendMessage(ChatColor.RED + "Invalid amount of arguments.");
        }
        sender.sendMessage(ChatColor.YELLOW + "===========================================");
        return true;
    }
}
