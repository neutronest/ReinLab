package com.neulab.rein;

import com.neulab.rein.mcts.MCTSNode;
import com.neulab.rein.player.Player;
import com.neulab.rein.skill.SkillFactory;
import com.neulab.rein.status.GameAction;
import com.neulab.rein.status.GameStatus;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class BasicSetup {

    private static Logger logger = LoggerFactory.getLogger(BasicSetup.class);

    public static Pair<Player, List<Player>> getAssignPlayers() {

        // Double assignHP = 1000.0;
        // Double assignEP = 200.0;
        // Double assignSP = 0.0;
        // Double assignATK = 100.0;

        // List<String> estelleSkillTokens = new ArrayList<String>(){
        //     {
        //         add("NormalAttack");
        //         add("Encourage");
        //         add("NaiveHeal");
        //         add("MasterHeal");
        //         add("MasterShell");
        //         add("Encourage");
        //     }
        // };
        // List<String> joshuaSkillTokens = new ArrayList<String>(){
        //     {
        //         add("NormalAttack");
        //         add("DoubleAttack");
        //         add("ShadowRaid");
        //     }
        // };
        // List<String> leonSkillTokens = new ArrayList<String>() {
        //     {
        //         add("NormalAttack");
        //     }
        // };

        // Player caster = new Player(
        //         "estelle",
        //         "艾丝蒂尔",
        //         assignHP,
        //         assignEP,
        //         assignSP,
        //         assignATK,
        //         estelleSkillTokens,
        //         0);
        // Player joshua = new Player(
        //     "joshua",
        //     "约修亚",
        //     assignHP,
        //     assignEP,
        //     assignSP,
        //     assignATK,
        //     joshuaSkillTokens,
        //     0
        // );

        // Player leon = new Player(
        //     "leon",
        //     "剑帝莱维",
        //     assignHP,
        //     assignEP,
        //     assignSP,
        //     assignATK,
        //     joshuaSkillTokens,
        //     1
        // );
        
        
        // List<Player> players = new ArrayList<Player>(){
        //     {
        //         add(caster);
        //         add(joshua);
        //         add(leon);
        //     }
        // };

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
                300.0,
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
        
        return new Pair<Player, List<Player>>(caster, players);
    }

    public static GameStatus getGameStatus() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        caster.setCurSP(100.0);
        List<Player> players = playerPair.getValue();

        SkillFactory skillFactory = new SkillFactory();

        GameStatus gameStatus = new GameStatus(players, skillFactory, 0, 1);
        return gameStatus;
    }

    public static MCTSNode getStartMCTSNode() {

        GameStatus gameStatus = getGameStatus();
        MCTSNode mctsNode = new MCTSNode();
        mctsNode.gameStatus = gameStatus;
        return mctsNode;
    }

    public static MCTSNode getAllExpandedMCTSNode() {
        GameStatus gameStatus = getGameStatus();
        MCTSNode mctsNode = new MCTSNode();
        mctsNode.gameStatus = gameStatus;
        List<GameAction> availableGameActions = gameStatus.getAvailableGameActions();
        for (GameAction gameAction: availableGameActions) {
            logger.warn(gameAction.getName());
            GameStatus nextGameStatus = gameStatus.applySpecialAction(gameAction);
            MCTSNode childNode = new MCTSNode();
            childNode.gameStatus = nextGameStatus;
            childNode.parent = mctsNode;
            mctsNode.children.add(childNode);
        }
        return mctsNode;
    }

}
