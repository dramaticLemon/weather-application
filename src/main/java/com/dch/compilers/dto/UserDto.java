package com.dch.compilers.dto;

// data transfer object for User

public class UserDto {
	private Long id;
	private String username;
	private String password;

	private UserDto(Builder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
	}

	public  UserDto() {}

	public static class Builder {
		private Long id;
		private String username;
		private String password;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public UserDto build() {
			return new UserDto(this);
		}
	}
	
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
}

