package com.krisapps.serverinfo.BanHistory;

import com.krisapps.serverinfo.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

public class HistoryManager {

    public ServerInfo main;
    public Logger logger;

    public HistoryManager(ServerInfo main, Logger logger) {
        this.main = main;
        this.logger = logger;
    }


    //Add a record to the provided player's ban history.
    public void addRecord(String uuid, String reason, String by) throws Exception {
        String playerName;
        String uuID = "";
        if (Bukkit.getPlayer(uuid) == null && Bukkit.getOfflinePlayer(uuid) == null) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b&l[&r&bBanHistory&b&l]&r&e: &aCould not add a ban history record. This player does not exist."));
            return;
        }else if (Bukkit.getPlayer(uuid) == null){
            playerName = Bukkit.getOfflinePlayer(uuid).getName();
        }else{
            playerName = Bukkit.getPlayer(uuid).getDisplayName();
            uuID = Bukkit.getPlayer(uuid).getUniqueId().toString();
        }
        String banID;
        int index = main.banHistoryFile.getInt("history." + uuid + ".banCount") | 1;
        banID = (new Date() + "-ID-" + index).replace(' ', '-').replace(':', '-');

        main.banHistoryFile.set("history." + uuid + ".bans." + banID + ".player", playerName);
        main.banHistoryFile.set("history." + uuid + ".bans." + banID + ".playerUUID", Bukkit.getOfflinePlayer(playerName).getUniqueId().toString());
        main.banHistoryFile.set("history." + uuid + ".bans." + banID + ".banDate", new Date());
        main.banHistoryFile.set("history." + uuid + ".bans." + banID + ".reason", reason);
        main.banHistoryFile.set("history." + uuid + ".bans." + banID + ".bannedBy", by);

        try {
            main.banHistoryFile.set("history." + uuid + ".banCount", Integer.valueOf(main.banHistoryFile.getInt("history." + uuid + ".banCount") + 1));
            main.banHistoryFile.save(main.banHistoryConf);
        } catch (IOException e) {
            main.getLogger().info("An error occurred while adding a new ban entry for " + playerName);
            e.printStackTrace();
        }
    }

    //Clear player's records.
    public void clearRecords(String uuid) throws IOException {
        if (main.banHistoryFile.get("history." + uuid + ".bans") != null) {
            main.banHistoryFile.set("history." + uuid + ".bans", null);
        } else {
            main.getLogger().info("This player has no ban history to clear.");
        }
        try {
            main.banHistoryFile.save(main.banHistoryConf);
            main.getLogger().info("Cleared records for " + Bukkit.getPlayer(uuid).getDisplayName());
        } catch (IOException e) {
            main.getLogger().info("Could not clear records for " + Bukkit.getPlayer(uuid).getDisplayName());
            e.printStackTrace();
        }
    }

    //Get the amount of records for the provided player.
    public int getRecords(String uuid) {
        if (main.banHistoryFile.getConfigurationSection("history." + Bukkit.getOfflinePlayer(uuid).getUniqueId() + ".bans") != null) {
            return main.banHistoryFile.getConfigurationSection("history." + Bukkit.getOfflinePlayer(uuid).getUniqueId() + ".bans").getKeys(false).size();
        } else {
            return 0;
        }
    }

    public boolean hasRecords(String uuid) {
        return main.banHistoryFile.get("history." + uuid + ".bans") != null;
    }

}

