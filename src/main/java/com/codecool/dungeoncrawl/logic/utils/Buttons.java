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
                if (validHealth(sceneSwitcher)) {
                    map.getPlayer().setHealth(health - maxSub);
                    sceneSwitcher.setSeparationStatistic(maximumToAdd + numberToScene);
                    sceneSwitcher.getHealthLabel().setText("" + map.getPlayer().getHealth());
                } else {
                    map.getPlayer().setHealth(PlayerStats.HEALTH.getValueStat());
                    MessageFlashing.flashMessage("That game is hard to play. Is better when you have more HP");
                }
            } else if (numberAddButtons == 2) {
                if (validArmor(sceneSwitcher)) {
                    map.getPlayer().setArmor(armor - maxSub);
                    sceneSwitcher.setSeparationStatistic(maximumToAdd + numberToScene);
                    sceneSwitcher.getArmorLabel().setText("" + map.getPlayer().getArmor());
                } else {
                    map.getPlayer().setArmor(PlayerStats.ARMOR.getValueStat());
                    MessageFlashing.flashMessage("0 armor is small value. And you want have smaller value???");
                }
            } else if (numberAddButtons == 3){
                if (validAttack(sceneSwitcher)) {
                    map.getPlayer().setAttack(attack - maxSub);
                    sceneSwitcher.setSeparationStatistic(maximumToAdd + numberToScene);
                    sceneSwitcher.getAttackLabel().setText("" + map.getPlayer().getAttack());
                } else {
                    map.getPlayer().setAttack(PlayerStats.ATTACK.getValueStat());
                    MessageFlashing.flashMessage("Don't subtract attack!!!");
                }
            }
        } else {
            MessageFlashing.flashMessage("You can't subtract stats");
        }

    }

    public void submitButtonDo(GameMap map, SceneSwitcher sceneSwitcher) {
        int addingStatisticNumber = sceneSwitcher.getSeparationStatistic();

        if (addingStatisticNumber > 0) {
            MessageFlashing.flashMessage("You have some stat points to add.");
        } else {
            if (sceneSwitcher.getPlayerNameInput().getText().equals("")) {
                MessageFlashing.flashMessage("All heroes have name. Don't be anonymous.");
            } else {
                map.getPlayer().setName(sceneSwitcher.getPlayerNameInput().getText());
                sceneSwitcher.getPlayerName().setText("" + map.getPlayer().getName());
            }
        }
    }

    public boolean validInputsAddingMenu(SceneSwitcher sceneSwitcher) {
        int addingStatisticNumber = sceneSwitcher.getSeparationStatistic();

        if (addingStatisticNumber > 0 && sceneSwitcher.getPlayerNameInput().getText().equals("")) {
            MessageFlashing.flashMessage("You have some stat points to add and set heroes name");
            return false;
        }
        else if (addingStatisticNumber > 0) {
            MessageFlashing.flashMessage("You have some stat points to add.");
            return false;
        }
        else if (sceneSwitcher.getPlayerNameInput().getText().equals("")) {
            MessageFlashing.flashMessage("All heroes have name. Don't be anonymous.");
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validHealth (SceneSwitcher sceneSwitcher) {
        return (PlayerStats.HEALTH.getValueStat()) < emptyStrToInt(sceneSwitcher.getHealthLabel().getText());
    }

    private boolean validArmor (SceneSwitcher sceneSwitcher) {
        return PlayerStats.ARMOR.getValueStat() < emptyStrToInt(sceneSwitcher.getArmorLabel().getText());
    }

    private boolean validAttack(SceneSwitcher sceneSwitcher) {
        return PlayerStats.ATTACK.getValueStat() < emptyStrToInt(sceneSwitcher.getAttackLabel().getText());
    }

    private int emptyStrToInt(String valueFromScene) {
        if (valueFromScene.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(valueFromScene);
        }
    }
}
