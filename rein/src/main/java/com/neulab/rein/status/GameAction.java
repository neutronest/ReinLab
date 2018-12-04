package com.neulab.rein.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.neulab.rein.player.Player;
import com.neulab.rein.skill.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameAction implements Serializable {

    public Player caster;
    public List<Player> targetPlayers;
    public Skill applySkill;

    private static Logger logger = LoggerFactory.getLogger(GameAction.class);


    public GameAction(Player caster, List<Player> targePlayers, Skill applySkill) {
        this.caster = caster;
        this.targetPlayers = targePlayers;
        this.applySkill = applySkill;
    }

    public static List<GameAction> getAvailableGameActions(List<GameAction> gameActionCandidates) {

        List<GameAction> availableGameActions = new ArrayList<>();
        for (GameAction gameAction: gameActionCandidates) {
            if (gameAction.applySkill.isAvailable(gameAction.caster, gameAction.targetPlayers)) {
                availableGameActions.add(gameAction);
            }
        }
        return availableGameActions;
    }

    public void apply() {
        this.applySkill.apply(caster, targetPlayers);
        logger.warn(String.format("apply: %s", this.toString()));
        System.out.println(String.format("apply: %s", this.toString()));

    }

    @Override
    public String toString() {
        String casterName = this.caster.getDisplayName();
        String targetPlayerName = String.join(
            " ",
            this.targetPlayers.stream().map(player -> player.getDisplayName()).collect(Collectors.toList())
            );
        String skillName = this.applySkill.getDisplayName();
        return String.format("%s, %s, %s", casterName, targetPlayerName, skillName);
    }
}