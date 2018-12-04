package com.neulab.rein;

import com.neulab.rein.player.Player;
import com.neulab.rein.skill.*;
import com.neulab.rein.status.GameStatus;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Main {



    public static void main(String[] args) throws InterruptedException {

        // initialize skill pool
        SkillFactory skillFactory = new SkillFactory();

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
                add("Encourage");
            }
        };
        List<String> joshuaSkillTokens = new ArrayList<String>(){
            {
                add("NormalAttack");
                add("DoubleAttack");
                add("ShadowRaid");
            }
        };
        List<String> leonSkillTokens = new ArrayList<String>() {
            {
                add("NormalAttack");
            }
        };

        Player caster = new Player(
                "estelle",
                "艾丝蒂尔",
                assignHP,
                assignEP,
                assignSP,
                assignATK,
                estelleSkillTokens,
                0);
        Player joshua = new Player(
                "joshua",
                "约修亚",
                assignHP,
                assignEP,
                assignSP,
                assignATK,
                joshuaSkillTokens,
                0
        );

        Player leon = new Player(
                "leon",
                "剑帝莱维",
                12000.0,
                assignEP,
                assignSP,
                assignATK,
                joshuaSkillTokens,
                1
        );


        List<Player> players = new ArrayList<Player>(){
            {
                add(caster);
                add(joshua);
                add(leon);
            }
        };

        caster.setCurSP(100.0);


        GameStatus gameStatus = new GameStatus(players, skillFactory, 0, 1);
        GameStatus nextGameStatus = gameStatus.applyRandomAction();
        while(!nextGameStatus.isTerminated()) {
            nextGameStatus = nextGameStatus.applyRandomAction();
            Thread.sleep(100);
        }
        //GameStatus gameStatus = new GameStatus(players);
    }

}
