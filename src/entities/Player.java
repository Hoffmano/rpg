package entities;

import main.Game;
import world.Camera;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    public boolean right, left, up, down;
    private double speed = 2;
    private boolean moving = false;

    private int spriteIndex = 0, animationIndex = 0;

    private int animationSpeed = 5;

    private BufferedImage[] rightSprites;
    private BufferedImage[] leftSprites;

    public Player(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
        super(x, y, WIDTH, HEIGHT, sprite);

        rightSprites = new BufferedImage[3];
        leftSprites = new BufferedImage[3];
        for (int i = 0; i <= 2; i++)
            rightSprites[i] = Game.spritesheet.getSprite(i + 2, 0);
        for (int i = 2; i >= 0; i--)
            leftSprites[i] = Game.spritesheet.getSprite(i + 2, 1);
    }

    @Override
    public void update() {
        moving = false;

        if (right && World.isFree((int) (x + speed), (int) y)) {
            x += speed;
            moving = true;
        } else if (left && World.isFree((int) (x - speed), (int) y)) {
            x -= speed;
            moving = true;
        }
        if (up && World.isFree((int) x, (int) (y - speed))) {
            y -= speed;
            moving = true;
        } else if (down && World.isFree((int) x, (int) (y + speed))) {
            y += speed;
            moving = true;
        }

        if (moving) {
            animationIndex++;
            if (animationIndex == animationSpeed) {
                spriteIndex++;
                if (spriteIndex > 2)
                    spriteIndex = 0;
                animationIndex = 0;
            }
        } else {
            spriteIndex = 0;
            animationIndex = 0;
        }

        Camera.x = Camera.clamp((int) (this.x - Game.WIDTH / 2), 0, World.WIDTH - Game.WIDTH);
        Camera.y = Camera.clamp((int) (this.y - Game.HEIGHT / 2), 0, World.HEIGHT - Game.HEIGHT);
    }

    @Override
    public void render(Graphics graphics) {
        if (right) {
            graphics.drawImage(rightSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
        } else if (left) {
            graphics.drawImage(leftSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
        }
        if (!(right || left)) {
            graphics.drawImage(rightSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
        }
    }
}
