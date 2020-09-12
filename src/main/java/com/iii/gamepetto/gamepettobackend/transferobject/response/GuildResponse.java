package com.iii.gamepetto.gamepettobackend.transferobject.response;

import java.io.Serializable;

public class GuildResponse implements Serializable {

    private final static long serialVersionUID = -2969062740594884692L;
    private String id;
    private String botPrefix;

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
