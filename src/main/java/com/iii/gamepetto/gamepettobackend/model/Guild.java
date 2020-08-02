package com.iii.gamepetto.gamepettobackend.model;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@DynamicInsert
public class Guild implements Serializable {

    private static final long serialVersionUID = 3205106693431767441L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 32, nullable = false, unique = true)
    private String guildId;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 128)
    private String icon;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE NOT NULL")
    private Boolean botPresent;
    @Column(columnDefinition = "VARCHAR(8) DEFAULT '!gp'")
    private String botPrefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean isBotPresent() {
        return botPresent;
    }

    public void setBotPresent(Boolean botPresent) {
        this.botPresent = botPresent;
    }

    public String getBotPrefix() {
        return botPrefix;
    }

    public void setBotPrefix(String botPrefix) {
        this.botPrefix = botPrefix;
    }
}
