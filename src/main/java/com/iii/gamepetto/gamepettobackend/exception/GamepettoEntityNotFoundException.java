package com.iii.gamepetto.gamepettobackend.exception;

public class GamepettoEntityNotFoundException extends RuntimeException {

	private final String fieldName;
	private final Object value;

	public GamepettoEntityNotFoundException(String message, String fieldName, Object value) {
		super(message);
		this.fieldName = fieldName;
		this.value = value;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getValue() {
		return value;
	}
}
