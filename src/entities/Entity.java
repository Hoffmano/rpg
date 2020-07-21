package entities;

import main.Game;
import world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public static final BufferedImage CHICKEN_THIGH = Game.spriteSheet.getSprite(6, 0);
    public static final BufferedImage WATER = Game.spriteSheet.getSprite(6, 1);
    public static final BufferedImage ROCK = Game.spriteSheet.getSprite(8, 0);
    public static final BufferedImage ENEMY = Game.spriteSheet.getSprite(7, 1);
    public static final BufferedImage STICK = Game.spriteSheet.getSprite(7, 0);
    protected final int WIDTH, HEIGHT;
    protected double x, y;
    protected BufferedImage sprite;
    protected Rectangle mask;

    public Entity(double x, double y, int WIDTH, int HEIGHT, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.sprite = sprite;

        mask = new Rectangle((int)x,(int)y,WIDTH,HEIGHT);
    }

    public void update() {
        updateMask();
    }

    public void render(Graphics graphics) {
        graphics.drawImage(sprite, (int) this.x - Camera.x, (int) this.y - Camera.y, WIDTH, HEIGHT, null);
        // graphics.setColor(Color.MAGENTA);
        // graphics.drawRect(mask.x- Camera.x, mask.y- Camera.y, WIDTH, HEIGHT);
    }

    public void updateMask(){
        mask.setLocation(this.getX(), this.getY());
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
