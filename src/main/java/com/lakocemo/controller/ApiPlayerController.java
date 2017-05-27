package com.lakocemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lakocemo.converter.PlayerToPlayerDTO;
import com.lakocemo.dto.PlayerDTO;
import com.lakocemo.model.Player;
import com.lakocemo.service.PlayerService;

@RestController
@RequestMapping(value = "/api/players")
public class ApiPlayerController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private PlayerToPlayerDTO toDTO;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PlayerDTO>> getPlayers(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "firstname", required = false) String firstname,
			@RequestParam(value = "lastname", required = false) String lastname,
			@RequestParam(value = "clubid", required = false) Long clubid) {

		HttpHeaders headers = new HttpHeaders();
		List<Player> retVal = new ArrayList<>();
		int totalPages = -1;

		if (page == null) {
			
			if(clubid != null) {
				Iterable<Player> players = playerService.findByClubID(clubid);
				for (Player p : players) {
					retVal.add(p);
				}
			} else {			
				Iterable<Player> players = playerService.findAll();
				for (Player p : players) {
					retVal.add(p);
				}
			}
			
		} else {

			Page<Player> players;

			if (firstname != null && lastname != null) {
				players = playerService.findByFirstnameAndLastname(firstname, lastname, page);
			} else if (firstname != null) {
				players = playerService.findByFirstname(firstname, page);
			} else if (lastname != null) {
				players = playerService.findByLastname(lastname, page);
			} else {
				players = playerService.findAll(page);
			}

			retVal = players.getContent();
			totalPages = players.getTotalPages();
		}

		if (retVal.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if (totalPages >= 0) {
			headers.add("totalPages", totalPages + "");
		}

		return new ResponseEntity<>(toDTO.convert(retVal), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<PlayerDTO> getPlayer(@PathVariable Long id) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Player retVal = playerService.findOne(id);

		if (retVal == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
	}

	@RequestMapping(consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<PlayerDTO> savePlayer(@RequestBody Player player) {

		if (player == null || player.getClub() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Player retVal = playerService.save(player);

		if (retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.CREATED);
	}

	@RequestMapping(consumes = "application/json", value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<PlayerDTO> editPlayer(@RequestBody Player player, @PathVariable Long id) {

		if (player == null || id == null || !player.getPlayerID().equals(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Player retVal = playerService.save(player);

		if (retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<PlayerDTO> deletePlayer(@PathVariable Long id) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Player retVal = playerService.delete(id);

		if (retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
	}

}
