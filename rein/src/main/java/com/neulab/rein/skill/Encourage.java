package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Encourage implements Skill {

    public static String name = "Encourage";
    public static String displayName = "大家加油啊!";
    private Double costSP = 60.0;
    private Integer maxEffort = 3;
    public Integer skillType = GameContants.SKILL_TYPE_SINGLE_FOR_SELF_FRIENDS;


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
