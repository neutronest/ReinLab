package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;
import java.util.List;

public class NaiveHeal implements Skill {


    public String name = "小回复";
    private Double costEP = 30.0;
    private Double healHP = 1200.0;


    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        Double casterCurEP = caster.getCurEP();
        caster.setCurEP(casterCurEP - this.costEP);

        Player targetPlayer = targetPlayers.get(0);

        if (targetPlayer.getState() == GameContants.PLAYER_STATE_DEAD) {
            return;
        }
        Double targetPlayerCurHP = targetPlayer.getCurHP();
        targetPlayer.setCurHP(Math.min(targetPlayer.getBaseHP(),  targetPlayerCurHP + this.healHP ));



    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {
        if (targetPlayers.size() != 1) {
            return false;
        }

//        Player targetPlayer = targetPlayers.get(0);
//        if (targetPlayer.getState() == GameContants.PLAYER_STATE_DEAD) {
//            return false;
//        }

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
