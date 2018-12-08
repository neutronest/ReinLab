package com.neulab.rein.mcts;

import com.neulab.rein.status.GameStatus;

import java.util.ArrayList;
import java.util.List;

public class MCTSNode {

    public MCTSNode parent;
    public List<MCTSNode> children;
    public GameStatus gameStatus;
    public Integer visitTimes;
    public Double qValue;

    public MCTSNode() {
        this.parent = null;
        this.children = new ArrayList<>();
        this.gameStatus = null;
        this.visitTimes = 0;
        this.qValue = 0.0;
    }

    public Boolean isAllExpanded() {

        Integer availableGameActionCount = this.gameStatus.getAvailableGameActions().size();
        if (availableGameActionCount.intValue() == this.children.size()) {
            return true;
        }
        return false;
    }

    public MCTSNode expandNode() {
        return null;
    }

    public MCTSNode getBestChild(Integer isExploration) {
        
        return null;
    }

    public Double defaultPolicy() {
        
        GameStatus currentGameStatus = this.gameStatus;
        while (currentGameStatus.isTerminated() != true) {
            currentGameStatus = currentGameStatus.applyRandomAction();
        }
        return currentGameStatus.getReward();
    }

    public MCTSNode treePolicy() {
        return null;
    }
}
