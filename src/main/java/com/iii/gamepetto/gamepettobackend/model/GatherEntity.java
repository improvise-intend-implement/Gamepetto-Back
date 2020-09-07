package com.iii.gamepetto.gamepettobackend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Gather", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_Gather_name", columnNames = {"guild_id", "name"}),
		@UniqueConstraint(name = "UQ_Gather_shortName", columnNames = {"guild_id", "short_name"})
})
public class GatherEntity implements Serializable {

	public static final long serialVersionUID = 3161254443484779838L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "guild_id", nullable = false)
	private GuildEntity guild;
	@Column(length = 128, nullable = false)
	private String name;
	@Column(name = "short_name", length = 16, nullable = false)
	private String shortName;
	@Column(unique = true)
	private String channelId;
	@Column(nullable = false)
	private Integer playersPerTeam;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id", nullable = false)
	private GameEntity game;
	@Column(nullable = false)
	private Integer mapsNumber;
	@Column(nullable = false)
	private boolean mapsRandom;
	@Column(nullable = false)
	private boolean captainRolePriority;
	@Column(nullable = false)
	private boolean allAllowed;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Gather_Maps",
			joinColumns = {@JoinColumn(name = "gather_id")},
			inverseJoinColumns = {@JoinColumn(name = "map_id")})
	private Set<MapEntity> maps = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GuildEntity getGuild() {
		return guild;
	}

	public void setGuild(GuildEntity guild) {
		this.guild = guild;
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

	public GameEntity getGame() {
		return game;
	}

	public void setGame(GameEntity game) {
		this.game = game;
	}

	public Integer getMapsNumber() {
		return mapsNumber;
	}

	public void setMapsNumber(Integer mapsNumber) {
		this.mapsNumber = mapsNumber;
	}

	public boolean isMapsRandom() {
		return mapsRandom;
	}

	public void setMapsRandom(boolean mapsRandom) {
		this.mapsRandom = mapsRandom;
	}

	public boolean isCaptainRolePriority() {
		return captainRolePriority;
	}

	public void setCaptainRolePriority(boolean captainRolePriority) {
		this.captainRolePriority = captainRolePriority;
	}

	public boolean isAllAllowed() {
		return allAllowed;
	}

	public void setAllAllowed(boolean allAllowed) {
		this.allAllowed = allAllowed;
	}

	public Set<MapEntity> getMaps() {
		return maps;
	}

	public void setMaps(Set<MapEntity> maps) {
		this.maps = maps;
	}
}
