package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import static com.neulab.rein.skill.BasicSetup.getAssignPlayers;

public class AttackTest {



    @Test
    public void shouldBeAvailable() throws Exception {

        Attack attackSkill = new Attack();

        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        Assert.assertEquals(true, attackSkill.isAvailable(caster, players));
    }

    @Test
    public void shouldBeEffective() throws Exception {

        Attack attackSkill = new Attack();
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        List<Player> targetPlayers = new ArrayList<Player>();
        targetPlayers.add(players.get(0));


        Double originalHP = targetPlayers.get(0).getCurHP();
        attackSkill.apply(caster, targetPlayers);
        assert((double)(originalHP - caster.getCurATK())
                == (double)targetPlayers.get(0).getCurHP());
    }

    @Test
    public void shouldBeNotEffectiveWhenTargetPlayersMoreThenOne() throws Exception {
        Attack attackSkill = new Attack();
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();

        Double originalHP = players.get(0).getCurHP();
        attackSkill.apply(caster, players);
        assert((double)(originalHP)
                == (double)players.get(0).getCurHP());
    }
}