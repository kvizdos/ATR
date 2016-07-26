package com.kentonvizdos.ATR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import net.md_5.bungee.api.ChatColor;

public final class main extends JavaPlugin implements Listener {

	Vote voteBans;
	Vote voteKick;
	ArrayList<String> banned = new ArrayList<String>();
	ArrayList<String> frozen = new ArrayList<String>();
	ArrayList<String> warns = new ArrayList<String>();
	ArrayList<String> muted = new ArrayList<String>();

	HashMap<String, Integer> beingVoteBanned = new HashMap<String, Integer>();
	ArrayList<String> guiBanReason = new ArrayList<String>();
	ArrayList<String> guiBanner = new ArrayList<String>();
	ArrayList<String> guiBanned = new ArrayList<String>();

	public boolean spectating = false;

	public void loadConfiguration() {

	}

	public String stripColorFromConfig(String config) {
		return config.replaceAll("RED", ChatColor.RED + "").replaceAll("BLUE", ChatColor.BLUE + "")
				.replaceAll("GREEN", ChatColor.GREEN + "").replaceAll("GOLD", ChatColor.GOLD + "")
				.replaceAll("GRAY", ChatColor.GRAY + "").replaceAll("DG", ChatColor.DARK_GRAY + "")
				.replaceAll("AQUA", ChatColor.AQUA + "");
	}
		

