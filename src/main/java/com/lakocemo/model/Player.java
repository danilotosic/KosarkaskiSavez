package com.lakocemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tblPlayer")
public class Player {

	@Id
	@GeneratedValue
	@Column(name = "playerID")
	private Long playerID;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@ManyToOne(fetch = FetchType.LAZY)
	private Club club;

	public Player() {
		super();
	}

	public Player(String firstname, String lastname, Club club) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.club = club;
	}

	public Player(Long playerID, String firstname, String lastname, Club club) {
		super();
		this.playerID = playerID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.club = club;
	}

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

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

}
