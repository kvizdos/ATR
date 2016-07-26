package com.kentonvizdos.ATR;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Reportexecutor implements CommandExecutor {


    private final main PLUGIN;

    public Reportexecutor (main plugin) {
        this.PLUGIN = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {


        Player player = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("report") && player.hasPermission("at.report")) {
            String mess = "";

            for(int i = 1; i < args.length; i++){
                String arg = args[i] + " ";
                mess = mess + arg;
            }

            Player target = Bukkit.getServer().getPlayer(args[0]);

            if(args.length == 1) {
                player.sendMessage(PLUGIN.error + "Do /report <player> <reason>!");
            } else {
                if(args.length == 0) {
                    player.sendMessage(PLUGIN.error + "Please Specify A Player!");
                } else {
                    player.sendMessage(PLUGIN.prefix + "Successfully Reported " + ChatColor.RED + target.getName() + ChatColor.AQUA + " for the reason: " + ChatColor.RED + mess);
                    PLUGIN.getConfig().addDefault("Reported." + target.getName() + ".Report", mess);
                    PLUGIN.getConfig().addDefault("Reported." + target.getName() + ".Reporter", player.getName());
                    PLUGIN.saveConfig();

                    Bukkit.broadcast(PLUGIN.prefix + target.getName() + " was reported by " + player.getName() + " for the reason: " + mess, "at.recieve");
                }
            }

        }
        return false;
    }


}