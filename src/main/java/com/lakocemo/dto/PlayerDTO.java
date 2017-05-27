package com.lakocemo.dto;

public class PlayerDTO {

	private Long playerID;
	private String firstname;
	private String lastname;
	private ClubWP club;

	public Long getPlayerID() {
		return playerID;
	}

	public void setPlayerID(Long playerID) {
		this.playerID = playerID;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public ClubWP getClub() {
		return club;
	}

	public void setClub(ClubWP club) {
		this.club = club;
	}

}
