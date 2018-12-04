package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import java.io.Serializable;
import java.util.List;

public class DoubleAttack implements Skill, Serializable {

    public static String name = "DoubleAttack";
    private static String displayName = "双连击";
    private Double costSP = 30.0;
    public Integer skillType = GameContants.SKILL_TYPE_SINGLE_FOR_ENEMY;

    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        Player targetPlayer = targetPlayers.get(0);

        Double casterCurSP = caster.getCurSP();
        caster.setCurSP(casterCurSP - this.costSP);

        // check if target has a shell
        Integer shellValue = targetPlayer.getShell();
        if (shellValue > 0) {
            targetPlayer.setShell(shellValue-1);
            return;
        }


        Double targetPlayerCurHP = targetPlayer.getCurHP();
        if (caster.getEncourage() == 0) {
            targetPlayer.setCurHP(Math.max(targetPlayerCurHP - caster.getCurATK() * 2, 0));
        } else {
            targetPlayer.setCurHP(Math.max(targetPlayerCurHP - caster.getCurATK() * 3, 0));
            caster.setEncourage(caster.getEncourage() - 1);
        }
        caster.setCurSP(Math.min(200.0, caster.getCurSP() - 10.0));
        targetPlayer.setCurSP(Math.min(200.0, targetPlayer.getCurSP() + 10.0));

        return;

    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {

        if (caster.getCurSP() < this.costSP) {
            return false;
        }
        if (targetPlayers.size() != 1) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Integer getSkillType() {
        return this.skillType;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

}
