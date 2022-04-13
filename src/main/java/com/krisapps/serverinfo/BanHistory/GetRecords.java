package com.krisapps.serverinfo.BanHistory;

import com.krisapps.serverinfo.ServerInfo;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.swing.*;

public class GetRecords implements CommandExecutor {

    ServerInfo main;
    HistoryManager hm;

    public GetRecords(ServerInfo main){
        this.main = main;
    }

    TextComponent createClickable(String entryID, String text, String uuid){
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text)));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/showbanentry " + uuid + " " + entryID));
        component.setBold(false);
        return component;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /getbans <player>
        hm = new HistoryManager(main, main.getLogger());
        String uuid = args[0];

        if (args.length == 1) {
            if (hm.hasRecords(uuid) || hm.hasRecords(uuid)) {
                sender.sendMessage(ChatColor.YELLOW + "===========================================");
                for (String entry : main.banHistoryFile.getConfigurationSection("history." + uuid + ".bans").getKeys(false)) {
                    sender.spigot().sendMessage(createClickable(entry, "&eBan &b[&a" + entry + "&b]", uuid));
                }
                sender.sendMessage(ChatColor.YELLOW + "===========================================");
            } else {
                sender.sendMessage(ChatColor.RED + "This player has no ban history.");
            }
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid syntax."));
            return false;
        }
        return true;
    }
}
