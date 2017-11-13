package com.picsart.demo.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
	public final String id;
	public final String email;
	public final String name;

	@JsonCreator
	public UserDto(@JsonProperty("id") String id,
	               @JsonProperty("email") String email,
	               @JsonProperty("name") String name) {
		this.id = id;
		this.email = email;
		this.name = name;
	}
}
