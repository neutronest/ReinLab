package com.neulab.rein.skill;


import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillFactory {

    private Map<String, Skill> skillTable = new HashMap<>();

    public SkillFactory() {

        this.skillTable.put(Attack.name, new Attack());
        this.skillTable.put(DoubleAttack.name, new DoubleAttack());
        this.skillTable.put(Encourage.name, new Encourage());
        this.skillTable.put(MasterShell.name, new MasterShell());
        this.skillTable.put(MasterHeal.name, new MasterHeal());
        this.skillTable.put(NaiveHeal.name, new NaiveHeal());
        this.skillTable.put(ShadowRaid.name, new ShadowRaid());
    }

    public List<Skill> getAvailableSkillsBySkillTokens(List<String> skillTokens,
                                                       Player caster,
                                                       List<Player> players) {
        List<Skill> availableSkills = new ArrayList<>();
        for (String token: skillTokens) {

            Skill skill = this.skillTable.get(token);
            if (skill != null && skill.isAvailable(caster, players)) {
                availableSkills.add(skill);
            }
        }
        return availableSkills;
    }
}
