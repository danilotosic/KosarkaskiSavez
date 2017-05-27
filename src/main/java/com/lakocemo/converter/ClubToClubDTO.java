package com.lakocemo.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.lakocemo.dto.ClubDTO;
import com.lakocemo.model.Club;

@Component
public class ClubToClubDTO implements Converter<Club, ClubDTO> {

	private PlayerToPlayerWC toPlayerWC = new PlayerToPlayerWC();

	@Override
	public ClubDTO convert(Club club) {
		ClubDTO dto = new ClubDTO();

		dto.setClubID(club.getClubID());
		dto.setName(club.getName());
		dto.setPlayers(toPlayerWC.convert(club.getPlayers()));

		return dto;
	}

	public List<ClubDTO> convert(List<Club> clubs) {
		List<ClubDTO> dtos = new ArrayList<>();

		for (Club c : clubs) {
			dtos.add(convert(c));
		}

		return dtos;
	}

}
