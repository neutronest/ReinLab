package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import java.util.List;

public class ShadowRaid implements Skill {

    public static String name = "ShadowRaid";
    public static String displayName = "秘技- 幻影奇袭!";
    private static Double costSP = 100.0;
    public Integer skillType = GameContants.SKILL_TYPE_FOR_ENEMY;


    @Override
    public void apply(Player caster, List<Player> targetPlayers) {
        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        Double casterCurSP = caster.getCurSP();
        caster.setCurSP(casterCurSP - this.costSP);


        for (Player player : targetPlayers) {

            if (player.getState() == GameContants.PLAYER_STATE_DEAD) {
                continue;
            }

            Integer shellValue = player.getShell();
            if (shellValue > 0) {
                player.setShell(shellValue-1);
                continue;
            }

            Double playerCurHP = player.getCurHP();
            player.setCurHP(Math.max(0, playerCurHP - 8 * caster.getCurATK()));
            return;

        }

    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {
        if (caster.getCurSP() < this.costSP) {
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
}
