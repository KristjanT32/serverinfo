package com.krisapps.serverinfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Locale;

public class PlayerInfo implements CommandExecutor {

    public void getPlayerInfo(String playerName, CommandSender sender){
        if (Bukkit.getPlayer(playerName) != null){
            Player player = Bukkit.getPlayer(playerName);

            String tagString = "";
            StringBuilder tags = new StringBuilder();
            if (player.getScoreboardTags().size() > 0){
                for (String tag: player.getScoreboardTags()){
                    tags.append(tag).append(", ");
                }
                tagString = tags.toString();
                tagString = tagString.substring(0, tagString.length() - 2);
            }else{
                tagString = "No tags.";
            }

            String bedLocation;
            if (player.getBedSpawnLocation() != null){
                bedLocation = String.format("X: %s, Y: %s, Z: %s", player.getBedSpawnLocation().getX(), player.getBedSpawnLocation().getY(), player.getBedSpawnLocation().getZ());
            }else{
                bedLocation = "No bed found.";
            }


            StringBuilder effects = new StringBuilder();
            if (player.getActivePotionEffects().size() > 0) {
                for (PotionEffect effect : player.getActivePotionEffects()) {
                    effects.append("&aminecraft:" + effect.getType().getName().toLowerCase(Locale.ROOT) + " &bfor&d " + effect.getDuration() + "&bs&e of level &d" + effect.getAmplifier() + "\n");
                }
            }else{
                effects.append("&cNo active potion effects.");
            }

            String status;
            if (player.isOp()){
                status = "Operator";
            }else{
                status = "Player";
            }

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&e============================================\n" +
                    "&aNickname: &b%s\n" +
                    "&aHealth: &b%s HP\n" +
                    "&aStatus: &b%s\n" +
                    "&aGamemode: &b%s\n" +
                    "&aTags: &b%s\n" +
                    "&aBed Location: &b%s\n" +
                    "&aActive Effects\n" +
                    "&e----------------------------------------------\n" +
                    "%s\n" +
                    "&e----------------------------------------------\n" +
                    "&aLatest Ping: &b%sms\n" +
                    "\n&e============================================", player.getDisplayName(), player.getHealth(), status,  player.getGameMode().toString().charAt(0) + player.getGameMode().toString().substring(1).toLowerCase(Locale.ROOT), tagString, bedLocation, effects, player.getPing())));

        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player appears to be offline."));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /playerinfo <player>

        if (sender instanceof Player){
            if (args.length > 0) {
                getPlayerInfo(args[0], sender);
            }else{
                sender.sendMessage(ChatColor.RED + "Invalid number of arguments provided. (Expected: 1)");
            }
        }else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSorry, but you have to be a player to execute this command."));
        }
        return true;
    }
}
