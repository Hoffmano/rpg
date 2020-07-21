package world;

import main.Game;

import java.awt.image.BufferedImage;

public class Water extends Tile {
    public static final BufferedImage TILE = Game.spriteSheet.getSprite(0, 1);

    public Water(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }
}
