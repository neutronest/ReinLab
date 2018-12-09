package com.neulab.rein.status;

import java.io.*;
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

    public String getName() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.caster.getName());
        sb.append("@@");
        sb.append(String.join("@@", targetPlayers.stream().map(player -> player.getName()).collect(Collectors.toList())));
        sb.append("@@");
        sb.append(applySkill.getName());
        return sb.toString();
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


    public GameAction deepCopy() {

        GameAction gameActionCopy = null;

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);

            ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi=new ObjectInputStream(bi);
            gameActionCopy = (GameAction)(oi.readObject());
        } catch (Exception e){
            logger.error("DeepCopy Failed!!!");
        }
        return gameActionCopy;
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