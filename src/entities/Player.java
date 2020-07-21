package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity {
    public static int life = 100;
    public static boolean criticalStateLive = false;
    public static int rocks = 0;

    public boolean right, left, up, down;
    private double speed = 2;
    private boolean moving = false;

    private int spriteIndex = 0, animationIndex = 0;
    private int animationSpeed = 5;

    private BufferedImage[] rightSprites;
    private BufferedImage[] leftSprites;
    private BufferedImage[] criticalStateLifeRightSprites;
    private BufferedImage[] criticalStateLiveLeftSprites;

    public Player(int x, int y, int WIDTH, int HEIGHT, BufferedImage sprite) {
        super(x, y, WIDTH, HEIGHT, sprite);

        rightSprites = new BufferedImage[3];
        leftSprites = new BufferedImage[3];
        criticalStateLifeRightSprites = new BufferedImage[3];
        criticalStateLiveLeftSprites = new BufferedImage[3];

        for (int i = 0; i <= 2; i++)
            rightSprites[i] = Game.spriteSheet.getSprite(i + 2, 0);
        for (int i = 2; i >= 0; i--)
            leftSprites[i] = Game.spriteSheet.getSprite(i + 2, 1);
        for (int i = 0; i <= 2; i++)
            criticalStateLifeRightSprites[i] = Game.spriteSheet.getSprite(i, 2);
        for (int i = 2; i >= 0; i--)
            criticalStateLiveLeftSprites[i] = Game.spriteSheet.getSprite(i, 3);
    }

    @Override
    public void update() {
        super.update();
        moving = false;

        checkLife();
        move();
        animate();
        isCollidingWithEntity();
        positionCamera();
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        drawSprite(graphics);
    }

    public void isCollidingWithEntity() {
        for (int i = 0; i < Game.entities.size(); i++) {
            if (Game.entities.get(i) == this) {
                continue;
            }
            
            if (Game.entities.get(i).mask.intersects(this.mask)) {
                if (Game.entities.get(i) instanceof WaterBottle) {
                    WaterBottle.increaseLife();
                    Game.entities.remove(i);
                } else if (Game.entities.get(i) instanceof entities.Rock) {
                    rocks++;
                    Game.entities.remove(i);
                }
            }
        }
    }

    private void drawSprite(Graphics graphics) {

        if (!criticalStateLive) {
            if (right) {
                graphics.drawImage(rightSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
            } else if (left) {
                graphics.drawImage(leftSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
            }

            if (!(right || left)) {
                graphics.drawImage(rightSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
            }
        } else {
            if (right) {
                graphics.drawImage(criticalStateLifeRightSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
            } else if (left) {
                graphics.drawImage(criticalStateLiveLeftSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
            }

            if (!(right || left)) {
                graphics.drawImage(criticalStateLifeRightSprites[spriteIndex], getX() - Camera.x, getY() - Camera.y, null);
            }
        }
    }

    private void positionCamera() {
        Camera.x = Camera.clamp((int) (this.x - Game.WIDTH / 2), 0, World.WIDTH - Game.WIDTH);
        Camera.y = Camera.clamp((int) (this.y - Game.HEIGHT / 2), 0, World.HEIGHT - Game.HEIGHT);
    }

    private void animate() {
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
    }

    private void move() {
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
    }

    private void checkLife() {
        if (life <= 0) {
            System.exit(1);
        }
        if (life < 50) {
            criticalStateLive = true;
        }else {
            criticalStateLive = false;
        }
    }
}
