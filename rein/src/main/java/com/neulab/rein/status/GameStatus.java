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
    public String applyActionName = "";

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

        GameStatus nextGameStatus = this.deepCopy();

        if (nextGameStatus.isTerminated()) {
            logger.warn(GameContants.EXCEPTION_GAME_IS_TERMINATED);
            return nextGameStatus;
        }

        // get this turn's active player
        Player activePlayer = nextGameStatus.playerMap.get(nextGameStatus.playerActionToken);
        if (activePlayer == null) {
            logger.warn(GameContants.EXCEPTION_NO_CORRESPONDING_PLAYER);
            return nextGameStatus;
        }

        List<GameAction> availableGameActions = nextGameStatus.getAvailableGameActions();

        if (availableGameActions.size() == 0) {
            logger.error("No Available Game Actions founded");
            return nextGameStatus;
        }

        Random rand = new Random();
        Integer applyActionId = rand.nextInt(availableGameActions.size());
        GameAction applyAction = availableGameActions.get(applyActionId);
        applyAction.apply();
        nextGameStatus.applyActionName = applyAction.getName();
        nextGameStatus.updatePlayerState();
        nextGameStatus.playerActionToken = nextGameStatus.getNextTurn();
        return nextGameStatus;
    }

    public GameStatus applySpecialAction(GameAction appliedGameAction) {

        // TODO
        // Duplicate codes.. Need to refactor
        // issue
        // need check player if deep copy
        GameStatus nextGameStatus = this.deepCopy();
        GameAction appliedGameActionCopy = appliedGameAction.deepCopy();

        if (nextGameStatus.isTerminated()) {
            logger.warn(GameContants.EXCEPTION_GAME_IS_TERMINATED);
            return nextGameStatus;
        }

        // get this turn's active player
        Player activePlayer = nextGameStatus.playerMap.get(nextGameStatus.playerActionToken);
        if (activePlayer == null) {
            logger.warn(GameContants.EXCEPTION_NO_CORRESPONDING_PLAYER);
            return nextGameStatus;
        }

        List<GameAction> availableGameActions = nextGameStatus.getAvailableGameActions();

        if (availableGameActions.size() == 0) {
            logger.error("No Available Game Actions founded");
            return nextGameStatus;
        }
        List<String> availableGameActionNames = availableGameActions
            .stream()
            .map(gameAction -> gameAction.getName())
            .collect(Collectors.toList());
        if (availableGameActionNames.contains(appliedGameAction.getName()) == false) {
            logger.warn("Illegaled gameAction apply...");
            return this;
        }

        appliedGameAction.apply();
        nextGameStatus.applyActionName = appliedGameAction.getName();
        nextGameStatus.updatePlayerState();
        nextGameStatus.playerActionToken = nextGameStatus.getNextTurn();
        return nextGameStatus;

    }

    public List<GameAction> generateGameActions(Player activePlayer, List<Player> ourTeamPlayers, List<Player> otherTeamPlayers) {


        List<Skill> skillCandidates = this.skillFactory.getCandidateSkillsBySkillTokens(activePlayer.getSkillTokens());

        List<GameAction> gameActionCandidates = new ArrayList<>();
        for (Skill skill: skillCandidates) {
            
            if (skill.getSkillType().intValue() == GameContants.SKILL_TYPE_SINGLE_FOR_SELF_FRIENDS) {
                for(Player targetPlayer: ourTeamPlayers) {
                    List<Player> targetPlayers = new ArrayList<>();
                    targetPlayers.add(targetPlayer);
                    gameActionCandidates.add(new GameAction(
                        activePlayer, 
                        targetPlayers, 
                        skill));
                } 
            } else if (skill.getSkillType().intValue() == GameContants.SKILL_TYPE_MULTI_FOR_SELF_FRIENDS) {
                gameActionCandidates.add(new GameAction(activePlayer, ourTeamPlayers, skill));

            } else if (Objects.equals(skill.getSkillType(), GameContants.SKILL_TYPE_SINGLE_FOR_ENEMY)) {
                for(Player targetPlayer: otherTeamPlayers) {
                    List<Player> targetPlayers = new ArrayList<>();
                    targetPlayers.add(targetPlayer);
                    gameActionCandidates.add(new GameAction(activePlayer, targetPlayers, skill));
                }
            } else if (skill.getSkillType().intValue() == GameContants.SKILL_TYPE_MULTI_FOR_ENEMY) {
                gameActionCandidates.add(new GameAction(activePlayer, otherTeamPlayers, skill));
            }
        }
        return gameActionCandidates;
    }

    public List<GameAction> getAvailableGameActions() {
        Player activePlayer = this.playerMap.get(this.playerActionToken);
        Integer ourTeamId = activePlayer.getTeamId();
        Integer otherTeamId = ourTeamId == this.firstTeamId ? this.secondTeamId :this.firstTeamId ;

        List<Player> ourTeamPlayers = this.getPlayersByTeamId(ourTeamId);
        List<Player> otherTeamPlayers = this.getPlayersByTeamId(otherTeamId);

        List<Skill> skillCandidates = this.skillFactory.getCandidateSkillsBySkillTokens(activePlayer.getSkillTokens());

        List<GameAction> gameActionCandidates = this.generateGameActions(activePlayer, ourTeamPlayers, otherTeamPlayers);
        List<GameAction> availableGameActions = GameAction.getAvailableGameActions(gameActionCandidates);
        return availableGameActions;
    }

    public List<Player> getPlayersByTeamId(Integer teamId) {
        
        List<Player> players = new ArrayList<>();
        for(Map.Entry<String, Player> entry: this.playerMap.entrySet()) {
            String playerName = entry.getKey();
            Player player = entry.getValue();
            if (Objects.equals(player.getTeamId(), teamId)) {
                players.add(player);
            }
        }
        return players;
    }

    public Boolean isEnemyTurn() {

        for(Map.Entry<String, Player> entry: this.playerMap.entrySet()) {
            String playerName = entry.getKey();
            Player player = entry.getValue();
            if (this.playerActionToken.equals(playerName) &&
                    Objects.equals(player.getTeamId(), this.secondTeamId)) {
                return true;
            }
        }
        return false;
    }

    public String PrettyPrintGameStatus() {
        return "";
    }

    public Double getReward() {
        List<Player> otherTeamPlayers = this.getPlayersByTeamId(this.secondTeamId);
        Double lastHP = otherTeamPlayers
            .stream()
            .map(player -> player.getCurHP())
            .mapToDouble(Double::doubleValue)
            .sum();
        return -1.0 * Math.abs(0 - lastHP / 10000.0); 
    }

    public String getNextTurn() {

        String curPlayerName = this.actionQueue.remove();
        this.actionQueue.add(curPlayerName);


        String nextPlayerName = this.actionQueue.peek();
        Boolean isGetNextTurn = false;
        while (!isGetNextTurn) {
            if (this.playerMap.get(nextPlayerName).getState().intValue() == GameContants.PLAYER_STATE_DEAD) {
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
                .filter( player -> Objects.equals(player.getTeamId(), this.firstTeamId)
                        && Objects.equals(player.getState(), GameContants.PLAYER_STATE_ALIVE))
                .collect(Collectors.toList());

        List<Player> enmeyAlivePlayers = this.playerMap.values()
                .stream()
                .filter(player -> Objects.equals(player.getTeamId(), this.secondTeamId)
                        && Objects.equals(player.getState(), GameContants.PLAYER_STATE_ALIVE))
                .collect(Collectors.toList());

        if (ourAlivePlayers.size() == 0 || enmeyAlivePlayers.size() == 0) {
            return true;
        }
        return false;
    }

    public void updatePlayerState() {
        for(Map.Entry<String, Player> entry: this.playerMap.entrySet()) {

            String playerName = entry.getKey();
            Player player = entry.getValue();
            if (player.getCurHP() <= 0) {
                player.setState(GameContants.PLAYER_STATE_DEAD);
            }
        }
    }

    public GameStatus deepCopy() {

        GameStatus nextGameStatus = null;

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);

            ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi=new ObjectInputStream(bi);
            nextGameStatus = (GameStatus)(oi.readObject());
        } catch (Exception e){
            logger.error("DeepCopy Failed!!!");
        }
        return nextGameStatus;
    }

    public String repr() {

        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String, Player> entry: this.playerMap.entrySet()) {

            Player player = entry.getValue();
            sb.append(player.repr());
            sb.append("\n");
        }
        return sb.toString();
    }
}
