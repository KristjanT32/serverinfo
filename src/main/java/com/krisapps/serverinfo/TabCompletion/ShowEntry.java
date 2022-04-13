package com.krisapps.serverinfo.TabCompletion;

import com.krisapps.serverinfo.ServerInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ShowEntry implements TabCompleter {

    ServerInfo main;
    public ShowEntry(ServerInfo main){
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1){
            if (main.banHistoryFile.getConfigurationSection("history." + args[0]) != null){
                for (String player: main.banHistoryFile.getConfigurationSection("history." + args[0]).getKeys(false)){
                    completions.add(player);
                }
            }
        }else if (args.length == 2){
            if (main.banHistoryFile.getConfigurationSection("history." + args[0] + ".bans." + args[1]) != null){
                for (String entry: main.banHistoryFile.getConfigurationSection("history." + args[0] + ".bans." + args[1]).getKeys(false)){
                    if (!entry.equalsIgnoreCase("bans") && !entry.equalsIgnoreCase("banCount")) {
                        completions.add(entry);
                    }
                }
            }
        }

        return completions;
    }
}
