package com.neulab.rein;

import com.neulab.rein.skill.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        List<Skill> skills = new ArrayList<>();
        skills.add(new Attack());
        skills.add(new DoubleAttack());
        skills.add(new Encourage());
        skills.add(new MasterHeal());
        skills.add(new MasterShell());
        skills.add(new NaiveHeal());
        skills.add(new ShadowRaid());

        System.out.println("Hello World");

        skills.stream()
                .forEach(System.out::println);

    }

}
