package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.neulab.rein.skill.BasicSetup.getAssignPlayers;

public class DoubleAttackTest {

    @Test
    public void shouldBeAvailable() {

        DoubleAttack doubleAttackSkill = new DoubleAttack();
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        caster.setCurSP(100.0);

        List<Player> players = playerPair.getValue();
        List<Player> targetPlayers = new ArrayList<Player>();
        targetPlayers.add(players.get(0));

        Assert.assertEquals(true, doubleAttackSkill.isAvailable(caster, targetPlayers));
    }

    @Test
    public void shouldBeNotAvailableWhenTargetMoreThenOne() {
        DoubleAttack doubleAttackSkill = new DoubleAttack();
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();


        Assert.assertEquals(false, doubleAttackSkill.isAvailable(caster, players));

    }

    @Test
    public void shouldBeNotAvailableWhenSPNotEnough() {
        DoubleAttack doubleAttackSkill = new DoubleAttack();
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();

        List<Player> targetPlayers = new ArrayList<Player>();
        targetPlayers.add(players.get(0));

        caster.setCurSP(0.0);
        Assert.assertEquals(false, doubleAttackSkill.isAvailable(caster, targetPlayers));
    }


}
