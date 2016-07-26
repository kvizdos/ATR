package com.kentonvizdos.ATR;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Vote {
	private String voteName;
	private JavaPlugin plugin;
	private List<String> playersWhoVoted = new ArrayList<>();
	private int voteLength;
	private int votesYes;
	private int votesNo;
	private boolean voteStarted;
	private String voteReason;

	public Vote(JavaPlugin plugin, String voteName, int voteLength) {
		this.plugin = plugin;
		this.voteName = voteName;
		this.voteLength = voteLength;
		this.votesNo = 0;
		this.votesYes = 0;
		this.voteStarted = false;
	}

	public void startVote() {
		voteStarted = true;
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				onVoteEnd();
			}
		}, voteLength * 20L);
	}

	private void onVoteEnd() {
		endVote();
		clearVotes();
		setVoteName("");
		voteStarted = false;
	}

	public abstract void endVote();

	public int getVoteLength() {
		return voteLength;
	}

	public void setVoteLength(int voteLength) {
		this.voteLength = voteLength;
	}

	public int getVotesYes() {
		return votesYes;
	}

	public void setVotesYes(int votesYes) {
		this.votesYes = votesYes;
	}

	public void addVoteYes() {
		this.votesYes += 1;
	}

	public void addVoteNo() {
		this.votesNo += 1;
	}

	public void addVoteYes(String playerUID) {
		this.votesYes += 1;
		playersWhoVoted.add(playerUID);
	}

	public int getVotesNo() {
		return votesNo;
	}

	public void setVotesNo(int votesNo) {
		this.votesNo = votesNo;
	}

	public void addVoteNo(String playerUID) {
		this.votesNo += 1;
		playersWhoVoted.add(playerUID);
	}

	public int getTotalVotes() {
		return votesYes + votesNo;
	}

	public boolean hasPlayerVoted(String playerUID) {
		return playersWhoVoted.contains(playerUID);
	}

	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}

	public String getVoteName() {
		return this.voteName;
	}

	public void clearVotes() {
		this.votesYes = 0;
		this.votesNo = 0;
		this.playersWhoVoted.removeAll(playersWhoVoted);
	}

	public boolean hasVoteStarted() {
		return this.voteStarted;
	}

	public void setVoteReason(String voteReason) {
		this.voteReason = voteReason;
	}

	public String getVoteReason() {
		return this.voteReason;
	}
}