/*
 * Copyright (c) Erik Partridge 2015. All rights reserved, program is for TakeoffSim.com
 */

/*
 * (c) Erik Malmstrom-com.erikpartridge 2014
 */

package com.takeoffsim.main;

/**
 * @author Erik
 */
public final class GameProperties {


    private static String nameOfSim;

    private static String unit;

    private static int investorDifficulty = 4;

    private GameProperties() {
    }


    /**
     * @return the nameOfSim
     */
    public static String getNameOfSim() {
        return nameOfSim;
    }


    /**
     * @param nos the nameOfSim to set
     */
    public static void setNameOfSim(String nos) {
        nameOfSim = nos;
    }

    public static int getInvestorDifficulty(){
        return investorDifficulty;
    }

    public static void setInvestorDifficulty(int diff){
        investorDifficulty = diff;
    }
}

