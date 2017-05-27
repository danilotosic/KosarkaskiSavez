package com.lakocemo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tblClub")
public class Club {

	@Id
	@GeneratedValue
	@Column(name = "clubID")
	private Long clubID;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
	private List<Player> players;

	public Club() {
		super();
	}

	public Club(String name) {
		super();
		this.name = name;
	}

	public Club(String name, List<Player> players) {
		super();
		this.name = name;
		this.players = players;
	}

	public Club(Long clubID, String name, List<Player> players) {
		super();
		this.clubID = clubID;
		this.name = name;
		this.players = players;
	}

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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
