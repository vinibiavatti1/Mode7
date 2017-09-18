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
public class BasicModeSevenWithComments {

    //Sizes
    public static final int WIDTH = 800;
    public static final int WIDTH_CENTER = WIDTH/2;
    public static final int HEIGHT = 600;
    public static final int HEIGHT_CENTER = HEIGHT/2;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        //Create Frame
        JFrame frame = new JFrame("Mode 7");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        //Create Buffered Images:
        //image - This is the image that will be printed in the render view
        //texture - This is the image that will be mapped to the render view
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage texture = ImageIO.read(new File("src/resources/texture.png"));
        
        //The new coords that will be used to get the pixel on the texture
        double _x, _y;
        
        //z - the incrementable variable that beggins at -300 and go to 300, because 
        //the depth will be in the center of the HEIGHT
        double z =  HEIGHT_CENTER * -1;
        
        //Scales just to control de scale of the printed pixel. It is not necessary
        double scaleX = 16.0;
        double scaleY = 16.0; 

        //Mode 7 - loop (Left Top to Down)
        for(int y = 0; y < HEIGHT; y++){
            
            _y = y / z; //The new _y coord generated
            if(_y < 0)_y *= -1; //Control the _y because the z starting with a negative number
            _y *= scaleY; //Increase the size using scale
            _y %= texture.getHeight(); //Repeat the pixel avoiding get texture out of bounds 
            
            for(int x = 0; x < WIDTH; x++){
                
                _x = (WIDTH_CENTER - x) / z; //The new _x coord generated
                if(_x < 0)_x *= -1; //Control the _x to dont be negative
                _x *= scaleX; //Increase the size using scale
                _x %= texture.getWidth(); //Repeat the pixel avoiding get texture out of bounds 
                
                //Set x,y of the view image with the _x,_y pixel in the texture
                image.setRGB(x, y, texture.getRGB((int)_x, (int)_y));
            }
            
            //Increment depth
            z++;
        }
        
        //Loop to render the generated image
        while(true){
            frame.getGraphics().drawImage(image, 0, 0, null);
        }
    }
}
