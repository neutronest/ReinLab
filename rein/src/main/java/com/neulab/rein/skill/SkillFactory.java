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

    public List<Skill> getAvailableSkillsBySkillTokens(Player caster,
                                                       List<Player> friendPlayers,
                                                       List<Player> enemyPlayers) {

        List<String> skillTokens = caster.getSkillTokens();
        List<Skill> availableSkills = new ArrayList<>();
        for (String token: skillTokens) {

            Skill skill = this.skillTable.get(token);
            // TODO: not a good logic to fix skill's usedFor to teamID
            if (skill == null) {
                continue;
            }
            
            if (skill.getSkillType() == GameContants.SKILL_TYPE_FOR_ENEMY) {
                if (skill.isAvailable(caster, enemyPlayers)) {
                    availableSkills.add(skill);
                }
            } else if (skill.getSkillType() == GameContants.SKILL_TYPE_FOR_SELF_FRIENDS) {
                if (skill.isAvailable(caster, friendPlayers)) {
                    availableSkills.add(skill);
                }
            }
        }
        return availableSkills;
    }
}
