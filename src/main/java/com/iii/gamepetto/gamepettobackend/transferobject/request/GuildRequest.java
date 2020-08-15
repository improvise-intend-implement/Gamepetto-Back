package com.iii.gamepetto.gamepettobackend.transferobject.request;

import java.io.Serializable;

public class GuildRequest implements Serializable {

    private static final long serialVersionUID = -5783557158853448407L;
    private String guildId;
    private String name;
    private String icon;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
