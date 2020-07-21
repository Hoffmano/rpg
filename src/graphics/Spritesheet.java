package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage spriteSheet;
    private final int SPRITE_SIZE = 16;

    public SpriteSheet(String path) {
        try {
            spriteSheet = ImageIO.read(getClass().getResource(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x, int y) {
        return spriteSheet.getSubimage(x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
    }
}
