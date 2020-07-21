package world;

import main.Game;

import java.awt.image.BufferedImage;

public class Grass extends Tile {
    public static final BufferedImage TILE = Game.spriteSheet.getSprite(0, 0);

    public Grass(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }
}
