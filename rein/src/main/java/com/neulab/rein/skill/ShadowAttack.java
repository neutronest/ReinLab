package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;
import java.io.Serializable;
import java.util.List;

public class ShadowAttack implements Skill, Serializable {

    public static String name = "ShadowAttack";
    public static String displayName = "暗影剑";
    private static Double costSP = 40.0;
    public Integer skillType = GameContants.SKILL_TYPE_SINGLE_FOR_ENEMY;

    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        Double atkValue = 1.6 * caster.getCurATK();
        if (caster.getEncourage() != 0) {
            atkValue = 1.5 * atkValue;
            caster.setEncourage(caster.getEncourage() - 1);
        }

        caster.setCurSP(Math.max(0.0, caster.getCurSP() - this.costSP));
        
        Player targetPlayer = targetPlayers.get(0);
        Integer targetPlayerShell = targetPlayer.getShell();
        if (targetPlayerShell > 0) {
            targetPlayer.setShell(targetPlayerShell-1);
            return;
        }

        targetPlayer.setCurHP(Math.max(0.0, targetPlayer.getCurHP() - atkValue));
        return;
    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {
        if (caster.getCurSP() < this.costSP) {
            return false;
        }
        if (targetPlayers == null || targetPlayers.size() == 0) {
            return false;
        }
        Player targetPlayer = targetPlayers.get(0);
        if (targetPlayer.getState() == GameContants.PLAYER_STATE_DEAD) {
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

    @Override
    public String getName() {
        return this.name;
    }


}