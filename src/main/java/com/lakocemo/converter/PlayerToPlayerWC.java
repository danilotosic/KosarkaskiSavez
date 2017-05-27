package com.lakocemo.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.lakocemo.dto.PlayerWC;
import com.lakocemo.model.Player;

@Component
public class PlayerToPlayerWC implements Converter<Player, PlayerWC> {

	@Override
	public PlayerWC convert(Player player) {
		PlayerWC playerWC = new PlayerWC();

		playerWC.setPlayerID(player.getPlayerID());
		playerWC.setFirstname(player.getFirstname());
		playerWC.setLastname(player.getLastname());

		return playerWC;
	}

	public List<PlayerWC> convert(List<Player> players) {
		List<PlayerWC> playersWC = new ArrayList<>();

		for (Player p : players) {
			playersWC.add(convert(p));
		}

		return playersWC;
	}

}
