package com.neulab.rein;

import com.neulab.rein.player.Player;
import com.neulab.rein.skill.*;
import com.neulab.rein.status.GameStatus;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // initialize skill pool
        SkillFactory skillFactory = new SkillFactory();

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


        // TODO: update skills for enemy
        List<String> leonSkillTokens = new ArrayList<String>(){
            {
                add("NormalAttack");
                add("DoubleAttack");
                add("ShadowRaid");
            }
        };

        Player estelle = new Player(
                "estelle",
                "艾丝蒂尔",
                2000.0,
                400.0,
                0.0,
                200.0,
                estelleSkillTokens,
                0);
        Player joshua = new Player(
                "joshua",
                "约修亚",
                1800.0,
                0.0,
                0.0,
                300.0,
                joshuaSkillTokens,
                0);
        Player leon = new Player(
                "leon",
                "剑帝莱维",
                20000.0,
                0.0,
                0.0,
                500.0,
                leonSkillTokens,
                1);

        List<Player> players = new ArrayList<Player>(){
            {
                add(estelle);
                add(joshua);
                add(leon);
            }
        };
        //GameStatus gameStatus = new GameStatus(players);
    }

}
