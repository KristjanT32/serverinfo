package com.krisapps.serverinfo.TabCompletion;

import com.krisapps.serverinfo.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PurgeHistory implements TabCompleter {

    ServerInfo main;
    public PurgeHistory(ServerInfo main){
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1){
            for (Player p: Bukkit.getOnlinePlayers()){
                completions.add(p.getDisplayName());
            }
        }else if (args.length == 2){
            try {
                for (String entry : main.banHistoryFile.getConfigurationSection("history." + args[0] + ".bans").getKeys(false)) {
                    completions.add(entry);
                }
            }catch (NullPointerException npe){

            }
        }

        return completions;
    }
}
