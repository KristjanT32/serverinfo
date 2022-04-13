package com.krisapps.serverinfo.BanHistory;

import com.krisapps.serverinfo.ServerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.io.IOException;

public class PurgeHistory implements CommandExecutor {

    ServerInfo main;
    public PurgeHistory(ServerInfo main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /purgehistory <player> [id]
        if (args.length == 1){
            if (main.banHistoryFile.get("history." + args[0] + ".bans") == null){
                sender.sendMessage(ChatColor.RED + "This player does not have any ban history.");
            }else{
                if (main.configurationFile.getBoolean("config.logHistoryDeletion")){
                    main.getLogger().warning("|HistoryManager|: Purge request received for " + args[0]);
                }
                long start = System.currentTimeMillis();
                for (String entry: main.banHistoryFile.getConfigurationSection("history." + args[0] + ".bans").getKeys(false)){
                    main.banHistoryFile.set("history." + args[0] + ".bans." + entry, null);
                    main.banHistoryFile.set("history." + args[0] + ".banCount", Integer.valueOf(main.banHistoryFile.getInt("history." + args[0] + ".banCount") - 1));
                    if (main.configurationFile.getBoolean("config.logHistoryDeletion")){
                        main.getLogger().info("|HistoryManager|: Purging entry '" + entry + "'...");
                    }
                }
                try {
                    main.banHistoryFile.save(main.banHistoryConf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (main.banHistoryFile.getInt("history." + args[0] + ".banCount") <= 1) sender.sendMessage(ChatColor.GREEN + "Purge operation " + ChatColor.BOLD + "complete!");
                if (main.configurationFile.getBoolean("config.logHistoryDeletion")){
                    main.getLogger().info("|HistoryManager|: Purge operation completed in " + (System.currentTimeMillis() - start) + "ms");
                }
            }
        }else if (args.length == 2){
            if (main.banHistoryFile.get("history." + args[0] + ".bans." + args[1]) != null){
                main.banHistoryFile.set("history." + args[0] + ".bans." + args[1], null);
                main.banHistoryFile.set("history." + args[0] + ".banCount", Integer.valueOf(main.banHistoryFile.getInt("history." + args[0] + ".banCount") - 1));
                try {
                    main.banHistoryFile.save(main.banHistoryConf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eEntry '&b" + args[1] + "&e' successfully deleted."));
            }else{
                sender.sendMessage(ChatColor.RED + "This player does not have any ban history.");
            }

        }else{
            sender.sendMessage(ChatColor.RED + "Invalid syntax.");
            return false;
        }
        return true;
    }
}
