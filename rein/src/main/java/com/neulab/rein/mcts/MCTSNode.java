package com.neulab.rein.mcts;

import com.neulab.rein.status.GameAction;
import com.neulab.rein.status.GameStatus;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCTSNode {

    private static Logger logger  = LoggerFactory.getLogger(MCTSNode.class);

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

        if (this.isAllExpanded()) {
            logger.warn("Failing expand... All the nodes has been expanded...");
            return this;
        }

        List<String> appliedActionNames = this.children
            .stream()
            .map(mctsNode -> mctsNode.gameStatus.applyActionName)
            .collect(Collectors.toList());
        List<GameAction> availableGameActions = this.gameStatus.getAvailableGameActions();
        Boolean hasExpanded = false;
        GameAction toBeAppliedAction = null;
        while (hasExpanded != true) {
            Random rand = new Random();
            Integer applyActionId = rand.nextInt(availableGameActions.size());
            GameAction applyAction = availableGameActions.get(applyActionId);
            String actionName = applyAction.getName();
            if (appliedActionNames.contains(actionName)) {
                continue;
            }
            hasExpanded = true;
            toBeAppliedAction = applyAction;
        }
        if (toBeAppliedAction == null) {
            logger.warn("Failing expand... The to be applied action is null...");
            return this;
        }

        GameStatus nextGameStatus = this.gameStatus.applySpecialAction(toBeAppliedAction);
        MCTSNode mctsNode = new MCTSNode();
        mctsNode.gameStatus = nextGameStatus;
        mctsNode.parent = this;
        this.children.add(mctsNode);
        return mctsNode; 
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
