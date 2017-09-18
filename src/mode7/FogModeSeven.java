package mode7;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Mode 7 - Fog Implementation
 *
 * @author VINICIUS
 */
public class FogModeSeven {

    public static final int WIDTH = 800;
    public static final int WIDTH_CENTER = WIDTH / 2;
    public static final int HEIGHT = 600;
    public static final int HEIGHT_CENTER = HEIGHT / 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Mode 7");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage texture = ImageIO.read(new File("src/resources/texture4.png"));
        int[] pixels = new int[WIDTH * HEIGHT];

        double _x, _y;
        double z = HEIGHT_CENTER * -1;
        double scaleX = 8.0;
        double scaleY = 8.0;
        double zBuffer[] = new double[WIDTH * HEIGHT];

        //Mode 7
        for (int y = 0; y < HEIGHT; y++) {

            _y = y / z;
            if (_y < 0) {
                _y *= -1;
            }
            _y *= scaleY;
            _y %= texture.getHeight();

            for (int x = 0; x < WIDTH; x++) {

                _x = (WIDTH_CENTER - x) / z;
                if (_x < 0) {
                    _x *= -1;
                }
                _x *= scaleX;
                _x %= texture.getWidth();

                //Set the value of Z buffer. Convert negative to positive
                zBuffer[x + y * WIDTH] = z < 0 ? z * -1 : z;

                pixels[x + y * WIDTH] = texture.getRGB((int) _x, (int) _y);
            }
            z++;
        }

        //Fog
        for (int i = 0; i < pixels.length; i++) {
            //Basic get RGB Algorithm
            int r = (pixels[i] >> 16) & 0xFF;
            int g = (pixels[i] >> 8) & 0xFF;
            int b = pixels[i] & 0xFF;

            double light = (5000.0 / zBuffer[i]);

            r -= light;
            g -= light;
            b -= light;

            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }

            pixels[i] = r << 16 | g << 8 | b;
        }
        
        //Render
        while (true) {
            WritableRaster raster = image.getRaster();
            raster.setDataElements(0, 0, WIDTH, HEIGHT, pixels);
            frame.getGraphics().drawImage(image, 0, 0, null);
        }
    }
}
