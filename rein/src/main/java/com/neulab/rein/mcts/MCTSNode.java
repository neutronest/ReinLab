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
        return false;
    }

    public MCTSNode expandNode() {
        return null;
    }

    public MCTSNode getBestChild() {
        return null;
    }

    public Double defaultPolicy() {
        return 0.0;
    }

    public MCTSNode treePolicy() {
        return null;
    }
}
