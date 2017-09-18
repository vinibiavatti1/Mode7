package mode7;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Mode 7 - Angled Movement Implementation
 *
 * @author VINICIUS
 */
public class AngleMovedModeSeven implements KeyListener {

    public static final int WIDTH = 800;
    public static final int WIDTH_CENTER = WIDTH / 2;
    public static final int HEIGHT = 600;
    public static final int HEIGHT_CENTER = HEIGHT / 2;

    //Movement
    private double angle = 0;
    private double moveX = 0;
    private double moveY = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new AngleMovedModeSeven().render();
    }

    /**
     * Render Mode 7
     */
    public void render() throws IOException {
        //Frame
        JFrame frame = new JFrame("Mode 7");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(this);
        frame.setVisible(true);

        //Create buffered images
        //image - This is the image that will be printed in the render view
        //texture - This is the image that will be mapped to the render view
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage texture = ImageIO.read(new File("src/resources/texture4.png"));
        int[] pixels = new int[WIDTH * HEIGHT];

        //Mode 7 - Loop
        while (true) {

            //Coords
            double _x;
            double _y;
            double z = HEIGHT_CENTER * -1;
            double scaleX = 4.0;
            double scaleY = 4.0;

            //Sin / Cos of the Angle
            double cos = Math.cos(Math.toRadians(angle));
            double sin = Math.sin(Math.toRadians(angle));

            for (int y = 0; y < HEIGHT; y++) {

                for (int x = 0; x < WIDTH; x++) {

                    //Generate new x,y postion
                    _y = (((WIDTH - x) * cos - (x) * sin)) / z;
                    _x = (((WIDTH - x) * sin + (x) * cos)) / z;

                    if (z < 0) {
                        _x -= moveX;
                        _y -= moveY;
                    } else {
                        _x += moveX;
                        _y += moveY;
                    }

                    //Always positive
                    if (_y < 0) {
                        _y *= -1;
                    }
                    if (_x < 0) {
                        _x *= -1;
                    }

                    //Increase/Decrease the size
                    _y *= scaleY;
                    _x *= scaleX;

                    //Repeat the pixel on the texture
                    _x %= texture.getWidth();
                    _y %= texture.getHeight();

                    //Se the position of the pixel array with the pixel of the new coords _x and _y
                    pixels[x + y * WIDTH] = texture.getRGB((int) _x, (int) _y);
                }
                z++;
            }

            //Draw pixels
            WritableRaster raster = image.getRaster();
            raster.setDataElements(0, 0, WIDTH, HEIGHT, pixels);
            frame.getGraphics().drawImage(image, 0, 0, null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            angle--;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            angle++;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            moveX += Math.sin(Math.toRadians(angle+45))*0.1;
            moveY += Math.cos(Math.toRadians(angle+45))*0.1;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            moveX -= Math.sin(Math.toRadians(angle+45))*0.1;
            moveY -= Math.cos(Math.toRadians(angle+45))*0.1;
        }
        if (angle > 360) {
            angle = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
