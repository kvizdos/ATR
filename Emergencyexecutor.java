package com.kentonvizdos.ATR;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Emergencyexecutor  implements CommandExecutor {

    private final main PLUGIN;

    public Emergencyexecutor (main plugin) {
        this.PLUGIN = plugin;
    }


    String error = ChatColor.DARK_RED + "[AdminTools] Error in syntax! ";
    String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "AdminTools" + ChatColor.DARK_AQUA + "] " + ChatColor.AQUA;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("911") && player.hasPermission("at.emergency")) {
            String mess = "";

            for(int i = 1; i < args.length; i++){
                String arg = args[i] + " ";
                mess = mess + arg;
            }

            if(args.length == 1) {
                player.sendMessage(error + "Do /911 <hidden/visible> <reason>!");
            } else {
                if(args[0].equalsIgnoreCase("hidden")) {
                    player.sendMessage(prefix + "A Invisible Admin Is On His/Her Way!");
                    Bukkit.broadcast(PLUGIN.prefix + player.getName() + " has requested a invisible admin for the reason: " + ChatColor.GREEN + mess, "at.recieve");
                }

                if(args[0].equalsIgnoreCase("visible")) {
                    player.sendMessage(prefix + "A Visible Admin Is On His/Her Way!");
                    Bukkit.broadcast(prefix + player.getName() + " has requested a visible admin for the reason: " + ChatColor.GREEN + mess, "at.recieve");
                }


            }
        }		return false;
    }


}
