package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class Encourage implements Skill, Serializable {

    public static String name = "Encourage";
    public static String displayName = "大家加油啊!";
    private Double costEP = 60.0;
    private Integer maxEffort = 3;
    public Integer skillType = GameContants.SKILL_TYPE_MULTI_FOR_SELF_FRIENDS;


    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }

        for (Player player: targetPlayers) {
            if (player.getState() == GameContants.PLAYER_STATE_DEAD) {
                continue;
            }
            player.setEncourage(this.maxEffort);
        }

        Double casterCurEP = caster.getCurEP();
        caster.setCurEP(casterCurEP - this.costEP);

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
