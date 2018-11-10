package com.neulab.rein.skill;

import com.neulab.rein.player.Player;

import java.util.List;

public class DoubleAttack implements Skill {

    public static String name = "DoubleAttack";
    private static String displayName = "双连击";
    private Double costSP = 30.0;

    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        Player targetPlayer = targetPlayers.get(0);

        Double casterCurSP = caster.getCurSP();
        caster.setCurSP(casterCurSP - this.costSP);

        Integer shellValue = targetPlayer.getShell();
        if (shellValue > 0) {
            targetPlayer.setShell(shellValue-1);
            return;
        }

        Double targetPlayerCurHP = targetPlayer.getCurHP();
        targetPlayer.setCurHP(Math.max(0, targetPlayerCurHP - 2 * caster.getCurATK()));
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

}
