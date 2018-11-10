package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import java.util.List;

public class MasterHeal implements Skill {

    public String name = "风之愈";
    private Double costEP = 60.0;
    private Double healHP = 1500.0;


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

            Double playerCurHP = player.getCurHP();
            player.setCurHP(Math.min(player.getBaseHP(), playerCurHP + healHP));
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