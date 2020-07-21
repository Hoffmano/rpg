package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import graphics.SpriteSheet;
import graphics.UI;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;
    
    public static final int WIDTH = 256;
    public static final int HEIGHT = 144;
    public static final int SCALE = 4;

    
    private static boolean isRunning = false;
    private static Thread thread;
    private static int framesPerSecond;
    
    public static SpriteSheet spriteSheet;
    private static BufferedImage image;
    
    private static World world;
    public static Player player;
    public static UI ui;

    public static List<Entity> entities;
    public static List<Enemy> enemies;
    
    public static Random random;
    public static void main(final String[] args) {
        Game game = new Game();
        game.start();
    }

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        createFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spriteSheet = new SpriteSheet("/spriteSheet.png");
        
        addKeyListener(this);
        random = new Random();

        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        
        player = new Player(new Random().nextInt(WIDTH - 16), new Random().nextInt(HEIGHT - 16), 16, 16, spriteSheet.getSprite(2, 0));
        entities.add(player);
        
        world = new World("/map.png");
        
        ui = new UI();
    }

    private void createFrame() {
        final JFrame frame = new JFrame("RPG");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    @Override
    public void run() {
        requestFocus();
        long lastTime = System.nanoTime();
        final float fps = 30;
        final double nanoSecondsPerFrame = 1000000000 / fps;
        double deltaTime = 0;
        double initialTimeMillis = System.currentTimeMillis();

        while (isRunning) {
            final long now = System.nanoTime();
            deltaTime += (now - lastTime) / nanoSecondsPerFrame;
            lastTime = now;
            deltaTime = executeNextFrame(deltaTime);
            initialTimeMillis = printFps(initialTimeMillis);
        }
    }

    private double executeNextFrame(double deltaTime) {
        if (deltaTime >= 1) {
            update();
            render();
            framesPerSecond++;
            deltaTime = 0;
        }

        return deltaTime;
    }

    private double printFps(double timer) {
        if (System.currentTimeMillis() - timer >= 1000) {
            // System.out.println(framesPerSecond);
            framesPerSecond = 0;
            timer += 1000;
        }

        return timer;
    }

    private void update() {
        updateEntities();
    }
    
    private void render() {
        final BufferStrategy bufferStrategy = this.getBufferStrategy();

        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = image.getGraphics();

        world.render(graphics);
        renderEntities(graphics);
        ui.render(graphics);

        graphics.dispose();
        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bufferStrategy.show();
    }

    private void updateEntities() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
    }

    private void renderEntities(Graphics graphics) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(graphics);
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        }
    }

	@Override
	public void keyTyped(KeyEvent e) {		
	}
}
