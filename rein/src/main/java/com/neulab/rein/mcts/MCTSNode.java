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

    public MCTSNode UCTSearch(Integer computationalBudget) {

        for(int epoch=0; epoch < computationalBudget; epoch++) {
            //System.out.println("epoch: " + epoch);
            MCTSNode mctsLeaf = this.treePolicy();
            Double reward = mctsLeaf.defaultPolicy();
            mctsLeaf.backProp(reward);
        }
        // TODO DEV
        return this;

    }

    public void replay() throws InterruptedException {

        MCTSNode curNode = this;
        while (curNode.gameStatus.isTerminated() != true) {
            curNode.UCTSearch(100);
            System.out.println(curNode.gameStatus.applyActionName);
            System.out.println(curNode.gameStatus.repr());
            MCTSNode nextNode = curNode.getBestChild(0);
            if (nextNode == null) {
                System.out.println("Search is not enough");
                GameStatus nextGameStatus = curNode.gameStatus.applyRandomAction();
                nextNode = new MCTSNode();
                nextNode.gameStatus = nextGameStatus;
                nextNode.parent = curNode;
            }
            curNode = nextNode;
            //Thread.sleep(1000);
        }
        System.out.println("Result: ");
        System.out.println(curNode.gameStatus.repr());
    }

    public MCTSNode treePolicy() {

        MCTSNode curNode = this;
        while (curNode.gameStatus.isTerminated() != true) {
            if (curNode.isAllExpanded() != true) {
                return curNode.expandNode();
            } else {
                curNode = curNode.getBestChild(1);
            }
        }
        return curNode;
    }

    public Double defaultPolicy() {
        
        GameStatus currentGameStatus = this.gameStatus;
        while (currentGameStatus.isTerminated() != true) {
            currentGameStatus = currentGameStatus.applyRandomAction();
        }
        return currentGameStatus.getReward();
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
        
        if (this.gameStatus.isEnemyTurn()) {
            // when enemy turn
            // select an available action randomly
            GameStatus nextGameStatus = this.gameStatus.applyRandomAction();
            for (MCTSNode childNode : this.children) {
                if (childNode.gameStatus.applyActionName.equals(nextGameStatus.applyActionName)) {
                    return childNode;
                }
            }
            // expand new node
            MCTSNode mctsNode = new MCTSNode();
            mctsNode.gameStatus = nextGameStatus;
            this.children.add(mctsNode);
            return mctsNode;
        }

        Double maxValue = -9999.0;
        MCTSNode choiceNode = null;
        for (MCTSNode childNode: this.children) {
            Double tempValue = childNode.qValue / childNode.visitTimes + isExploration * Math.sqrt(2 * Math.log(this.visitTimes) / childNode.visitTimes);
            if (maxValue < tempValue) {
                maxValue = tempValue;
                choiceNode = childNode;
            }
        }
        return choiceNode;
    }
    
    public void backProp(Double reward) {

        MCTSNode curNode = this;
        while (curNode != null) {
            curNode.visitTimes += 1;
            curNode.qValue += reward;
            curNode = curNode.parent;
        }
        return;        
    }

    

    
}
