package com.krisapps.serverinfo.TabCompletion;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class BanInfo implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1){
            for (OfflinePlayer offlinePlayer: Bukkit.getOfflinePlayers()){
                completions.add(offlinePlayer.getName());
            }
        }

        return completions;
    }
}
