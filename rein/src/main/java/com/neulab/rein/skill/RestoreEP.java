package com.neulab.rein.skill;

import java.io.Serializable;
import java.util.List;
import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestoreEP implements Skill, Serializable {
    
    private static Logger logger = LoggerFactory.getLogger(RestoreEP.class);

    public static String name = "RestoreEP";
    public static String displayName = "EP回复";

    public Double costSP = 40.0;
    public Double addEP = 100.0;

    public Integer skillType = GameContants.SKILL_TYPE_SINGLE_FOR_SELF;


    @Override
    public void apply(Player caster, List<Player> targetPlayers) {
        if (!this.isAvailable(caster, targetPlayers)) {
            return;
        }
        caster.setCurEP(Math.min(caster.getBaseEP(), caster.getCurEP() + this.addEP));
        caster.setCurSP(Math.max(0, caster.getCurSP() - this.costSP));
        return;
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

    @Override
    public String getName() {
        return this.name;
    }

}