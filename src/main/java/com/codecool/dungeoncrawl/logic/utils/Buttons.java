package com.codecool.dungeoncrawl.logic.utils;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.PlayerStats;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class Buttons {
    //    1 - addStatHealth analogical in subStatButtons
    //    2 - addStatArmor
    //    3 - addStatAttack

    public void addStatButtons(SceneSwitcher sceneSwitcher, int numberAddButtons, GameMap map) {
        int maximumToAdd = sceneSwitcher.getSeparationStatistic();
        int maxAdd = 1;
        int health = map.getPlayer().getHealth();
        int armor = map.getPlayer().getArmor();
        int attack = map.getPlayer().getAttack();
        if (maximumToAdd > 0) {
            if (numberAddButtons == 1) {
                map.getPlayer().setHealth(health + maxAdd);
                sceneSwitcher.setSeparationStatistic(maximumToAdd - 1);
                sceneSwitcher.getHealthLabel().setText("" + map.getPlayer().getHealth());
            }
            else if (numberAddButtons == 2) {
                map.getPlayer().setArmor(armor + maxAdd);
                sceneSwitcher.setSeparationStatistic(maximumToAdd - 1);
                sceneSwitcher.getArmorLabel().setText("" + map.getPlayer().getArmor());
            }
            else if (numberAddButtons == 3){
                map.getPlayer().setAttack(attack + maxAdd);
                sceneSwitcher.setSeparationStatistic(maximumToAdd - 1);
                sceneSwitcher.getAttackLabel().setText("" + map.getPlayer().getAttack());
            }
        }
        else {
            MessageFlashing.flashMessage("You don't have stats to add");
        }

    }

    public void subStatButtons(SceneSwitcher sceneSwitcher, int numberAddButtons, GameMap map) {
        int maximumToAdd =  sceneSwitcher.getSeparationStatistic();
        int maxSub = 1;
        int numberToScene = 1;
        int health = map.getPlayer().getHealth();
        int armor = map.getPlayer().getArmor();
        int attack = map.getPlayer().getAttack();

        int sumStatsPlayer = health + armor + attack;
        int sumStatsConstant = PlayerStats.HEALTH.getValueStat() + PlayerStats.ATTACK.getValueStat() +
                PlayerStats.ARMOR.getValueStat();

        if (sumStatsPlayer > sumStatsConstant) {
            if (numberAddButtons == 1) {
                map.getPlayer().setHealth(health - maxSub);
                sceneSwitcher.setSeparationStatistic(maximumToAdd + numberToScene);
                sceneSwitcher.getHealthLabel().setText("" + map.getPlayer().getHealth());
            } else if (numberAddButtons == 2) {
                map.getPlayer().setArmor(armor - maxSub);
                sceneSwitcher.setSeparationStatistic(maximumToAdd + numberToScene);
                sceneSwitcher.getArmorLabel().setText("" + map.getPlayer().getArmor());
            } else if (numberAddButtons == 3){
                map.getPlayer().setAttack(attack - maxSub);
                sceneSwitcher.setSeparationStatistic(maximumToAdd + numberToScene);
                sceneSwitcher.getAttackLabel().setText("" + map.getPlayer().getAttack());
            }
        } else {
            MessageFlashing.flashMessage("You can't subtract stats");
        }

    }

    public void submitButtonDo(GameMap map, SceneSwitcher sceneSwitcher) {
        map.getPlayer().setName(sceneSwitcher.getPlayerNameInput().getText());
        sceneSwitcher.getPlayerName().setText("" + map.getPlayer().getName());
    }
}
