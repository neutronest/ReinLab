package com.neulab.rein;

import com.neulab.rein.mcts.MCTSNode;
import com.neulab.rein.player.Player;
import com.neulab.rein.skill.*;
import com.neulab.rein.status.GameStatus;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {


    private static Logger logger = LoggerFactory.getLogger(Main.class);


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
                add("RestoreEP");
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
                add("ShadowAttack");
            }
        };

        Player caster = new Player(
                "estelle",
                "艾丝蒂尔",
                2000.0,
                200.0,
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
                400.0,
                joshuaSkillTokens,
                0
        );

        Player leon = new Player(
                "leon",
                "剑帝莱维",
                20000.0,
                assignEP,
                assignSP,
                500.0,
                leonSkillTokens,
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
        MCTSNode rootNode = new MCTSNode();
        rootNode.gameStatus = gameStatus;
        //rootNode.UCTSearch(1000);
        rootNode.replay();
        // GameStatus nextGameStatus = gameStatus.applyRandomAction();
        // while(!nextGameStatus.isTerminated()) {
        //     nextGameStatus = nextGameStatus.applyRandomAction();
        //     System.out.println(nextGameStatus.repr());
        //     Thread.sleep(100);
        // }
        //GameStatus gameStatus = new GameStatus(players);

    }

}
