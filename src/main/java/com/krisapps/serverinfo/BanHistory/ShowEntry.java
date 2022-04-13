package com.krisapps.serverinfo.BanHistory;

import com.krisapps.serverinfo.ServerInfo;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.index.qual.SubstringIndexUnknown;

public class ShowEntry implements CommandExecutor {

    ServerInfo main;
    public ShowEntry(ServerInfo main){
        this.main = main;
    }

    TextComponent createHoverable(String text, String hoverText){
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(text));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText)));
        component.setBold(false);
        return component;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /showbanentry <targetUUID> <entryID>
        if (args.length == 2) {
            String uuid = args[0];
            if (main.banHistoryFile.getConfigurationSection("history." + uuid) == null) {
                sender.sendMessage(ChatColor.RED + "There is no ban history available for this player.");
            }

            if (main.banHistoryFile.getConfigurationSection("history." + uuid + ".bans").getKeys(false).contains(args[1])) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eBan Entry &b" + args[1] + " &e:&r"));
                sender.spigot().sendMessage(createHoverable(ChatColor.translateAlternateColorCodes('&', "&aBanned Player&e:&d " + uuid), ChatColor.translateAlternateColorCodes('&', "&bPlayer Unique ID\n&d " + main.banHistoryFile.getString("history." + uuid+ ".bans." + args[1] + ".playerUUID"))));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aBan Date&e:&d " + main.banHistoryFile.getString("history." + uuid + ".bans." + args[1] + ".banDate")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aBan Reason&e:&d " + main.banHistoryFile.getString("history." + uuid + ".bans." + args[1] + ".reason")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aBanned By&e:&d " + main.banHistoryFile.getString("history." + uuid + ".bans." + args[1] + ".bannedBy")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e==============================================="));
            } else {
                sender.sendMessage("There's no such ban entry for this player.");
            }
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid syntax."));
            return false;
        }
        return true;
    }
}
