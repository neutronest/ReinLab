package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import java.util.List;

public class MasterShell implements Skill {

    public static String name = "MasterShell";
    public static String displayName = "大地之墙";
    private Double costEP = 80.0;


    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        Double casterCurEP = caster.getCurEP();
        caster.setCurEP(casterCurEP - this.costEP);

        for (Player player: targetPlayers) {
            if (player.getState() == GameContants.PLAYER_STATE_DEAD) {
                continue;
            }

            player.setShell(1);
        }

    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {

        if (caster.getCurEP() < this.costEP) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return this.name;
    }
}
