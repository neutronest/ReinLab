package com.neulab.rein.skill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neulab.rein.player.Player;
import java.util.List;

public class Attack implements Skill {

    private static Logger logger = LoggerFactory.getLogger(Attack.class);

    public static String name = "NormalAttack";
    public static String displayName = "普通攻击";

    @Override
    public void apply(Player caster, List<Player> targetPlayers) {

        if (targetPlayers == null) {
            logger.error("players must not been null!");
        }
        if (targetPlayers.size() != 1) {
            logger.warn("the target of normal attack must not been none or exceed for one player!");
            return;
        }

        Player targetPlayer = targetPlayers.get(0);

        Integer targetPlayerShell = targetPlayer.getShell();
        if (targetPlayerShell > 0) {
            targetPlayer.setShell(targetPlayerShell-1);
            return;
        }

        Double targetPlayerCurHP = targetPlayer.getCurHP();
        targetPlayer.setCurHP(Math.max(targetPlayerCurHP - caster.getCurATK(), 0));
        return;
    }

    @Override
    public Boolean isAvailable(Player caster, List<Player> targetPlayers) {
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
