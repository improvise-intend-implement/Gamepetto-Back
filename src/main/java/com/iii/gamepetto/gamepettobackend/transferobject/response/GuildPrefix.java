package com.iii.gamepetto.gamepettobackend.transferobject.response;

import java.io.Serializable;

public class GuildPrefix implements Serializable {

	private final static long serialVersionUID = 2745352938917729819L;
	private String guildId;
	private String botPrefix;

	public GuildPrefix(String guildId, String botPrefix) {
		this.guildId = guildId;
		this.botPrefix = botPrefix;
	}

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	public String getBotPrefix() {
		return botPrefix;
	}

	public void setBotPrefix(String botPrefix) {
		this.botPrefix = botPrefix;
	}
}
