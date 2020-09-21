package com.iii.gamepetto.gamepettobackend.transferobject.response;

import java.io.Serializable;

public class GameResponse implements Serializable {

	private static final long serialVersionUID = 7600833982590928412L;
	private Long id;
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
