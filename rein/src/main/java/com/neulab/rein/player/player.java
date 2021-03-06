package com.neulab.rein.player;

import com.neulab.rein.status.GameStatus;
import com.neulab.rein.utils.GameContants;

import java.io.*;
import java.util.List;

public class Player implements Serializable {

    private String name;
    private String displayName;

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
    private Integer teamId;

    public Player(
            String name,
            String displayName,
            Double baseHP,
            Double baseEP,
            Double baseSP,
            Double baseATK,
            List<String> skillTokens,
            Integer teamId) {
        this.name = name;
        this.displayName = displayName;
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
        this.skillTokens = skillTokens;
        this.teamId = teamId;
    }

    public String repr() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%s, %.1f/%.1f/%.1f, Shell:%d, Encourage:%d",
                this.displayName, this.getCurHP(), this.getCurEP(), this.getCurSP(),
                this.getShell(), this.getEncourage()));
        return sb.toString();
    }

    public Player deepCopy() {

        Player playerCopy = null;

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);

            ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi=new ObjectInputStream(bi);
            playerCopy = (Player)(oi.readObject());
        } catch (Exception e){
            //logger.error("DeepCopy Failed!!!");
        }
        return playerCopy;
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


    public Integer getTeamId() {
        return this.teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSkillTokens(List<String> skillTokens) {
        this.skillTokens = skillTokens;
    }

    public List<String> getSkillTokens() {
        return this.skillTokens;
    }



//    double getBaseHP();
//
//    double getBaseEP();
//
//    double getBaseSP();
//
//    double getBaseATK();
}
