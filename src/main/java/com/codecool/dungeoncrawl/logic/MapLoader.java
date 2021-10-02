package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Heart;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.items.Key;


import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapName) {
        InputStream is = MapLoader.class.getResourceAsStream(mapName);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY, mapName);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'l':
                            cell.setType(CellType.DOWN_STAIRS);
                            break;
                        case 'j':
                            cell.setType(CellType.UP_STAIRS);
                            break;
                        case 'd':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            Skeleton skeleton = new Skeleton(cell);
                            map.addEnemyToList(skeleton);
                            map.addAllEnemyToList(skeleton);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            Spider spider = new Spider(cell);
                            map.addEnemyToList(spider);
                            map.addAllEnemyToList(spider);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            Wizard wizard = new Wizard(cell);
                            map.addEnemyToList(wizard);
                            map.addAllEnemyToList(wizard);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            Ghost ghost = new Ghost(cell);
                            map.addEnemyToList(ghost);
                            map.addAllEnemyToList(ghost);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            Key key = new Key(cell);
                            map.addItemToList(key);
                            map.addAllItemToList(key);

                            break;
                        case 'x':
                            cell.setType(CellType.HEART);
                            Heart heart = new Heart(cell);
                            map.addItemToList(heart);
                            map.addAllItemToList(heart);
                            break;
                        case 'i':
                            cell.setType(CellType.SWORD);
                            Sword sword = new Sword(cell);
                            map.addItemToList(sword);
                            map.addAllItemToList(sword);
                            break;
                        case 'h':
                            cell.setType(CellType.SHIELD);
                            Shield shield = new Shield(cell);
                            map.addItemToList(shield);
                            map.addAllItemToList(shield);
                            break;
                        case 'n':
                            cell.setType(CellType.CANDLE);
                            break;
                        case 'z':
                            cell.setType(CellType.GRASS);
                            break;
                        case 'b':
                            cell.setType(CellType.BONES);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
