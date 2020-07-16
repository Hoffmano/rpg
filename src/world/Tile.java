package world;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {


    private final int x;
    private final int y;

    private final BufferedImage sprite;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x * 16;
        this.y = y * 16;
        this.sprite = sprite;
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, x-Camera.x, y-Camera.y, null);
    }
}
