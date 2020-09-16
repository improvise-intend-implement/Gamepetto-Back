package com.iii.gamepetto.gamepettobackend.transferobject.request;

import java.io.Serializable;

public class GuildRequest implements Serializable {

    private static final long serialVersionUID = -5783557158853448407L;
    private String id;
    private String name;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
