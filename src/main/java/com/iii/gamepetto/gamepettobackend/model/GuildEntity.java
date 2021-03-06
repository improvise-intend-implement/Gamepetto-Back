package com.iii.gamepetto.gamepettobackend.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Guild")
@DynamicInsert
public class GuildEntity implements Serializable {

    private static final long serialVersionUID = 3205106693431767441L;

    @Id
    private String id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(length = 128)
    private String icon;
    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE NOT NULL")
    private Boolean botPresent;
    @Generated(GenerationTime.INSERT)
    @Column(columnDefinition = "VARCHAR(3) DEFAULT '?'")
    private String botPrefix;

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
