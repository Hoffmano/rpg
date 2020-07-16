package main;

import entities.Entity;
import entities.Player;
import graphics.Spritesheet;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDTH = 256;
    public static int HEIGHT = 144;
    public static int SCALE = 4;
    public static Spritesheet spritesheet;
    public static List<Entity> entities;
    private static BufferedImage image;
    private static boolean isRunning = false;
    private static Thread thread;
    private static World world;
    public static Player player;

    public Game() {
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        createFrame();

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        spritesheet = new Spritesheet("/spritesheet.png");



        entities = new ArrayList<>();
        player = new Player(new Random().nextInt(WIDTH - 16), new Random().nextInt(HEIGHT - 16), 16, 16, spritesheet.getSprite(2, 0));
        entities.add(player);

        world = new World("/map.png");
    }

    private void createFrame() {
        JFrame frame = new JFrame("RPG");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();

        game.start();
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
        float fps = 30;
        double nanoSecondsPerFrame = 1000000000 / fps;
        double deltaTime = 0;
        double initialTimeMillis = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
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
            deltaTime = 0;
        }
        return deltaTime;
    }

    private double printFps(double timer) {
        if (System.currentTimeMillis() - timer >= 1000) {
            timer += 1000;
        }
        return timer;
    }

    private void update() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
    }

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.darkGray);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        Graphics2D graphics2D = (Graphics2D) graphics;

        world.render(graphics);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(graphics);
        }

        graphics.dispose();
        graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
    public void keyReleased(KeyEvent e) {
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
}
