package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.actors.Spider;
import com.codecool.dungeoncrawl.logic.actors.Wizard;


import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
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
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'd':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            break;
                        case 's':
                            cell.setType(CellType.ENEMY);
                            Skeleton skeleton = new Skeleton(cell);
                            map.addEnemyToList(skeleton);
                            break;
                        case 'p':
                            cell.setType(CellType.ENEMY);
                            Spider spider = new Spider(cell);
                            map.addEnemyToList(spider);
                            break;
                        case 'w':
                            cell.setType(CellType.ENEMY);
                            Wizard wizard = new Wizard(cell);
                            map.addEnemyToList(wizard);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'i':
                            cell.setType(CellType.SWORD);
                            new Sword(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.SHIELD);
                            new Shield(cell);
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
