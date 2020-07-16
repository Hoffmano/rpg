package entities;

import main.Game;
import world.World;

import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private double speed = 1;

    public Enemy(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
        super(x, y, WIDTH, HEIGHT, sprite);
    }

    @Override
    public void update() {
        if (x < Game.player.getX() && World.isFree((int) (x + speed), (int) y)) {
            x += speed;
        } else if (x > Game.player.getX() && World.isFree((int) (x - speed), (int) y)) {
            x -= speed;
        }
        if (y < Game.player.getY() && World.isFree((int) (x), (int) (y + speed))) {
            y += speed;
        } else if (y > Game.player.getY() && World.isFree((int) (x), (int) (y - speed))) {
            y -= speed;
        }
    }
}
