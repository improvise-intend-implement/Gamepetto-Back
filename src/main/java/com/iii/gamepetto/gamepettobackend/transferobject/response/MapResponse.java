package com.iii.gamepetto.gamepettobackend.transferobject.response;

import java.io.Serializable;

public class MapResponse implements Serializable {

	private static final long serialVersionUID = 1910783879985428887L;
	private Long id;
	private String name;

	public MapResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

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
