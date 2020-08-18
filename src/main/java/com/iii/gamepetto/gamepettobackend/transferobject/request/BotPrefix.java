package com.iii.gamepetto.gamepettobackend.transferobject.request;

import java.io.Serializable;

public class BotPrefix implements Serializable {

	private final static long serialVersionUID = 7013670694399149033L;
	private String botPrefix;

	public String getBotPrefix() {
		return botPrefix;
	}

	public void setBotPrefix(String botPrefix) {
		this.botPrefix = botPrefix;
	}
}
