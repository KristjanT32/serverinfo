package com.krisapps.serverinfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ServerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /server

        if (sender instanceof Player){

            StringBuilder sb = new StringBuilder();
            Server server = Bukkit.getServer();
            sb.append("&aServer Type: &b").append(server.getName()).append("\n");
            if (!server.getIp().trim().equals("")) {
                sb.append("&aServer IP Address: &b").append(server.getIp()).append("\n");
            }else{
                sb.append("&aServer IP Address: &b" + "Not specified." + "\n");
            }
            sb.append("&aActive Bukkit Version: &b").append(server.getBukkitVersion()).append("\n");
            sb.append("&aServer MOTD: &r'").append(server.getMotd()).append("'\n");
            sb.append("&aServer Port: &b").append(server.getPort()).append("\n");

            StringBuilder worldString = new StringBuilder();
            worldString.append("\n&e-------------------------------------\n");
            for (World world: server.getWorlds()){
                worldString.append("&d" + world.getName()).append("\n");
            }
            worldString.append("&e-------------------------------------");

            sb.append("&aServer Worlds&b").append(worldString).append("\n");
            sb.append("&aActive Plugins: &b").append(Arrays.toString(server.getPluginManager().getPlugins()).replace('[', ' ').replace(']', ' ')).append("\n");

            sender.sendMessage(ChatColor.YELLOW + "======================================");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', sb.toString()));
            sender.sendMessage(ChatColor.YELLOW + "======================================");

        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSorry, but you have to be a player to execute this."));
        }
        return true;
    }
}
