package com.neulab.rein.player;

import com.neulab.rein.utils.GameContants;

import java.util.GregorianCalendar;
import java.util.List;

public class Player {

    private Double baseHP;
    private Double baseEP;
    private Double baseSP;

    private Double curHP;
    private Double curEP;
    private Double curSP;


    private Double baseATK;
    private Double curATK;

    private Integer shell;
    private Integer encourage;
    private Integer state;

    private List<String> skillTokens;

    public Player(Double baseHP, Double baseEP, Double baseSP, Double baseATK, List<String> skillTokens) {
        this.baseHP = baseHP;
        this.baseEP = baseEP;
        this.baseSP = baseSP;
        this.baseATK = baseATK;
        this.curHP = this.baseHP;
        this.curEP = this.baseEP;
        this.curSP = this.baseSP;
        this.curATK = this.baseATK;
        this.shell = 0;
        this.encourage = 0;
        this.state = GameContants.PLAYER_STATE_ALIVE;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }




    public Integer getEncourage() {
        return encourage;
    }

    public void setEncourage(Integer encourage) {
        this.encourage = encourage;
    }


    public Double getBaseATK() {
        return baseATK;
    }

    public void setBaseATK(Double baseATK) {
        this.baseATK = baseATK;
    }

    public Double getCurATK() {
        return curATK;
    }

    public void setCurATK(Double curATK) {
        this.curATK = curATK;
    }


    public Double getCurHP() {
        return curHP;
    }

    public void setCurHP(Double curHP) {
        this.curHP = curHP;
    }

    public Double getCurEP() {
        return curEP;
    }

    public void setCurEP(Double curEP) {
        this.curEP = curEP;
    }

    public Double getCurSP() {
        return curSP;
    }

    public void setCurSP(Double curSP) {
        this.curSP = curSP;
    }

    public Integer getShell() {
        return shell;
    }

    public void setShell(Integer shell) {
        this.shell = shell;
    }


    public Double getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(Double baseHP) {
        this.baseHP = baseHP;
    }


    public Double getBaseEP() {
        return baseEP;
    }

    public void setBaseEP(Double baseEP) {
        this.baseEP = baseEP;
    }

    public Double getBaseSP() {
        return baseSP;
    }

    public void setBaseSP(Double baseSP) {
        this.baseSP = baseSP;
    }




//    double getBaseHP();
//
//    double getBaseEP();
//
//    double getBaseSP();
//
//    double getBaseATK();
}
