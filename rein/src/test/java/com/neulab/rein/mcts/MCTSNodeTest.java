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



}