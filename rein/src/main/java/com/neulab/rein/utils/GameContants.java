package com.neulab.rein.utils;

public class GameContants {

    public static Integer PLAYER_STATE_ALIVE = 0;
    public static Integer PLAYER_STATE_DEAD = 1;

    public static Integer SKILL_USED_FOR_TEAM_0 = 0;
    public static Integer SKILL_USED_FOR_TEAM_1 = 1;


    public static Integer SKILL_TYPE_SINGLE_FOR_SELF = 0;
    public static Integer SKILL_TYPE_SINGLE_FOR_FRIENDS = 1;
    public static Integer SKILL_TYPE_MULTI_FOR_FRIENDS = 2;
    public static Integer SKILL_TYPE_SINGLE_FOR_SELF_FRIENDS = 3;
    public static Integer SKILL_TYPE_MULTI_FOR_SELF_FRIENDS = 4;
    public static Integer SKILL_TYPE_SINGLE_FOR_ENEMY = 5;
    public static Integer SKILL_TYPE_MULTI_FOR_ENEMY = 6;


    public static String EXCEPTION_GAME_IS_TERMINATED = "the game has been terminated";
    public static String EXCEPTION_NO_CORRESPONDING_PLAYER = "no corresponding player founded";
}
