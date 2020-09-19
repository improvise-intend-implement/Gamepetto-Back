package com.iii.gamepetto.gamepettobackend.transferobject.request;

import java.io.Serializable;
import java.util.Set;

public class GatherRequest implements Serializable {

	private static final long serialVersionUID = -3277202476641720999L;
	private String guildId;
	private String name;
	private String shortName;
	private String channelId;
	private Integer playersPerTeam;
	private Long gameId;
	private Integer mapsNumber;
	private Boolean mapsRandom;
	private Boolean captainRolePriority;
	private Boolean allAllowed;
	private Set<Long> mapsIds;

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getPlayersPerTeam() {
		return playersPerTeam;
	}

	public void setPlayersPerTeam(Integer playersPerTeam) {
		this.playersPerTeam = playersPerTeam;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Integer getMapsNumber() {
		return mapsNumber;
	}

	public void setMapsNumber(Integer mapsNumber) {
		this.mapsNumber = mapsNumber;
	}

	public Boolean getMapsRandom() {
		return mapsRandom;
	}

	public void setMapsRandom(Boolean mapsRandom) {
		this.mapsRandom = mapsRandom;
	}

	public Boolean getCaptainRolePriority() {
		return captainRolePriority;
	}

	public void setCaptainRolePriority(Boolean captainRolePriority) {
		this.captainRolePriority = captainRolePriority;
	}

	public Boolean getAllAllowed() {
		return allAllowed;
	}

	public void setAllAllowed(Boolean allAllowed) {
		this.allAllowed = allAllowed;
	}

	public Set<Long> getMapsIds() {
		return mapsIds;
	}

	public void setMapsIds(Set<Long> mapsIds) {
		this.mapsIds = mapsIds;
	}
}
