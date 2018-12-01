package com.neulab.rein.status;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;

import java.util.*;
import java.util.stream.Collectors;

public class GameStatus {


    private Map<String, Player> playerMap = new HashMap<>();
    public String playerActionToken = "";
    public Queue<String> actionQueue = new ArrayDeque<>();

    

    public GameStatus(List<Player> players) {

        for (Player player: players) {
            this.playerMap.put(player.getName(), player);
        }

        for (Player player: players) {
            this.actionQueue.add(player.getName());
        }

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
                    player.getIsEnemy()) {
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
                .filter( player -> !player.getIsEnemy() && player.getState() == GameContants.PLAYER_STATE_ALIVE)
                .collect(Collectors.toList());

        List<Player> enmeyAlivePlayers = this.playerMap.values()
                .stream()
                .filter(player -> player.getIsEnemy()  && player.getState() == GameContants.PLAYER_STATE_ALIVE)
                .collect(Collectors.toList());

        if (ourAlivePlayers.size() == 0 || enmeyAlivePlayers.size() == 0) {
            return true;
        }
        return false;
    }
}
