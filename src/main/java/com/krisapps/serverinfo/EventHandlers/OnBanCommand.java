package com.krisapps.serverinfo.EventHandlers;

import com.krisapps.serverinfo.BanHistory.HistoryManager;
import com.krisapps.serverinfo.ServerInfo;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.Arrays;

public class OnBanCommand implements Listener {

    ServerInfo main;
    public OnBanCommand(ServerInfo main){
        this.main = main;
    }

    @EventHandler
    public void OnPlayerBanned(PlayerCommandPreprocessEvent event) throws Exception {
        String[] command = event.getMessage().split(" ");
        StringBuilder reason = new StringBuilder();

        HistoryManager hm = new HistoryManager(main, main.getLogger());

        for (int i = 2; i < command.length; i++){
            reason.append(" ").append(command[i]);
        }

        if (reason.toString().equalsIgnoreCase("")){
            reason.append("Banned by an operator.");
        }

        if (command[0].replace('/', ' ').trim().equalsIgnoreCase("ban")){
            if (Bukkit.getBanList(BanList.Type.NAME).getBanEntry(command[1]) == null) {
                if ((Boolean) main.configurationFile.get("config.announceBan")) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', main.configurationFile.getString("config.banMessage").replace("%player%", command[1]).replace("%reason%", reason.toString())));
                    hm.addRecord(command[1], reason.toString(), event.getPlayer().getName());
                }
            }
        }
    }
}
