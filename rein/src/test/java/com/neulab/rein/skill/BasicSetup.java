package com.neulab.rein.skill;

import com.neulab.rein.player.Player;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BasicSetup {

    public static Pair<Player, List<Player>> getAssignPlayers() {

        Double assignHP = 1000.0;
        Double assignEP = 200.0;
        Double assignSP = 0.0;
        Double assignATK = 100.0;

        Player caster = new Player(assignHP, assignEP, assignSP, assignATK);
        List<Player> players = new ArrayList<Player>();
        for (int i=0; i<3; i++) {
            players.add(new Player(assignHP, assignEP, assignSP, assignATK));
        }
        return new Pair<Player, List<Player>>(caster, players);
    }
}
