package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.sun.javafx.application.PlatformImpl;
//import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap;

    @BeforeEach
    void testInit() {
        gameMap = new GameMap(3, 3, CellType.FLOOR, "/map.txt");
    }

    @Test
    void moveUpdatesCells() {
        //given
        Player player = new Player(gameMap.getCell(1, 1), "Bobol");
        //when
        player.move(1, 0);
        //then
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
        assertNull(gameMap.getCell(1, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        //given
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        //when
        player.move(1, 0);
        //then
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void whenCreatingNewPlayer_AllOfAttributesWorkProperly() {
        //given
        Player player = new Player(gameMap.getCell(1, 1));
        //when
        //then
        assertFalse(player.getHasKey());
        assertFalse(player.isGoingUp());
        assertFalse(player.isGoingDown());
        assertFalse(player.isCheatModeOn());
        assertTrue(player.getInventory().getInventoryList().isEmpty());
        assertEquals("Player", player.getName());
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(10, player.getHealth());
        assertEquals(5, player.getAttack());
        assertEquals(0, player.getArmor());
        assertFalse(player.isDead);
        assertEquals("player", player.getTileName());
    }

    @Test
    void whenPlayerHasNoKey_PlayerCannotOpenTheDoor() throws Exception {
        PlatformImpl.startup(() -> {
            //given
            Player player = new Player(gameMap.getCell(1, 1));
            Cell door = gameMap.getCell(2, 1);
            door.setType(CellType.CLOSED_DOOR);
            //when
            player.move(1, 0);
            //then
            assertFalse(player.getHasKey());
            assertEquals(1, player.getX());
            assertEquals(1, player.getY());
        });
    }

    @Test
    void whenPlayerHasKey_PlayerCanOpenTheDoorAndGoThrough() throws Exception {
        PlatformImpl.startup(() -> {
            //given
            Player player = new Player(gameMap.getCell(1, 1));
            Cell door = gameMap.getCell(2, 1);
            door.setType(CellType.CLOSED_DOOR);
            //when
            player.setHasKey(true);
            player.move(1, 0);
            player.move(1, 0);
            //then
            assertTrue(player.getHasKey());
            assertEquals(2, player.getX());
            assertEquals(1, player.getY());
            assertEquals(CellType.OPEN_DOOR, door.getType());
        });
    }

    @Test
    void whenPlayerAttacksDeadlyEnemy_PlayerDies() {
        //given
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        //when
        player.setAttack(1);
        player.setHealth(1);
        player.move(1, 0);
        //then
        assertTrue(player.isDead());
    }

    @Test
    void whenPlayerAttacksEnemy_BattleHappensPlayerLoosesHPEnemySurvives() {
        //given
        Player player = new Player(gameMap.getCell(1,1));
        Ghost ghost = new Ghost(gameMap.getCell(1,2));
        player.setAttack(1);
        player.setHealth(10);
        ghost.setAttack(1);
        ghost.setHealth(2);
        //when
        player.move(0,1);
        //then
        assertEquals(1, ghost.getHealth());
        assertEquals(9, player.getHealth());
        assertFalse(player.isDead());
        assertEquals(player.getCell(), gameMap.getCell(1,1));
    }

    @Test
    void whenPlayerAttacksEnemy_BattleHappensPlayerLoosesHPEnemyDies() {
        //given
        Player player = new Player(gameMap.getCell(1,1));
        Ghost ghost = new Ghost(gameMap.getCell(1,2));
        player.setAttack(1);
        player.setHealth(10);
        ghost.setAttack(1);
        ghost.setHealth(1);
        //when
        player.move(0,1);
        //then
        assertEquals(10, player.getHealth());
        assertFalse(gameMap.getEnemiesList().contains(ghost));
        assertFalse(player.isDead());
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    void whenPlayerMovesIntoCandle_loosesHealth() {
        //given
        gameMap.getCell(2, 1).setType(CellType.CANDLE);
        Player player = new Player(gameMap.getCell(1, 1));
        player.setHealth(10);
        //when
        player.move(1, 0);
        //then
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(9, player.getHealth());
    }

    @Test
    void whenPlayerWalksIntoStairsAndGoesDown_goingDownTriggers() {
        //given
        gameMap.getCell(2, 1).setType(CellType.DOWN_STAIRS);
        Player player = new Player(gameMap.getCell(1, 1));
        //when
        player.move(1, 0);
        player.setGoingDown(true);
        //then
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertTrue(player.isGoingDown());
    }

    @Test
    void whenPlayerWalksIntoStairsAndGoesUP_goingUPTriggers() {
        //given
        gameMap.getCell(2, 1).setType(CellType.UP_STAIRS);
        Player player = new Player(gameMap.getCell(1, 1));
        //when
        player.move(1, 0);
        player.setGoingUp(true);
        //then
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertTrue(player.isGoingUp());
    }

    @Test
    void whenPlayerEntersNameOfOneOfTheDevs_cheatModeIsActivated () {
        //given
        Player player1 = new Player(gameMap.getCell(1,1));
        Player player2 = new Player(gameMap.getCell(1,1));
        Player player3 = new Player(gameMap.getCell(1,1));
        Player player4 = new Player(gameMap.getCell(1,1));
        player1.setName("adam");
        player2.setName("marcelina");
        player3.setName("dymitr");
        player4.setName("damian");
        //when
        player1.checkCheatCode(player1.getName());
        player1.setCheatMode(true);
        player2.checkCheatCode(player2.getName());
        player2.setCheatMode(true);
        player3.checkCheatCode(player3.getName());
        player3.setCheatMode(true);
        player4.checkCheatCode(player4.getName());
        player4.setCheatMode(true);
        //then
        assertTrue(player1.isCheatModeOn());
        assertTrue(player2.isCheatModeOn());
        assertTrue(player3.isCheatModeOn());
        assertTrue(player4.isCheatModeOn());
        assertTrue(player1.checkCheatCode(player1.getName()));
        assertTrue(player2.checkCheatCode(player2.getName()));
        assertTrue(player3.checkCheatCode(player3.getName()));
        assertTrue(player4.checkCheatCode(player4.getName()));
    }

    @Test
    void whenCreatingANewSpider_AllOfAttributesWorkProperly() {
        //given
        Spider spider = new Spider(gameMap.getCell(1, 1));
        //when
        spider.move(1,0);
        //then
        assertEquals(2, spider.getHealth());
        assertEquals(1, spider.getAttack());
        assertEquals("spider", spider.getTileName());
        assertNotEquals(spider.cell, gameMap.getCell(1,1));
    }

    @Test
    void whenCreatingANewWizard_AllOfAttributesWorkProperly() {
        //given
        Wizard wizard = new Wizard(gameMap.getCell(1, 1));
        //when
        wizard.move(1,0);
        //then
        assertEquals(12, wizard.getHealth());
        assertEquals(5, wizard.getAttack());
        assertEquals("wizard", wizard.getTileName());
        assertEquals(wizard.cell, gameMap.getCell(1,1));
    }
}