package entities;

import main.Game;
import world.Camera;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.enemies;

public class Enemy extends Entity {
    private double speed = 1;

    private int animationIndex = 0;
    private int animationSpeed = 5;
    private int spriteIndex = 0;

    private BufferedImage[] sprites;

    public Enemy(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
        super(x, y, WIDTH, HEIGHT, sprite);
        sprites = new BufferedImage[3];
        sprites[0] = Game.spriteSheet.getSprite(7, 1);
        sprites[1] = Game.spriteSheet.getSprite(8, 1);
        sprites[2] = Game.spriteSheet.getSprite(9, 1);
    }

    @Override
    public void update() {
        if (!isCollidingWithPlayer()) {
            if (x < Game.player.getX() && World.isFree((int) (x + speed), (int) y)
                    && !isColliding((int) (x + speed), (int) y)) {
                x += speed;
            } else if (x > Game.player.getX() && World.isFree((int) (x - speed), (int) y)
                    && !isColliding((int) (x - speed), (int) y)) {
                x -= speed;
            }
            if (y < Game.player.getY() && World.isFree((int) (x), (int) (y + speed))
                    && !isColliding((int) (x), (int) (y + speed))) {
                y += speed;
            } else if (y > Game.player.getY() && World.isFree((int) (x), (int) (y - speed))
                    && !isColliding((int) (x), (int) (y - speed))) {
                y -= speed;
            }
        } else {
            if (Game.random.nextInt(100) < 10) {
                Player.life -= 1;
            }
        }

        animationIndex++;
        if (animationIndex == animationSpeed) {
            spriteIndex++;
            if (spriteIndex > 2)
                spriteIndex = 0;
            animationIndex = 0;
        }
    }
    
    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(sprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
    }

    public boolean isColliding(int x, int y) {
        Rectangle currentEnemyRectangle = new Rectangle(x, y, 16, 16);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy otherEnemy = enemies.get(i);

            if (otherEnemy == this) {
                continue;
            }

            Rectangle otherEnemyRectangle = new Rectangle(otherEnemy.getX(), otherEnemy.getY(), 16, 16);

            if (otherEnemyRectangle.intersects(currentEnemyRectangle)) {
                return true;
            }
        }

        return false;
    }

    public boolean isCollidingWithPlayer() {
        Rectangle currentEnemyRectangle = new Rectangle(this.getX(), this.getY(), 16, 16);
        Rectangle playerRectangle = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
        
        return currentEnemyRectangle.intersects(playerRectangle);
    }
}
