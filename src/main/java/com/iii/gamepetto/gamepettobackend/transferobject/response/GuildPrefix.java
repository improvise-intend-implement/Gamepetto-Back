package com.iii.gamepetto.gamepettobackend.transferobject.response;

import java.io.Serializable;

public class GuildPrefix implements Serializable {

	private final static long serialVersionUID = 2745352938917729819L;
	private String id;
	private String botPrefix;

	public GuildPrefix(String id, String botPrefix) {
		this.id = id;
		this.botPrefix = botPrefix;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBotPrefix() {
		return botPrefix;
	}

	public void setBotPrefix(String botPrefix) {
		this.botPrefix = botPrefix;
	}
}
