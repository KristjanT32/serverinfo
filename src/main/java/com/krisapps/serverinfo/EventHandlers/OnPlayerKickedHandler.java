package com.krisapps.serverinfo.EventHandlers;

import com.krisapps.serverinfo.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerKickEvent;

public class OnPlayerKickedHandler implements Listener {

    ServerInfo main;
    public OnPlayerKickedHandler(ServerInfo main){
        this.main = main;
    }

    @EventHandler
    public void OnPlayerKicked(PlayerKickEvent kickEvent){
        if ((Boolean) main.configurationFile.get("config.announceKick")) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', main.configurationFile.getString("config.kickMessage").replace("%player%", kickEvent.getPlayer().getDisplayName()).replace("%reason%", kickEvent.getReason())));
        }
    }
}
