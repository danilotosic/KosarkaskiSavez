package com.lakocemo.dto;

import java.util.List;

public class ClubDTO {

	private Long clubID;
	private String name;
	private List<PlayerWC> players;

	public Long getClubID() {
		return clubID;
	}

	public void setClubID(Long clubID) {
		this.clubID = clubID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PlayerWC> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerWC> players) {
		this.players = players;
	}

}
