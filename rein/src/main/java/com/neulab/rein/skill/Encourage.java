package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Encourage implements Skill {

    private static Logger logger = LoggerFactory.getLogger(Encourage.class);

    private Double costSP = 60.0;
    private Integer maxEffort = 3;

    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        for (Player player: targetPlayers) {
            player.setEncourage(this.maxEffort);
        }

        Double casterCurSP = caster.getCurSP();
        caster.setCurSP(casterCurSP - this.costSP);

    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {
        if (caster.getCurSP() < this.costSP) {
            return false;
        }
        return true;
    }
}
