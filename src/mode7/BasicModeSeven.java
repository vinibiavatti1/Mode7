package mode7;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Mode 7 - Basic Implementation
 * This code will map a texture to create a pseudo-3d perspective.
 * This is an infinite render mode. The texture will be repeated without bounds.
 * @author VINICIUS
 */
public class BasicModeSeven {

    
    public static final int WIDTH = 800;
    public static final int WIDTH_CENTER = WIDTH/2;
    public static final int HEIGHT = 600;
    public static final int HEIGHT_CENTER = HEIGHT/2;
    
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
        BufferedImage texture = ImageIO.read(new File("src/resources/texture.png"));
        
        double _x, _y;
        double z =  HEIGHT_CENTER * -1;
        double scaleX = 16.0;
        double scaleY = 16.0; 

        //Mode 7
        for(int y = 0; y < HEIGHT; y++){
            
            _y = y / z; 
            if(_y < 0)_y *= -1; 
            _y *= scaleY; 
            _y %= texture.getHeight(); 
            
            for(int x = 0; x < WIDTH; x++){
                
                _x = (WIDTH_CENTER - x) / z; 
                if(_x < 0)_x *= -1; 
                _x *= scaleX; 
                _x %= texture.getWidth(); 

                image.setRGB(x, y, texture.getRGB((int)_x, (int)_y));
            }
            z++;
        }

        //Render
        while(true){
            frame.getGraphics().drawImage(image, 0, 0, null);
        }
    }
}
