package com.neulab.rein.skill;

import com.neulab.rein.player.Player;

import java.io.Serializable;
import java.util.List;

public interface Skill {

    public void apply(Player caster, List<Player> targetPlayers);

    public Boolean isAvailable(Player caster, List<Player> targetPlayers);

    public Integer getSkillType();

    public String getDisplayName();
}
