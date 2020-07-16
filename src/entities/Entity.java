package entities;

import main.Game;
import world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public static final BufferedImage CHICKEN_THIGH = Game.spritesheet.getSprite(6, 0);
    public static final BufferedImage WATER = Game.spritesheet.getSprite(6, 1);
    public static final BufferedImage ROCK = Game.spritesheet.getSprite(8, 0);
    public static final BufferedImage ENEMY = Game.spritesheet.getSprite(7, 1);
    public static final BufferedImage STICK = Game.spritesheet.getSprite(7, 0);
    protected final int WIDTH, HEIGHT;
    protected double x, y;
    protected BufferedImage sprite;

    public Entity(double x, double y, int WIDTH, int HEIGHT, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.sprite = sprite;
    }

    public void update() {
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, (int) this.x - Camera.x, (int) this.y - Camera.y, WIDTH, HEIGHT, null);
    }

    public int getY() {
        return (int) y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getX() {
        return (int) x;
    }

    public void setX(double x) {
        this.x = x;
    }

}
