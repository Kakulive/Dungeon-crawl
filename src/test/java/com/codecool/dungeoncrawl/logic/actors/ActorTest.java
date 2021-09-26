package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.sun.javafx.application.PlatformImpl;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR, "/map.txt");

    @Test
    void moveUpdatesCells() {
        //given
        Player player = new Player(gameMap.getCell(1, 1));
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
}