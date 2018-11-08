package com.neulab.rein.skill;

import com.neulab.rein.player.Player;

import java.util.List;

public interface Skill {

    public void apply(Player caster, List<Player> targetPlayers);

    public Boolean isAvailable(Player caster, List<Player> targetPlayers);
}