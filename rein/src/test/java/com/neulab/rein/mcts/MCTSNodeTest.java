package com.neulab.rein.mcts;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.neulab.rein.mcts.MCTSNode;

import java.util.ArrayList;
import static com.neulab.rein.BasicSetup.*;

public class MCTSNodeTest {

    private static Logger logger = LoggerFactory.getLogger(MCTSNodeTest.class);

    @Test
    public void shouldNotAllExpanded() {
        MCTSNode mctsNode = getStartMCTSNode();
        Assert.assertEquals(mctsNode.isAllExpanded(), false);
    }

    @Test
    public void shouldAllExpanded() {
        MCTSNode expandedMCTSNode = getAllExpandedMCTSNode();
        Assert.assertEquals(expandedMCTSNode.isAllExpanded(), true);
    }

    @Test
    public void shouldGetDefaultPolicy() {
        for(int i=0; i<10; i++) {
            MCTSNode mctsNode = getStartMCTSNode();
            Double reward = mctsNode.defaultPolicy();
            logger.info(String.format("Reward for default policy: %.2f", reward));
        }
    }

    @Test
    public void shouldGetTreePolicy() {
        MCTSNode mctsNode = getStartMCTSNode();
        mctsNode.treePolicy();

    }

    @Test
    public void shouldGetUCTSearch() {
        MCTSNode mctsNode = getStartMCTSNode();
        mctsNode.UCTSearch(10);
    }
}