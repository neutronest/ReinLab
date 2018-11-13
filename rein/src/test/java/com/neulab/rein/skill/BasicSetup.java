package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BasicSetup {

    public static Pair<Player, List<Player>> getAssignPlayers() {

        Double assignHP = 1000.0;
        Double assignEP = 200.0;
        Double assignSP = 0.0;
        Double assignATK = 100.0;

        List<String> estelleSkillTokens = new ArrayList<String>(){
            {
                add("NormalAttack");
                add("Encourage");
                add("NaiveHeal");
                add("MasterHeal");
                add("MasterShell");
            }
        };
        List<String> joshuaSkillTokens = new ArrayList<String>(){
            {
                add("NormalAttack");
                add("DoubleAttack");
                add("ShadowRaid");
            }
        };

        Player caster = new Player(assignHP, assignEP, assignSP, assignATK, estelleSkillTokens);
        List<Player> players = new ArrayList<Player>();
        for (int i=0; i<3; i++) {
            players.add(new Player(assignHP, assignEP, assignSP, assignATK, joshuaSkillTokens));
        }
        return new Pair<Player, List<Player>>(caster, players);
    }
}
