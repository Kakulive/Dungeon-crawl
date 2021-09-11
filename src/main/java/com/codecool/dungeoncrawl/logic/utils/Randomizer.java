package com.codecool.dungeoncrawl.logic.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Randomizer {

    private Random random = new Random();

    public int rollRandomMove() {
        List<Integer> validNumbers = Arrays.asList(-1, 0, 1);
        int randomInt = validNumbers.get(random.nextInt(validNumbers.size()));
        return randomInt;
    }
}
