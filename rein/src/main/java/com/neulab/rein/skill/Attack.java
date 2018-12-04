package com.neulab.rein.skill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import java.io.Serializable;
import java.util.List;

public class Attack implements Skill, Serializable {

    private static Logger logger = LoggerFactory.getLogger(Attack.class);

    public static String name = "NormalAttack";
    public static String displayName = "普通攻击";

    public Integer skillType = GameContants.SKILL_TYPE_SINGLE_FOR_ENEMY;

    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        
        if (targetPlayers == null) {
            logger.error("players must not been null!");
        }
        if (targetPlayers.size() != 1) {
            logger.warn("the target of normal attack must not been none or exceed for one player!");
            return;
        }

        Player targetPlayer = targetPlayers.get(0);

        // check if target has a shell
        Integer targetPlayerShell = targetPlayer.getShell();
        if (targetPlayerShell > 0) {
            targetPlayer.setShell(targetPlayerShell-1);
            return;
        }

        Double targetPlayerCurHP = targetPlayer.getCurHP();
        if (caster.getEncourage() == 0) {
            targetPlayer.setCurHP(Math.max(targetPlayerCurHP - caster.getCurATK(), 0));
        } else {
            targetPlayer.setCurHP(Math.max(targetPlayerCurHP - caster.getCurATK() * 1.5, 0));
            caster.setEncourage(caster.getEncourage()-1);
        }
        caster.setCurSP(Math.min(200.0, caster.getCurSP() + 10.0));
        targetPlayer.setCurSP(Math.min(200.0, targetPlayer.getCurSP() + 10.0));
        return;
    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {
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
