package com.iii.gamepetto.gamepettobackend.transferobject;

public class GuildPrefix {

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
