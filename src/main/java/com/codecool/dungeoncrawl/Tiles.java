package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(20, 8));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("sword", new Tile(0,30));
        tileMap.put("heart", new Tile(23,22));
        tileMap.put("key", new Tile(16,23));
        tileMap.put("open_door", new Tile(12,11));
        tileMap.put("closed_door", new Tile(13,11));
        tileMap.put("down_stairs", new Tile(1,6));
        tileMap.put("up_stairs", new Tile(2,6));
        tileMap.put("wizard", new Tile(24, 1));
        tileMap.put("spider", new Tile(29, 5));
        tileMap.put("shield", new Tile(5,26));
        tileMap.put("candle", new Tile(4,15));
        tileMap.put("grass", new Tile(21,2));
        tileMap.put("bones", new Tile(0,15));
        tileMap.put("ghost", new Tile(26,6));


    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
