package com.kentonvizdos.ATR;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatColor;

public class Atexecutor implements CommandExecutor {
	public boolean voteInprogress = false;

	private final main PLUGIN;

	public Atexecutor(main plugin) {
		this.PLUGIN = plugin;
	}

	String error = ChatColor.DARK_RED + "[AdminTools] Error in syntax! ";
	String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "AdminTools" + ChatColor.DARK_AQUA + "] "
			+ ChatColor.AQUA;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("at")) {
			if (args.length == 0) {
				player.sendMessage(ChatColor.AQUA + "===--------------------------------------===");
				player.sendMessage(ChatColor.AQUA + "===-------------" + ChatColor.DARK_AQUA + "ADMIN  TOOLS"
						+ ChatColor.AQUA + "--------------===");
				player.sendMessage(ChatColor.AQUA + "COMMAND | DESCRIPTION | PERMISSION");
				player.sendMessage(ChatColor.DARK_AQUA + "1) " + ChatColor.AQUA + "/at bc <message>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Send A Message To Everyone!"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.bc");
				player.sendMessage(ChatColor.DARK_AQUA + "2) " + ChatColor.AQUA + "/at gm <c/s/a/sp>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Change Your Gamemode!" + ChatColor.DARK_AQUA
						+ " | " + ChatColor.AQUA + "at.gm");
				player.sendMessage(ChatColor.DARK_AQUA + "3) " + ChatColor.AQUA + "/at ban <player> <reason>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Ban A Player!" + ChatColor.DARK_AQUA + " | "
						+ ChatColor.AQUA + "at.ban");
				player.sendMessage(ChatColor.DARK_AQUA + "4) " + ChatColor.AQUA + "/at config" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Show Configurations" + ChatColor.DARK_AQUA
						+ " | " + ChatColor.AQUA + "at.config");
				player.sendMessage(ChatColor.DARK_AQUA + "5) " + ChatColor.AQUA + "/at freeze <player>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Freeze A player (toggles)"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.freeze");
				player.sendMessage(ChatColor.DARK_AQUA + "6) " + ChatColor.AQUA + "/at spec <player>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Spectate a Player!" + ChatColor.DARK_AQUA
						+ " | " + ChatColor.AQUA + "at.spec");
				player.sendMessage(ChatColor.DARK_AQUA + "7) " + ChatColor.AQUA + "/at heal <player/all>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Heal !" + ChatColor.DARK_AQUA + " | "
						+ ChatColor.AQUA + "at.heal");
				player.sendMessage(ChatColor.DARK_AQUA + "8) " + ChatColor.AQUA + "/at warn <player>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Warn A player !" + ChatColor.DARK_AQUA + " | "
						+ ChatColor.AQUA + "at.warn");
				player.sendMessage(ChatColor.DARK_AQUA + "9) " + ChatColor.AQUA + "/at research <player>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Get More info About a player"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.research");
				player.sendMessage(ChatColor.DARK_AQUA + "10) " + ChatColor.AQUA + "/911 <reason>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Have Players Contact Staff For An Emergancy"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.emergancy");
				player.sendMessage(ChatColor.DARK_AQUA + "11) " + ChatColor.AQUA + "/report <player> <reason>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Report a player!" + ChatColor.DARK_AQUA
						+ " | " + ChatColor.AQUA + "at.report");
				player.sendMessage(ChatColor.DARK_AQUA + "12) " + ChatColor.AQUA + "/at invcheck <player>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Look at a players inventory"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.invcheck");
				player.sendMessage(ChatColor.DARK_AQUA + "13) " + ChatColor.AQUA + "/at blacklist <add/remove> <word>"
						+ "" + ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Add/Remove a word from blacklist!"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.blacklist");
				player.sendMessage(ChatColor.DARK_AQUA + "14) " + ChatColor.AQUA + "/at blacklisted" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Displays All Blacklisted Words"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.blacklisted");
				player.sendMessage(ChatColor.DARK_AQUA + "14) " + ChatColor.AQUA + "/at contributors" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Displays All Contributors That Have Helped!"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.contributors");
				player.sendMessage(ChatColor.DARK_AQUA + "15) " + ChatColor.AQUA + "/at voteban <player> <reason> || /at voteban <yes/no>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Start the voteban || Vote for the voteban"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.voteban");
				player.sendMessage(ChatColor.DARK_AQUA + "16) " + ChatColor.AQUA + "/at votekick <player> <reason> || /at votekick <yes/no>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Start the votekick || Vote for the votekick"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.votekick");
				player.sendMessage(ChatColor.DARK_AQUA + "17) " + ChatColor.AQUA + "/at mute <player> <reason>" + ""
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "Mutes a player of your choice!"
						+ ChatColor.DARK_AQUA + " | " + ChatColor.AQUA + "at.mute");
				player.sendMessage(prefix
						+ "AdminTools Created By Kenton Vizdos \n (DOESNT WORK ATM) www.kentonvizdos.com/minecraft/plugins/AdminTools.php");

			}
			// BC
			else if (args[0].equalsIgnoreCase("bc") && player.hasPermission("at.bc")) {
				if (args.length != 1) {
					String mess = "";

					for (int i = 1; i < args.length; i++) {
						String arg = args[i] + " ";
						mess = mess + arg;
					}

					Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "BROADCAST"
							+ ChatColor.DARK_RED + "] " + ChatColor.AQUA + mess); // send
					// the
					// message
					// to
					// the
					// command
					// sender.
				} else {
					player.sendMessage(error + "Do /at bc <message>");
				}
			}
			// gm
			else if (args[0].equalsIgnoreCase("gm")) {
				if (args.length != 2) {
					player.sendMessage(error + "Do /at gm <c/s/a/sp>");
				} else if (args[1].equalsIgnoreCase("c")) {
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(prefix + "Gamemode set to Creative!");
				} else if (args[1].equalsIgnoreCase("s")) {
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(prefix + "Gamemode set to Survival!");
				} else if (args[1].equalsIgnoreCase("a")) {
					player.setGameMode(GameMode.ADVENTURE);
					player.sendMessage(prefix + "Gamemode set to Adventure!");
				} else if (args[1].equalsIgnoreCase("sp")) {
					player.setGameMode(GameMode.SPECTATOR);
					player.sendMessage(prefix + "Gamemode set to Spectator/!");
				} else {
					player.sendMessage(error + "Do /at gm <c/s/a/sp>");
				}
			}
			// BAN
			else if (args[0].equalsIgnoreCase("ban") && player.hasPermission("at.ban")) {

				if (args.length != 2) {
					Player target = Bukkit.getServer().getPlayer(args[1]);
					String mess = "";

					for (int i = 2; i < args.length; i++) {
						String arg = args[i] + " ";
						mess = mess + arg;
					}
					if (target != null && player.hasPermission("at.unbannable")) {

						player.sendMessage(prefix + "Banned " + target.getName() + "!");
						target.kickPlayer(ChatColor.RED + "You Have Been Banned! \n Reason: " + ChatColor.DARK_RED
								+ "The Ban Hammer Has Spoken!" + ChatColor.RED + " \n Banned By: " + ChatColor.DARK_RED
								+ player.getName() + ChatColor.RED + "\n Appeal Here: \n" + ChatColor.DARK_RED
								+ PLUGIN.getConfig().getString("Settings.Ban.AppealLink"));

						PLUGIN.getConfig().set("Players.Banned." + target.getUniqueId() + ".Reason",
								"The Ban Hammer Has Spoken!");
						PLUGIN.getConfig().set("Players.Banned." + target.getUniqueId() + ".Banner", player.getName());

						PLUGIN.saveConfig();
					} else {
						player.sendMessage(error + "Player Not Found");
					}

				} else {
					player.sendMessage(error + "Do /ban <player>");

				}
			}
			// KICK
			else if (args[0].equalsIgnoreCase("kick") && player.hasPermission("at.kick")) {

				if (args.length != 2) {
					Player target = Bukkit.getServer().getPlayer(args[1]);
					String mess = "";

					for (int i = 2; i < args.length; i++) {
						String arg = args[i] + " ";
						mess = mess + arg;
					}
					if (target != null && player.hasPermission("at.unbannable")) {
						target.kickPlayer(ChatColor.RED + "You Have Been Kicked! \n Reason: " + ChatColor.DARK_RED
								+ mess + ChatColor.RED + "\n Kicked By: " + ChatColor.DARK_RED + player.getName());
					} else {
						player.sendMessage(error + "Player Not Found");
					}

				} else {

					player.sendMessage(error + "Do /ban <player>");

				}
			}
			// CONFIG
			else if (args[0].equalsIgnoreCase("config") && player.hasPermission("at.config")) {
				if (args.length == 1) {
					player.sendMessage(
							prefix + "Config Settings (Do /at config <setting to change> To See More info on them!");
					player.sendMessage(ChatColor.BLUE + "1) " + ChatColor.AQUA + "Join Message: " + ChatColor.DARK_AQUA
							+ PLUGIN.getConfig().getString("Settings.Join.Msg") + ChatColor.AQUA + " | joinmsg | "
							+ ChatColor.RESET
							+ PLUGIN.getConfig().getString("Settings.Join.Msg").replaceAll("PLAYER", player.getName())
									.replaceAll("RED", ChatColor.RED + "").replaceAll("BLUE", ChatColor.BLUE + "")
									.replaceAll("GREEN", ChatColor.GREEN + "").replaceAll("GOLD", ChatColor.GOLD + "")
									.replaceAll("GRAY", ChatColor.GRAY + "").replaceAll("DG", ChatColor.DARK_GRAY + "")
									.replaceAll("AQUA", ChatColor.AQUA + ""));
					player.sendMessage(ChatColor.BLUE + "2) " + ChatColor.AQUA + "Leave Message: " + ChatColor.DARK_AQUA
							+ PLUGIN.getConfig().getString("Settings.Leave.Msg") + ChatColor.AQUA + " | leavemsg | "
							+ ChatColor.RESET
							+ PLUGIN.getConfig().getString("Settings.Leave.Msg").replaceAll("PLAYER", player.getName())
									.replaceAll("RED", ChatColor.RED + "").replaceAll("BLUE", ChatColor.BLUE + "")
									.replaceAll("GREEN", ChatColor.GREEN + "").replaceAll("GOLD", ChatColor.GOLD + "")
									.replaceAll("GRAY", ChatColor.GRAY + "").replaceAll("DG", ChatColor.DARK_GRAY + "")
									.replaceAll("AQUA", ChatColor.AQUA + ""));
				} else if (args[1].equalsIgnoreCase("joinmsg")) {
					if (args.length == 2) {
						player.sendMessage(prefix + "Do /at config joinmsg <join message>!");
						player.sendMessage(ChatColor.AQUA + "Chat Color Codes");
						player.sendMessage(ChatColor.RED + "RED = Red Color");
						player.sendMessage(ChatColor.BLUE + "BLUE = Blue Color");
						player.sendMessage(ChatColor.GREEN + "GREEN = Green Color");
						player.sendMessage(ChatColor.GOLD + "GOLD = Gold Color");
						player.sendMessage(ChatColor.GRAY + "GRAY = Gray Color");
						player.sendMessage(ChatColor.DARK_GRAY + "DG = Dark Gray Color");
						player.sendMessage(ChatColor.AQUA + "AQUA = Aqua Color");

					} else {
						String mess = "";

						for (int i = 2; i < args.length; i++) {
							String arg = args[i] + " ";
							mess = mess + arg;
						}

						PLUGIN.getConfig().set("Settings.Join.Msg", mess);
						PLUGIN.saveConfig();
						PLUGIN.reloadConfig();
						player.sendMessage(prefix + "Correct?: "
								+ PLUGIN.stripColorFromConfig(PLUGIN.getConfig().getString("Settings.Join.Msg")));
					}
				} else if (args[1].equalsIgnoreCase("leavemsg")) {
					if (args.length == 2) {
						player.sendMessage(prefix + "Do /at config leavemsg <join message>!");
						player.sendMessage(ChatColor.AQUA + "Chat Color Codes");
						player.sendMessage(ChatColor.RED + "RED = Red Color");
						player.sendMessage(ChatColor.BLUE + "BLUE = Blue Color");
						player.sendMessage(ChatColor.GREEN + "GREEN = Green Color");
						player.sendMessage(ChatColor.GOLD + "GOLD = Gold Color");
						player.sendMessage(ChatColor.GRAY + "GRAY = Gray Color");
						player.sendMessage(ChatColor.DARK_GRAY + "DG = Dark Gray Color");
						player.sendMessage(ChatColor.AQUA + "AQUA = Aqua Color");
					} else {
						String mess = "";

						for (int i = 2; i < args.length; i++) {
							String arg = args[i] + " ";
							mess = mess + arg;
						}

						PLUGIN.getConfig().set("Settings.Leave.Msg", mess);
						PLUGIN.saveConfig();
						PLUGIN.reloadConfig();

						player.sendMessage(prefix + "Correct?: "
								+ PLUGIN.stripColorFromConfig(PLUGIN.getConfig().getString("Settings.Leave.Msg")));
					}
				}
			}
			// FREEZE
			else if (args[0].equalsIgnoreCase("freeze") && player.hasPermission("at.freeze")) {

				if (args.length == 0) {
					sender.sendMessage(error + ChatColor.RED + "Please specify a player!");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[1]);
				if (target == null) {
					sender.sendMessage(error + ChatColor.RED + "Could not find player " + args[0] + "!");
					return true;
				}
				if (PLUGIN.frozen.contains(target.getName())) {
					PLUGIN.frozen.remove(target.getName());
					sender.sendMessage(
							prefix + ChatColor.GREEN + "Player " + target.getName() + " has been unPLUGIN.frozen!");
					return true;
				}
				PLUGIN.frozen.add(target.getName());
				sender.sendMessage(
						prefix + ChatColor.GREEN + "Player " + target.getName() + " has been PLUGIN.frozen!");
			}
			// SPEC
			else if (args[0].contains("spec") && player.hasPermission("at.spec")) {
				if (args.length == 0) {
					sender.sendMessage(error + ChatColor.RED + "Please specify a player!");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[1]);
				if (target == null) {
					player.sendMessage(error + "Player Not Found.");
					return true;
				} else {
					if (PLUGIN.spectating == false) {
						player.teleport(target);
						for (Player players : Bukkit.getOnlinePlayers()) {
							players.hidePlayer(player);
							player.sendMessage(prefix + "You Are Invisible To All Players And PLUGIN.spectating "
									+ ChatColor.BLUE + target.getName());
							PLUGIN.spectating = true;
						}
					} else {
						for (Player players : Bukkit.getOnlinePlayers()) {
							players.showPlayer(player);
							player.sendMessage(
									prefix + "You have stopped PLUGIN.spectating " + ChatColor.BLUE + target.getName());
							PLUGIN.spectating = false;
						}
					}

				}
			}
			// HEAL
			else if (args[0].equalsIgnoreCase("heal") && player.hasPermission("at.heal")) {
				if (args.length == 1) {
					player.setHealth(20);
					player.sendMessage(prefix + "Healed!");

				} else {
					Player target = Bukkit.getServer().getPlayer(args[1]);

					if (target != null) {
						target.setHealth(20);
						target.sendMessage(prefix + "Healed By " + player.getName() + "!");
						player.sendMessage(prefix + "Healed " + target.getName() + "!");

					}

					if (args[1].equalsIgnoreCase("all")) {
						for (Player s : Bukkit.getOnlinePlayers()) {
							s.setHealth(20);
							s.sendMessage(prefix + player.getName() + " has healed you!");
						}
					}

				}
			}
			// WARN
			else if (args[0].equalsIgnoreCase("warn") && player.hasPermission("at.warn")) {
				Player target = Bukkit.getPlayer(args[1]);

				if (target != null) {
					if (args.length == 2) {
						player.sendMessage(prefix + ChatColor.GOLD + target.getName() + ChatColor.AQUA + " has "
								+ PLUGIN.getConfig().getString("Players.Warns." + target.getName()) + "/3 warn(s)!");
						return true;
					}
					if (args[2].equalsIgnoreCase("add")) {
						String mess = "";

						for (int i = 2; i < args.length; i++) {
							String arg = args[i] + " ";
							mess = mess + arg;
						}

						player.sendMessage(prefix + "Added 1 Warn to " + target.getName() + "!");
						PLUGIN.getConfig().set("Players.Warns." + target.getName(),
								PLUGIN.getConfig().getInt("Players.Warns." + target.getName(), 0) + 1);
						PLUGIN.saveConfig();
						target.sendMessage(prefix + "You Have Been Warned! "
								+ PLUGIN.getConfig().getString("Players.Warns." + target.getName()) + "/3 Warns!");
					}
				} else {
					player.sendMessage(error + "Please specify a valid, online player!");
				}
			}
			// RESEARCH
			else if (args[0].equalsIgnoreCase("research") && player.hasPermission("at.research")) {
				Player target = Bukkit.getPlayer(args[1]);

				if (target != null) {
					player.sendMessage(ChatColor.AQUA + "More Info About " + ChatColor.GREEN + target.getName());
					player.sendMessage(ChatColor.BLUE + "Name: " + ChatColor.GREEN + target.getName());
					player.sendMessage(ChatColor.BLUE + "UUID: " + ChatColor.GREEN + target.getUniqueId());
					player.sendMessage(ChatColor.BLUE + "IP: " + ChatColor.GREEN + target.getAddress());
				} else {
					player.sendMessage(error + "Invalid Player!");
				}

			}
			// Look at a players inventory
			else if (args[0].equalsIgnoreCase("invcheck") && player.hasPermission("at.invcheck")) {
				Player target = Bukkit.getServer().getPlayer(args[1]);

				Inventory targetInv = target.getInventory();

				if (target != null) {
					player.openInventory(targetInv);

					player.sendMessage(prefix + "Now Looking at " + target.getName() + "'s Inventory.");
				}
			}
			// Add a word to blacklist
			else if (args[0].equalsIgnoreCase("blacklist") && player.hasPermission("at.blacklist")) {
				if (args[1].equalsIgnoreCase("add")) {
					if (!PLUGIN.getConfig().getList("badwords").contains(args[2])) {
						player.sendMessage(prefix + "Added " + args[2] + " to the blacklist!");
						@SuppressWarnings("unchecked")
						List<String> black = (List<String>) this.PLUGIN.getConfig().getList("badwords");
						black.add(args[2]);

						PLUGIN.saveConfig();
						PLUGIN.reloadConfig();
					} else {
						player.sendMessage(error + "That word is already on the blacklist");
					}
				}

				if (args[1].equalsIgnoreCase("remove")) {
					player.sendMessage(prefix + "Removed " + args[2] + " from the blacklist!");
					@SuppressWarnings("unchecked")
					List<String> black = (List<String>) this.PLUGIN.getConfig().getList("badwords");
					black.remove(args[2]);

					PLUGIN.saveConfig();
					PLUGIN.reloadConfig();
				}

			}
			// What words are on blacklist
			else if (args[0].equalsIgnoreCase("blacklisted") && player.hasPermission("at.blacklisted")) {
				@SuppressWarnings("unchecked")
				List<String> black = (List<String>) this.PLUGIN.getConfig().getList("badwords");
				player.sendMessage(prefix + "Dont Say: " + black);

			}

			// Contributors
			else if (args[0].equalsIgnoreCase("contributors") && player.hasPermission("at.contributors")) {
				player.sendMessage(prefix + "Thanks To All Of The Amazing Contributors:\n" + ChatColor.AQUA
						+ "Mr. Diamond: Helped With The GUI Idea, Permission ranks, and contributor list\n"
						+ "bwfcwalshy: Helped With Debugging\n");

			}
			// GUI
			else if (args[0].equalsIgnoreCase("gui") && player.hasPermission("at.gui")) {
				main.createDisplay(Material.DIAMOND_AXE, PLUGIN.cmdGUI, 1,
						ChatColor.RED + "" + ChatColor.BOLD + "The All Mighty Ban Hammer!", "Ban a Player!");
				main.createDisplay(Material.IRON_AXE, PLUGIN.cmdGUI, 2,
						ChatColor.RED + "" + ChatColor.BOLD + "The Kicker 9000", "Kick a Player!");
				main.createDisplay(Material.EYE_OF_ENDER, PLUGIN.cmdGUI, 3,
						ChatColor.RED + "" + ChatColor.BOLD + "Spectationeer", "Spectate a player!");
				main.createDisplay(Material.PACKED_ICE, PLUGIN.cmdGUI, 5,
						ChatColor.RED + "" + ChatColor.BOLD + "The Freezer", "Freeze a player!");
				main.createDisplay(Material.BEDROCK, PLUGIN.cmdGUI, 6,
						ChatColor.RED + "" + ChatColor.BOLD + "Gamiest Mode Changer", "Change yer Gamemode");
				main.createDisplay(Material.END_CRYSTAL, PLUGIN.cmdGUI, 7,
						ChatColor.RED + "" + ChatColor.BOLD + "Researcher", "Research a player");

				main.createDisplay(Material.BEDROCK, PLUGIN.gmGUI, 2, "Creative", "Be The Gawd");
				main.createDisplay(Material.REDSTONE_BLOCK, PLUGIN.gmGUI, 3, "Survival", "Survive.");
				main.createDisplay(Material.BARRIER, PLUGIN.gmGUI, 5, "Spectator", "This may be even more gawd..");
				main.createDisplay(Material.DIAMOND_SWORD, PLUGIN.gmGUI, 6, "Adventure", "Whats even the point?");

				player.openInventory(PLUGIN.cmdGUI);
			}
			// VOTE BAN
			// DiamondMCPro was here

			else if (args[0].equalsIgnoreCase("voteban") && player.hasPermission("at.voteban")) {
				Vote vb = PLUGIN.voteBans;

				if (args.length >= 2) {

					if (args[1].equalsIgnoreCase("yes")) {
						if (vb.hasVoteStarted() == true) {
							if (vb.getVoteName().equalsIgnoreCase(player.getName())) {
								player.sendMessage(ChatColor.RED + "You Cannot Vote On Your Own Vote");
								return true;
							}
							if (!vb.hasPlayerVoted(player.getUniqueId().toString())) {
								vb.addVoteYes(player.getUniqueId().toString());
								player.sendMessage(prefix + "You Voted Yes to ban the player! Current Ratio (Y/N): "
										+ vb.getVotesYes() + "/" + vb.getVotesNo());
							} else {
								player.sendMessage(ChatColor.RED + "[AdminTools] You Have Already Voted!");
							}
						}
					} else if (args[1].equalsIgnoreCase("no")) {
						if (vb.getVoteName().equalsIgnoreCase(player.getName())) {
							player.sendMessage(ChatColor.RED + "You Cannot Vote On Your Own Vote");
							return true;
						}
						if (vb.hasVoteStarted() == true) {
							if (!vb.hasPlayerVoted(player.getUniqueId().toString())) {
								vb.addVoteNo(player.getUniqueId().toString());
								player.sendMessage(prefix + "You Voted No to ban the player! Current Ratio (Y/N): "
										+ vb.getVotesYes() + "/" + vb.getVotesNo());
							} else {
								player.sendMessage(ChatColor.RED + "[AdminTools] You Have Already Voted!");
							}
						}
					} else {
						if (vb.hasVoteStarted() == false) {
							Player target = Bukkit.getPlayer(args[1]);

							if (target == null) {
								player.sendMessage("Player not online");
								return true;
							}

							String mess = "";

							if (args.length >= 3) {
								for (int i = 2; i < args.length; i++) {
									String arg = args[i] + "";
									mess = mess.concat(arg).concat(" ");
								}
							}

							if (args.length > 1) {
								Bukkit.broadcastMessage(prefix + "Started a vote ban for " + ChatColor.RED
										+ target.getName() + ChatColor.AQUA + " for the reason: " + ChatColor.RED + mess
										+ " do /at votekick <yes/no>!");

								vb.setVoteName(target.getName());
								vb.setVoteReason(mess);
								vb.startVote();
							} else {
								player.sendMessage(error + "Please do /at voteban <player> <reason>");
							}
						} else {
							player.sendMessage(ChatColor.RED + "[AdminTools] A Vote Is Already Started!");
						}
					}

				}

			}
			// VOTE KICK
			else if (args[0].equalsIgnoreCase("votekick") && player.hasPermission("at.votekick")) {
				Vote vk = PLUGIN.voteKick;

				if (args.length >= 2) {

					if (args[1].equalsIgnoreCase("yes")) {
						if (vk.hasVoteStarted() == true) {
							if (vk.getVoteName().equalsIgnoreCase(player.getName())) {
								player.sendMessage(ChatColor.RED + "You Cannot Vote On Your Own Vote");
								return true;
							}
							if (!vk.hasPlayerVoted(player.getUniqueId().toString())) {
								vk.addVoteYes(player.getUniqueId().toString());
								player.sendMessage(prefix + "You Voted Yes to kick the player! Current Ratio (Y/N): "
										+ vk.getVotesYes() + "/" + vk.getVotesNo());
							} else {
								player.sendMessage(ChatColor.RED + "[AdminTools] You Have Already Voted!");
							}
						}
					} else if (args[1].equalsIgnoreCase("no")) {
						if (vk.getVoteName().equalsIgnoreCase(player.getName())) {
							player.sendMessage(ChatColor.RED + "You Cannot Vote On Your Own Vote");
							return true;
						}
						if (vk.hasVoteStarted() == true) {
							if (!vk.hasPlayerVoted(player.getUniqueId().toString())) {
								vk.addVoteNo(player.getUniqueId().toString());
								player.sendMessage(prefix + "You Voted No to kick the player! Current Ratio (Y/N): "
										+ vk.getVotesYes() + "/" + vk.getVotesNo());
							} else {
								player.sendMessage(ChatColor.RED + "[AdminTools] You Have Already Voted!");
							}
						}
					} else {
						if (vk.hasVoteStarted() == false) {
							Player target = Bukkit.getPlayer(args[1]);

							if (target == null) {
								player.sendMessage("Player not online");
								return true;
							}

							String mess = "";

							if (args.length >= 3) {
								for (int i = 2; i < args.length; i++) {
									String arg = args[i] + "";
									mess = mess.concat(arg).concat(" ");
								}
							}

							if (args.length > 1) {
								Bukkit.broadcastMessage(prefix + "Started a vote kick for " + ChatColor.RED
										+ target.getName() + ChatColor.AQUA + " for the reason: " + ChatColor.RED + mess
										+ " do /at votekick <yes/no>!");

								vk.setVoteName(target.getName());
								vk.setVoteReason(mess);
								vk.startVote();
							} else {
								player.sendMessage(error + "Please do /at votekick <player> <reason>");
							}
						} else {
							player.sendMessage(ChatColor.RED + "[AdminTools] A Vote Is Already Started!");
						}
					}

				}

			}
			// MUTE
			if(args[0].equalsIgnoreCase("mute") && player.hasPermission("at.mute")) {
				Player target = Bukkit.getPlayer(args[1]);
				
				String mess = "";

				if (args.length >= 3) {
					for (int i = 2; i < args.length; i++) {
						String arg = args[i] + "";
						mess = mess.concat(arg).concat(" ");
					}
				}
				
				
				if(args.length < 2) {
					player.sendMessage(ChatColor.RED + "[AdminTools] Please specify a message!");
				} else {
					if(!PLUGIN.muted.contains(target.getName())) {
						PLUGIN.muted.add(target.getName());
						player.sendMessage(prefix + "Muted " + target.getName() + "!");
						target.sendMessage(prefix + "You have been muted by: " + ChatColor.RED + player.getName() + ChatColor.AQUA + ", for the reason: " + ChatColor.RED + mess);
					} else {
						player.sendMessage(prefix + "You unmuted " + target.getName());
						PLUGIN.muted.remove(target.getName());
					}
				}
				
			} 

		else {
			player.sendMessage(error + "That is not a command!");
		}
		}
		return true;

	}
}
