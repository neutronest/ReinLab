package com.neulab.rein.skill;

import java.util.List;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.neulab.rein.player.Player;
import com.neulab.rein.utils.GameContants;
import com.neulab.rein.skill.ShadowAttack;
import static com.neulab.rein.BasicSetup.getBoss;
import static com.neulab.rein.BasicSetup.getAssignPlayers;

public class ShadowAttackTest {


    @Test
    public void ShouldBeAvailable() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        Player leon = players.get(2);

        ShadowAttack shadowAttack = new ShadowAttack();
        Assert.assertEquals(true, shadowAttack.isAvailable(leon, players));
    }

    @Test
    public void ShouldNotBeAvailablewhenSPNotEnough() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        Player leon = players.get(2);
        leon.setCurSP(20.0);

        ShadowAttack shadowAttack = new ShadowAttack();
        Assert.assertEquals(false, shadowAttack.isAvailable(leon, players));
    }

    @Test
    public void ShouldNotBeAvailablewhenTargetPlayerDead() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        Player leon = players.get(2);
        Player targetPlayer = players.get(0);
        targetPlayer.setState(GameContants.PLAYER_STATE_DEAD);

        ShadowAttack shadowAttack = new ShadowAttack();
        Assert.assertEquals(false, shadowAttack.isAvailable(leon, players));
    }

    

    @Test
    public void ShouldEffect() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        Player leon = players.get(2);
        Player targetPlayer = players.get(0);
        Double targetPlayerHP = targetPlayer.getCurHP();
        ShadowAttack shadowAttack = new ShadowAttack();
        shadowAttack.apply(leon, players);
        System.out.println(String.format("%.2f", targetPlayerHP - 1.6 * leon.getCurATK() ));
        System.out.println(String.format("%.2f", targetPlayer.getCurHP().doubleValue() ));
        Assert.assertTrue(
            targetPlayerHP - 1.6 * leon.getCurATK() == 
            targetPlayer.getCurHP().doubleValue());
    }

    public void shouldEffectMoreWhenCasterEncouraged() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        Player leon = players.get(2);
        leon.setEncourage(3);
        Player targetPlayer = players.get(0);
        Double targetPlayerHP = targetPlayer.getCurHP();
        ShadowAttack shadowAttack = new ShadowAttack();
        shadowAttack.apply(leon, players);
        System.out.println(String.format("%.2f", targetPlayerHP - 1.6 * leon.getCurATK() ));
        System.out.println(String.format("%.2f", targetPlayer.getCurHP().doubleValue() ));
        Assert.assertTrue(
            targetPlayerHP - 1.5 * 1.6 * leon.getCurATK() == 
            targetPlayer.getCurHP().doubleValue());
    }

    public void ShouldNotEffectWhenTargetPlayersHasShell() {
        Pair<Player, List<Player>> playerPair = getAssignPlayers();
        Player caster = playerPair.getKey();
        List<Player> players = playerPair.getValue();
        Player leon = players.get(2);
        Player targetPlayer = players.get(0);
        targetPlayer.setShell(1);
        Double targetPlayerHP = targetPlayer.getCurHP();
        ShadowAttack shadowAttack = new ShadowAttack();
        shadowAttack.apply(leon, players);
        System.out.println(String.format("%.2f", targetPlayerHP - 1.6 * leon.getCurATK() ));
        System.out.println(String.format("%.2f", targetPlayer.getCurHP().doubleValue() ));
        Assert.assertTrue(
            targetPlayerHP == 
            targetPlayer.getCurHP().doubleValue());

    }

}
