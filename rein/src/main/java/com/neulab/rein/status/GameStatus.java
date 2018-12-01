package com.neulab.rein.status;

import com.neulab.rein.player.Player;
import com.neulab.rein.skill.Skill;
import com.neulab.rein.skill.SkillFactory;
import com.neulab.rein.utils.GameContants;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GameStatus implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(GameStatus.class);

    private Map<String, Player> playerMap = new HashMap<>();
    public String playerActionToken = "";
    public Queue<String> actionQueue = new ArrayDeque<>();
    public SkillFactory skillFactory = null;

    public Integer firstTeamId = -1;
    public Integer secondTeamId = -1;

    public GameStatus(List<Player> players, SkillFactory skillFactory, Integer firstTeamId, Integer secondTeamId) {

        for (Player player: players) {
            this.playerMap.put(player.getName(), player);
        }

        for (Player player: players) {
            this.actionQueue.add(player.getName());
        }
        this.playerActionToken = players.get(0).getName();
        this.skillFactory = skillFactory;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;

    }

    public GameStatus applyRandomAction() {
        if (this.isTerminated()) {
            logger.warn(GameContants.EXCEPTION_GAME_IS_TERMINATED);
            return this;
        }

        // get this turn's active player
        this.playerActionToken = this.getNextTurn();
        Player activePlayer = this.playerMap.get(this.playerActionToken);
        if (activePlayer == null) {
            logger.warn(GameContants.EXCEPTION_NO_CORRESPONDING_PLAYER);
            return this;
        }

        Integer ourTeamId = activePlayer.getTeamId();
        Integer otherTeamId = ourTeamId == this.firstTeamId ? this.firstTeamId : this.secondTeamId;

        List<Player> ourTeamPlayers = this.getPlayersByTeamId(ourTeamId);
        List<Player> otherTeamPlayers = this.getPlayersByTeamId(otherTeamId);

        List<Skill> skills = this.skillFactory.getAvailableSkillsBySkillTokens(
            activePlayer,
            ourTeamPlayers,
            otherTeamPlayers
        );
        // TODO: Dev
        return this;
    }

    public List<Player> getPlayersByTeamId(Integer teamId) {
        
        List<Player> players = new ArrayList<>();
        for(Map.Entry<String, Player> entry: this.playerMap.entrySet()) {
            String playerName = entry.getKey();
            Player player = entry.getValue();
            if (player.getTeamId() == teamId) {
                players.add(player);
            }
        }
        return players;
    }

    public GameStatus generateNextGameStatus() {

        this.playerActionToken = this.getNextTurn();
        
        return this;
    }

    public Boolean isEnemyTurn() {

        for(Map.Entry<String, Player> entry: this.playerMap.entrySet()) {
            String playerName = entry.getKey();
            Player player = entry.getValue();
            if (this.playerActionToken.equals(playerName) &&
                    player.getTeamId() == this.secondTeamId) {
                return true;
            }
        }
        return false;
    }

    public String PrettyPrintGameStatus() {
        return "";
    }

    public Double getReward() {return 0.0; }

    public String getNextTurn() {

        String curPlayerName = this.actionQueue.remove();
        this.actionQueue.add(curPlayerName);


        String nextPlayerName = this.actionQueue.peek();
        Boolean isGetNextTurn = false;
        while (isGetNextTurn) {
            if (this.playerMap.get(nextPlayerName).getState() == GameContants.PLAYER_STATE_DEAD) {
                curPlayerName = this.actionQueue.remove();
                this.actionQueue.add(curPlayerName);
                nextPlayerName = this.actionQueue.peek();
            } else {
                isGetNextTurn = true;
            }
        }
        return nextPlayerName;
    }

    public Boolean isTerminated() {

        List<Player> ourAlivePlayers = this.playerMap.values()
                .stream()
                .filter( player -> player.getTeamId() == this.firstTeamId && player.getState() == GameContants.PLAYER_STATE_ALIVE)
                .collect(Collectors.toList());

        List<Player> enmeyAlivePlayers = this.playerMap.values()
                .stream()
                .filter(player -> player.getTeamId() == this.secondTeamId  && player.getState() == GameContants.PLAYER_STATE_ALIVE)
                .collect(Collectors.toList());

        if (ourAlivePlayers.size() == 0 || enmeyAlivePlayers.size() == 0) {
            return true;
        }
        return false;
    }
}
