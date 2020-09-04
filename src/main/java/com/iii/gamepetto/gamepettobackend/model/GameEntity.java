package com.iii.gamepetto.gamepettobackend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Game")
public class GameEntity implements Serializable {

	private static final long serialVersionUID = 4390528504440169348L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 128, nullable = false, unique = true)
	private String name;
	@Lob
	private byte[] img;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "game_id")
	private Set<MapEntity> maps;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public Set<MapEntity> getMaps() {
		return maps;
	}

	public void setMaps(Set<MapEntity> maps) {
		this.maps = maps;
	}
}
