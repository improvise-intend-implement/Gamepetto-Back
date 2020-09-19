package com.iii.gamepetto.gamepettobackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

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
}
