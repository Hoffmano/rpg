package world;

import main.Game;

import java.awt.image.BufferedImage;

public class Rock extends Tile {
    public static final BufferedImage TILE = Game.spritesheet.getSprite(1, 0);

    public Rock(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }
}