	@Override
	public void onEnable() {
		loadConfiguration();
		System.out.print("[TestPlugin] TestPlugin Enabled!");

		Bukkit.getServer().getPluginManager().registerEvents(this, this);

		saveDefaultConfig();
		getCommand("at").setExecutor(new Atexecutor(this));
		getCommand("911").setExecutor(new Emergencyexecutor(this));
		getCommand("report").setExecutor(new Reportexecutor(this));

		voteBans = new Vote(this, "", getConfig().getInt("Settings.AutoBan.Length")) {
			@Override
			public void endVote() {
				Player b = Bukkit.getPlayer(getVoteName());
				if (b != null) {
					b.kickPlayer(ChatColor.RED + "You Have Been Banned! \n Reason: " + ChatColor.DARK_RED
							+ getVoteReason() + ChatColor.RED + " \n Banned By: " + ChatColor.DARK_RED
							+ "The Masses (Vote Ban)" + ChatColor.RED + "\n Appeal Here: \n" + ChatColor.DARK_RED
							+ getConfig().getString("Settings.Ban.AppealLink"));
				}
				OfflinePlayer banned = Bukkit.getOfflinePlayer(getVoteName());

				if (getVotesYes() > getVotesNo()) {
					getConfig().set("Players.Banned." + banned.getUniqueId() + ".Reason", getVoteReason());
					getConfig().set("Players.Banned." + banned.getUniqueId() + ".Banner",
							"Banned By The Masses (Vote Ban)");
					saveConfig();
					Bukkit.broadcastMessage(
							prefix + "The player was vote banned " + getVoteName() + ". The Vote Ratio being (Y/N): "
									+ getVotesYes() + ChatColor.DARK_AQUA + "/" + ChatColor.AQUA + getVotesNo());
				} else if (getVotesYes() == getVotesNo()) {
					Bukkit.broadcastMessage(prefix + "The Vote Was A Tie, No One Will Be Banned... Today..");
				} else if (getVotesYes() < getVotesNo()) {
					Bukkit.broadcastMessage(
							prefix + "More players decided to give the player another chance. No one will be banned. ");
				}
			}
		};

		voteKick = new Vote(this, "", getConfig().getInt("Settings.AutoBan.Length")) {

			@Override
			public void endVote() {
				Player banned = Bukkit.getPlayer(getVoteName());

				if (getVotesYes() > getVotesNo()) {
					if (banned != null) {
						banned.kickPlayer(ChatColor.RED + "You Have Been Kicked! \n Reason: " + ChatColor.DARK_RED
								+ "The Masses (Vote Kick)" + ChatColor.RED + " \n Kicked By: " + ChatColor.DARK_RED
								+ "The Masses");
					}
					Bukkit.broadcastMessage(
							prefix + "The player was vote kicked " + getVoteName() + ". The Vote Ratio being (Y/N): "
									+ getVotesYes() + ChatColor.DARK_AQUA + "/" + ChatColor.AQUA + getVotesNo());

				} else if (getVotesYes() == getVotesNo()) {
					Bukkit.broadcastMessage(prefix + "The Vote Was A Tie, No One Will Be Banned... Today..");
				} else if (getVotesYes() < getVotesNo()) {
					Bukkit.broadcastMessage(
							prefix + "More players decided to give the player another chance. No one will be banned. ");
				}
			}
		};

		// Auto Broadcaster
		BukkitScheduler scheduler = getServer().getScheduler();

		if (getConfig().getBoolean("Settings.Autobroadcaster.Enabled") == true) {

			scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					Bukkit.broadcastMessage(
							stripColorFromConfig(getConfig().getString("Settings.Autobroadcaster.Msg")));
				}
			}, 0L, getConfig().getLong("Settings.Autobroadcaster.Delay"));

		} else {
			Bukkit.broadcast(prefix + "Autobroadcaster Not Enabled!", "at.recieve");
		}
	}

	HashMap<String, Boolean> BannedPlayers = new HashMap<String, Boolean>();

	@Override
	public void onDisable() {
		System.out.println(ChatColor.BLUE + "UnLoaded!");
	
	}
	

	/*
	 * public void voteBanWait() { BukkitScheduler scheduler =
	 * Bukkit.getServer().getScheduler(); }
	 */

	

	Inventory cmdGUI = Bukkit.createInventory(null, 9, ChatColor.AQUA + "AdminTools GUI beta 1.0");

	int ps = Bukkit.getOnlinePlayers().size();
	Inventory banPlayers = Bukkit.createInventory(null, 54, ChatColor.AQUA + "AdminTools >>" + ChatColor.RED + " Ban");
	Inventory kickGUI = Bukkit.createInventory(null, 54, ChatColor.AQUA + "AdminTools >>" + ChatColor.RED + " Kick");
	Inventory specGUI = Bukkit.createInventory(null, 54,
			ChatColor.AQUA + "AdminTools >>" + ChatColor.DARK_BLUE + " Spectate");
	Inventory freezeGUI = Bukkit.createInventory(null, 54,
			ChatColor.AQUA + "AdminTools >>" + ChatColor.BLUE + " Freezer");
	Inventory gmGUI = Bukkit.createInventory(null, 9, ChatColor.AQUA + "AdminTools >>" + ChatColor.GREEN + " GM'r");
	Inventory researchGUI = Bukkit.createInventory(null, 54,
			ChatColor.AQUA + "AdminTools >>" + ChatColor.DARK_PURPLE + " Research");

	public static void createDisplay(Material material, Inventory inv, int Slot, String name, String lore) {

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(lore);
		meta.setLore(Lore);
		item.setItemMeta(meta);

		inv.setItem(Slot, item);
	}

	

	String error = ChatColor.DARK_RED + "[AdminTools] Error in syntax! ";
	String prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "AdminTools" + ChatColor.DARK_AQUA + "] "
			+ ChatColor.AQUA;

	// CHAT
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		for (String badword : getConfig().getStringList("badwords")) {
			if (e.getMessage().equalsIgnoreCase(badword)) {
				e.setCancelled(true);
				p.sendMessage(error + ChatColor.RED
						+ "You Used A Bad Word, So The Message Got Deleted! A staff member has been informed!");
				Bukkit.broadcast(prefix + ChatColor.RED + "Player " + ChatColor.GREEN + p.getName() + ChatColor.RED
						+ " said: " + e.getMessage(), "At.recieve");
			}
		}

		if (voteBans.getVoteName().equalsIgnoreCase(p.getName()) && voteBans.hasVoteStarted()) {
			p.sendMessage(prefix + "You are being Vote Banned so you can no longer chat until the vote is over!");
			e.setCancelled(true);
		}
		
		if(guiBanner.contains(p.getName())) {

			e.setCancelled(true);
			guiBanReason.add(e.getMessage());
			p.sendMessage(prefix + "Reason for ban: " + guiBanReason.toString());
			guiBanner.remove(p.getName());
			
			
		}
		
		if(muted.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "[AdminTools] You Are Muted And Can Not Talk!");
		}

	}
	// COMMANDS
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		
		if(muted.contains(p.getName())) {
			e.setCancelled(true);
			
			p.sendMessage(ChatColor.RED + "[AdminTools] You are muted and con not run commands!");
		}
		
		if (voteBans.getVoteName().equalsIgnoreCase(p.getName()) && voteBans.hasVoteStarted()) {
			p.sendMessage(prefix + "You are being Vote Banned so you can no longer chat until the vote is over!");
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase(getConfig().getStringList("badwords") + "")) { e.setLine(0, "DON SAY DAT"); }
	}
	
	// PLAYER JOIN
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(p.getName().equalsIgnoreCase("DiamondMCPro")) {
			e.setJoinMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "DiamondMCPro, THE DIAMOND GOD JOINED!");
		} else {
		e.setJoinMessage(getConfig().getString("Settings.Join.Msg").replaceAll("PLAYER", p.getName() + "")
				.replaceAll("RED", ChatColor.RED + "").replaceAll("BLUE", ChatColor.BLUE + "")
				.replaceAll("GREEN", ChatColor.GREEN + "").replaceAll("GOLD", ChatColor.GOLD + "")
				.replaceAll("GRAY", ChatColor.GRAY + "").replaceAll("DG", ChatColor.DARK_GRAY + "")
				.replaceAll("AQUA", ChatColor.AQUA + ""));
		if (getConfig().isSet("Players.Banned." + p.getUniqueId())) {
			p.kickPlayer(ChatColor.RED + "You Have Been Banned! \n Reason: " + ChatColor.DARK_RED
					+ getConfig().getString("Players.Banned." + p.getUniqueId() + ".Reason") + ChatColor.RED
					+ "\n Banned By: " + ChatColor.DARK_RED
					+ getConfig().getString("Players.Banned." + p.getUniqueId() + ".Banner") + ChatColor.RED
					+ "\n Appeal Here: \n" + ChatColor.DARK_RED + getConfig().getString("Settings.Ban.AppealLink"));
			e.setJoinMessage("");
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(getConfig().getString("Settings.Leave.Msg").replaceAll("PLAYER", p.getName())
				.replaceAll("RED", ChatColor.RED + "").replaceAll("BLUE", ChatColor.BLUE + "")
				.replaceAll("GREEN", ChatColor.GREEN + "").replaceAll("GOLD", ChatColor.GOLD + "")
				.replaceAll("GRAY", ChatColor.GRAY + "").replaceAll("DG", ChatColor.DARK_GRAY + "")
				.replaceAll("AQUA", ChatColor.AQUA + ""));

		if (getConfig().isSet("Players.Banned." + p.getUniqueId())) {
			e.setQuitMessage("");
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (frozen.contains(p.getName())) {
			e.setTo(e.getFrom());
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
		Inventory inventory = e.getInventory();

		int slot = 0;

		if (inventory.getName().equals(cmdGUI.getName())) {
			switch (clicked.getType()) {
			case DIAMOND_AXE:
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(banPlayers);
				for (Player ban : Bukkit.getOnlinePlayers()) {
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta smeta = (SkullMeta) skull.getItemMeta();
					smeta.setOwner(ban.getName());
					smeta.setDisplayName(ban.getName());
					skull.setItemMeta(smeta);
					banPlayers.setItem(slot, skull);
					slot++;
				}
				break;
			case IRON_AXE:
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(kickGUI);
				for (Player kick : Bukkit.getOnlinePlayers()) {
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta smeta = (SkullMeta) skull.getItemMeta();
					smeta.setOwner(kick.getName());
					smeta.setDisplayName(kick.getName());
					skull.setItemMeta(smeta);
					kickGUI.setItem(slot, skull);
					slot++;
				}
				break;
			case EYE_OF_ENDER:
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(specGUI);
				for (Player spectated : Bukkit.getOnlinePlayers()) {
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta smeta = (SkullMeta) skull.getItemMeta();
					smeta.setOwner(spectated.getName());
					smeta.setDisplayName(spectated.getName());
					skull.setItemMeta(smeta);
					specGUI.setItem(slot, skull);
					slot++;
				}
				break;
			case PACKED_ICE:
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(freezeGUI);
				for (Player freezed : Bukkit.getOnlinePlayers()) {
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta smeta = (SkullMeta) skull.getItemMeta();
					smeta.setOwner(freezed.getName());
					smeta.setDisplayName(freezed.getName());
					skull.setItemMeta(smeta);
					freezeGUI.setItem(slot, skull);
					slot++;
				}
				break;
			case BEDROCK:
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(gmGUI);
				break;
			case END_CRYSTAL:
				e.setCancelled(true);
				p.closeInventory();
				p.openInventory(researchGUI);
				for (Player researchee : Bukkit.getOnlinePlayers()) {
					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta smeta = (SkullMeta) skull.getItemMeta();
					smeta.setOwner(researchee.getName());
					smeta.setDisplayName(researchee.getName());
					skull.setItemMeta(smeta);
					researchGUI.setItem(slot, skull);
					slot++;
				}
				break;

			}
		}
		
		// BAN PLAYER GUI
		if (inventory.getName().equals(banPlayers.getName())) {

			String playername = e.getCurrentItem().getItemMeta().getDisplayName();
			Player bannd = Bukkit.getPlayer(playername);

			if (clicked.getType() == Material.SKULL_ITEM) {
				e.setCancelled(true);
				p.closeInventory();

				p.sendMessage(prefix + "Banned " + bannd.getName() + "!");
				bannd.kickPlayer(ChatColor.RED + "You Have Been Banned! \n Reason: " + ChatColor.DARK_RED
						+ "The Ban Hammer Has Spoken!" + ChatColor.RED + " \n Banned By: " + ChatColor.DARK_RED
						+ p.getName() + ChatColor.RED + "\n Appeal Here: \n" + ChatColor.DARK_RED
						+ getConfig().getString("Settings.Ban.AppealLink"));
				banned.add(bannd.getName());

				getConfig().set("Players.Banned." + bannd.getUniqueId() + ".Reason", "The Ban Hammer Has Spoken!");
				getConfig().set("Players.Banned." + bannd.getUniqueId() + ".Banner", p.getName());

				saveConfig();
				
			}
		}

		if (inventory.getName().equals(kickGUI.getName())) {

			String playername = e.getCurrentItem().getItemMeta().getDisplayName();
			Player bannd = Bukkit.getPlayer(playername);

			if (clicked.getType() == Material.SKULL_ITEM) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage(prefix + "Kicked " + bannd.getName() + "!");
				bannd.kickPlayer(ChatColor.RED + "You Have Been Kicked! \n Reason: " + ChatColor.DARK_RED
						+ "The Kicker 9000 Has Spoken!" + ChatColor.RED + " \n Kicked By: " + ChatColor.DARK_RED
						+ p.getName());

			}
		}

		if (inventory.getName().equals(specGUI.getName())) {

			String playername = e.getCurrentItem().getItemMeta().getDisplayName();
			Player spectated = Bukkit.getPlayer(playername);

			if (clicked.getType() == Material.SKULL_ITEM) {
				p.closeInventory();
				p.setGameMode(GameMode.SPECTATOR);
				p.teleport(spectated);
				p.sendMessage(prefix + "Spectating " + spectated.getName());
				e.setCancelled(true);

			}
		}
		if (inventory.getName().equals(researchGUI.getName())) {
			String playername = e.getCurrentItem().getItemMeta().getDisplayName();
			Player researchee = Bukkit.getPlayer(playername);

			if (clicked.getType() == Material.SKULL_ITEM) {
				e.setCancelled(true);
				p.closeInventory();
				p.sendMessage(ChatColor.AQUA + "More Info About " + ChatColor.GREEN + researchee.getName());
				p.sendMessage(ChatColor.BLUE + "Name: " + ChatColor.GREEN + researchee.getName());
				p.sendMessage(ChatColor.BLUE + "UUID: " + ChatColor.GREEN + researchee.getUniqueId());
				p.sendMessage(ChatColor.BLUE + "IP: " + ChatColor.GREEN + researchee.getAddress());
				p.sendMessage(ChatColor.BLUE + "Entity ID: " + ChatColor.GREEN + researchee.getEntityId());
				p.sendMessage(ChatColor.BLUE + "Player Time: " + ChatColor.GREEN + researchee.getPlayerTime());

			}
		}
		if (inventory.getName().equals(freezeGUI.getName())) {

			String playername = e.getCurrentItem().getItemMeta().getDisplayName();
			Player frozened = Bukkit.getPlayer(playername);

			if (clicked.getType() == Material.SKULL_ITEM) {
				if (frozen.contains(frozened.getName())) {
					p.closeInventory();
					frozen.remove(frozened.getName());
					p.sendMessage(prefix + "Unfroze " + frozened.getName());
					e.setCancelled(true);
				} else {
					p.closeInventory();
					frozen.add(frozened.getName());
					p.sendMessage(prefix + "Froze " + frozened.getName());
					e.setCancelled(true);
				}
			}
		}
		if (inventory.getName().equals(gmGUI.getName())) {
			switch (clicked.getType()) {
			case BEDROCK:
				e.setCancelled(true);
				p.closeInventory();
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(prefix + "Changed Gamemode To Creative!");
				break;

			case REDSTONE_BLOCK:
				e.setCancelled(true);
				p.closeInventory();
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(prefix + "Changed Gamemode To Survival!");
				break;

			case BARRIER:
				e.setCancelled(true);
				p.closeInventory();
				p.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(prefix + "Changed Gamemode To Spectator Mode!");
				break;

			case DIAMOND_SWORD:
				e.setCancelled(true);
				p.closeInventory();
				p.setGameMode(GameMode.ADVENTURE);
				p.sendMessage(prefix + "Changed Gamemode To Adventure Mode!");
				break;
			}
		}

	}
	
}
