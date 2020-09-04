package com.iii.gamepetto.gamepettobackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Map")
public class MapEntity  implements Serializable {

	private static final long serialVersionUID = -7036634752429475884L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(length = 128, nullable = false, unique = true)
	private String name;

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
}
