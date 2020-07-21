package graphics;

import java.awt.Color;
import java.awt.Graphics;

import entities.Player;

public class UI {
    public void render(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.fillRect(5, 5, 100, 10);
        graphics.setColor(Color.green);
        graphics.fillRect(5, 5, (int) ((Player.life / 100.0) * 100), 10);
        graphics.setColor(Color.white);
        graphics.drawString("Rocks: " + Player.rocks,200,15);
    }
}