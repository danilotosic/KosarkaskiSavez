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

import com.lakocemo.converter.ClubToClubDTO;
import com.lakocemo.dto.ClubDTO;
import com.lakocemo.model.Club;
import com.lakocemo.model.Player;
import com.lakocemo.service.ClubService;
import com.lakocemo.service.PlayerService;

@RestController
@RequestMapping(value = "/api/clubs")
public class ApiClubController {

	@Autowired
	private ClubService clubService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private ClubToClubDTO toDTO;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClubDTO>> getClubs(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "name", required = false) String name) {

		List<Club> retVal = new ArrayList<>();
		int totalPages = -1;

		if (page == null) {

			Iterable<Club> clubs = clubService.findAll();

			for (Club c : clubs) {
				retVal.add(c);
			}

		} else {

			Page<Club> clubs;

			if (name != null) {
				clubs = clubService.findByName(name, page);
			} else {
				clubs = clubService.findAll(page);
			}

			retVal = clubs.getContent();
			totalPages = clubs.getTotalPages();

		}

		HttpHeaders headers = new HttpHeaders();
		if (totalPages >= 0) {
			headers.add("totalPages", totalPages + "");
		}

		if (retVal.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClubDTO> getClub(@PathVariable Long id) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Club retVal = clubService.findOne(id);

		if (retVal == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
	}

	// TODO NAPRAVITI PUT i DELETE METODU
	@RequestMapping(consumes = "application/json", method = RequestMethod.POST)
	public ResponseEntity<ClubDTO> saveClub(@RequestBody Club club) {

		if (club == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Club retVal = clubService.save(club);

		if (club.getPlayers() != null) {
			for (Player p : club.getPlayers()) {
				p.setClub(club);
				p.setPlayerID(null);
				playerService.save(p);
			}
		}

		if (retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", consumes = "application/json", method = RequestMethod.PUT)
	public ResponseEntity<ClubDTO> editClub(@RequestBody Club club, @PathVariable Long id) {

		if (id == null || club == null || !club.getClubID().equals(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Club retVal = clubService.save(club);

		if (retVal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ClubDTO> deleteClub(@PathVariable Long id) {

		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Club retVal = clubService.delete(id);

		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
	}

}
