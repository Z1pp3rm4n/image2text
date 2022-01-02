package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class MyImageIO {
    /** Read the image from given file */
    public static BufferedImage read(File file){
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e){
            e.printStackTrace();
        }

        return img;
    }

    /** Reads the image from corresponding pathname */
    public static BufferedImage read(String pathname){
        File file = new File(pathname);
        return read(file);
    }

    /** Writes the image to any pathname (creates file if file doesn't exist) */
    public static void write(RenderedImage img, String format, String pathname) {
        File file = new File(pathname);

        try {
            file.createNewFile();   //
            ImageIO.write(img, format, file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
