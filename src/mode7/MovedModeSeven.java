package mode7;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Mode 7 - Basic Implementation
 *
 * @author VINICIUS
 */
public class MovedModeSeven implements KeyListener {

    public static final int WIDTH = 800;
    public static final int WIDTH_CENTER = WIDTH / 2;
    public static final int HEIGHT = 600;
    public static final int HEIGHT_CENTER = HEIGHT / 2;

    private double xCan = 0.0;
    private double yCan = 0.0;
    private double zCan = 0.0;
    private double velocity = 0.05;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new MovedModeSeven().render();
    }

    public void render() throws IOException {
        JFrame frame = new JFrame("Mode 7");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(this);
        frame.setVisible(true);

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage texture = ImageIO.read(new File("src/resources/texture.png"));

        while (true) {
            double _x = 0.0;
            double _y = 0.0;
            double z = HEIGHT_CENTER * -1;
            double scaleX = 16.0;
            double scaleY = 16.0;
            z += zCan;
            for (int y = 0; y < HEIGHT; y++) {
                
                _y = y / z;

                if (_y < 0) {
                    _y += yCan;
                    _y *= -1;
                } else {
                    _y -= yCan;
                }
                
                if (_y < 0) {_y *= -1;}
                
                _y *= scaleY;
                _y %= texture.getHeight();

                for (int x = 0; x < WIDTH; x++) {
                    
                    _x = (WIDTH_CENTER - x) / z;
                    
                    if(z < 0){
                        _x += xCan;
                    } else {
                        _x -= xCan;
                    }
                    
                    if (_x < 0) {
                        _x *= -1;
                    }

                    _x *= scaleX;
                    _x %= texture.getWidth();
                    
                    image.setRGB(x, y, texture.getRGB((int) _x, (int) _y));
                }
                z++;
            }
            frame.getGraphics().drawImage(image, 0, 0, null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {xCan-=velocity;}
        if (e.getKeyCode() == KeyEvent.VK_D) {xCan+=velocity;}
        if (e.getKeyCode() == KeyEvent.VK_W) {yCan-=velocity;}
        if (e.getKeyCode() == KeyEvent.VK_S) {yCan+=velocity;}
        if (e.getKeyCode() == KeyEvent.VK_Q) {zCan+=velocity*100;}
        if (e.getKeyCode() == KeyEvent.VK_E) {zCan-=velocity*100;}
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
