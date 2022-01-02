package image;

import image.array.GrayscaleArray;

import java.awt.image.BufferedImage;
import java.util.function.Function;

public class Grayscale {
    /**
     * Returns a grayscale image of the input image.
     * @param img               The color image.
     * @param imageOutputType   Type of the output buffered image.
     * @param rgbToLuminosity   The function to transform an rgb value in hex to its luminosity (grayscale).
     *                          in range 0-255.0.
     * @return                  The output grayscale image.
     */
    public static BufferedImage getImage(BufferedImage img, int imageOutputType, Function<Integer, Double> rgbToLuminosity){
        BufferedImage grayImg = new BufferedImage(img.getWidth(), img.getHeight(), imageOutputType);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int lum = rgbToLuminosity.apply(rgb).intValue();

                int gray = Rgb.getGray(lum, alpha);
                grayImg.setRGB(x, y, gray);
            }
        }

        return grayImg;
    }

    /** Returns an 8-bit grayscale array of the image. */
    public static GrayscaleArray getArray(BufferedImage img){
        int[][] grayscaleArr = new int[img.getHeight()][img.getWidth()];

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int lum = (int) Rgb.getLuminosityLinear(rgb);

                grayscaleArr[y][x] = lum;
            }
        }

        return new GrayscaleArray(grayscaleArr);
    }
}
