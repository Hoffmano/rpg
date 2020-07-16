package world;

import entities.Enemy;
import entities.Entity;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {
    public static int WIDTH;
    public static int HEIGHT;
    private static Tile[] tiles;
    private static BufferedImage world;

    public World(String path) {
        try {
            world = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[world.getHeight() * world.getWidth()];
            tiles = new Tile[world.getHeight() * world.getWidth()];
            world.getRGB(0, 0, world.getWidth(), world.getHeight(), pixels, 0, world.getWidth());

            WIDTH = world.getWidth() * 16;
            HEIGHT = world.getHeight() * 16;

            for (int x = 0; x < world.getWidth(); x++) {
                for (int y = 0; y < world.getHeight(); y++) {
                    int currentPixel = pixels[x + y * world.getWidth()];

                    tiles[x + y * world.getWidth()] = new Grass(x, y, Grass.TILE);

                    switch (currentPixel) {
                        case 0xff9baae5:
                            tiles[x + y * world.getWidth()] = new Water(x, y, Water.TILE);
                            break;
                        case 0xff000000:
                            tiles[x + y * world.getWidth()] = new Rock(x, y, Rock.TILE);
                            break;
                        case 0xff1800ff:
                            Game.player.setX(x * 16);
                            Game.player.setY(y * 16);
                            break;
                        case 0xffff0000:
                            Game.entities.add(new Enemy(x * 16, y * 16, 16, 16, Entity.ENEMY));
                            break;
                        case 0xff00ff5a:
                            Game.entities.add(new Entity(x * 16, y * 16, 16, 16, Entity.ROCK));
                            break;
                        case 0xffd200ff:
                            Game.entities.add(new Entity(x * 16, y * 16, 16, 16, Entity.WATER));
                            break;
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFree(int x, int y) {
        return !(
                tiles[((x / 16) + (y / 16) * world.getWidth())] instanceof Rock ||
                tiles[(((x + 15) / 16) + (y / 16) * world.getWidth())] instanceof Rock ||
                tiles[((x / 16) + (y+15) / 16 * world.getWidth())] instanceof Rock ||
                tiles[(((x +15)/ 16) + ((y + 15)/16) * world.getWidth())] instanceof Rock ||
                tiles[((x / 16) + (y / 16) * world.getWidth())] instanceof Water ||
                tiles[(((x + 15) / 16) + (y / 16) * world.getWidth())] instanceof Water ||
                tiles[((x / 16) + (y+15) / 16 * world.getWidth())] instanceof Water ||
                tiles[(((x +15)/ 16) + ((y + 15)/16) * world.getWidth())] instanceof Water
        );
    }

    public void render(Graphics graphics) {
        int startRenderingX = Camera.x >> 4;
        int startRenderingY = Camera.y >> 4;

        int finishRenderingX = startRenderingX + (Game.WIDTH >> 4);
        int finishRenderingY = startRenderingY + (Game.HEIGHT >> 4);

        for (int x = startRenderingX; x <= finishRenderingX; x++) {
            for (int y = startRenderingY; y <= finishRenderingY; y++) {
                if (x < 0 || y < 0 || x >= world.getWidth() || y >= world.getHeight())
                    continue;
                Tile tile = tiles[x + y * world.getWidth()];
                tile.render(graphics);
            }
        }
    }
}
