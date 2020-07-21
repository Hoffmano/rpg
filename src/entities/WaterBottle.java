package entities;

import java.awt.image.BufferedImage;

public class WaterBottle extends Entity{

    public WaterBottle(double x, double y, int WIDTH, int HEIGHT, BufferedImage sprite) {
        super(x, y, WIDTH, HEIGHT, sprite);
    }

    public static void increaseLife() {
        if (Player.life <= 80) {
            Player.life += 20;
        } else {
            Player.life += 100 - Player.life;
        }
    }
}