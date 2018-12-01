package com.neulab.rein.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neulab.rein.status.*;
import com.neulab.rein.player.*;
import javafx.util.Pair;
import com.neulab.rein.BasicSetup;
import com.neulab.rein.skill.SkillFactory;
import static com.neulab.rein.BasicSetup.getAssignPlayers;

public class GameStatusTest {
    
    private static Logger logger = LoggerFactory.getLogger(GameStatusTest.class);

    @Test
    public void shouldBeEnemyTurn() {

        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        caster.setCurSP(100.0);
        List<Player> players = playerPair.getValue();

        SkillFactory skillFactory = new SkillFactory();

        GameStatus gameStatus = new GameStatus(players, skillFactory, 0, 1);
        gameStatus.playerActionToken = "leon";
        for(Player player: players) {
            logger.warn(player.getName());
        }

        Assert.assertEquals(true, gameStatus.isEnemyTurn());
    }

    @Test
    public void ShouldGetGameActions() {

        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        caster.setCurSP(100.0);
        List<Player> players = playerPair.getValue();

        SkillFactory skillFactory = new SkillFactory();

        GameStatus gameStatus = new GameStatus(players, skillFactory, 0, 1);

        Player activePlayer = players.get(0);
        Integer ourTeamId = activePlayer.getTeamId();
        Integer otherTeamId = ourTeamId == gameStatus.firstTeamId ? gameStatus.secondTeamId :gameStatus.firstTeamId ;


        List<Player> ourTeamPlayers = gameStatus.getPlayersByTeamId(ourTeamId);
        List<Player> otherTeamPlayers = gameStatus.getPlayersByTeamId(otherTeamId);

        List<GameAction> gameActionCandidates = gameStatus.generateGameActions(activePlayer, ourTeamPlayers, otherTeamPlayers);
        logger.debug(gameActionCandidates.toString());    
    }

    @Test
    public void ShouldApplyRandomAction() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        caster.setCurSP(100.0);
        List<Player> players = playerPair.getValue();

        SkillFactory skillFactory = new SkillFactory();

        GameStatus gameStatus = new GameStatus(players, skillFactory, 0, 1);
        GameStatus nextGameStatus = gameStatus.applyRandomAction();
        logger.debug("....");

    }
}