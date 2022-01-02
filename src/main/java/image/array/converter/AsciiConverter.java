package image.array.converter;

import image.Grayscale;
import image.array.BlockArray;
import image.array.GrayscaleArray;

import java.awt.image.BufferedImage;

public class AsciiConverter {
    /** Corresponding char to each luminosity level. e.g: In this program, lowest (darkest) is @, while brightest is .*/
    private static final String grayscaleToChar = "@$#*!=;:~-,. ";

    /** Number of luminosity levels. */
    private static final int baseSize = grayscaleToChar.length() - 1;

    /**
     * Convert given image to its ASCII-String.
     * @param img image
     * @param lineWidth width of each line of output string. Larger lineWidth corresponds to
     * more detailed ASCII
     * @return ASCII-String
     */
    public static String convert(BufferedImage img, int lineWidth) {
        int lineCount = findLineCount(lineWidth, img);

        GrayscaleArray grayArr = Grayscale.getArray(img);
        BlockArray blockArr = grayArr.toBlockArray(lineWidth, lineCount);
        return blockArr.toString(AsciiConverter::findChar);
    }

    /**
     * Find character corresponding to average luminosity of the block
     * @param grayscaleBlock block
     * @return ASCII-char
     */
    private static char findChar(int[][] grayscaleBlock){
        int average = (int) BlockArray.getAverageValue(grayscaleBlock);
        return findChar(average);
    }

    /**
     * Find character corresponding to given luminosity
     * @param grayscale luminosity level
     * @return ASCII-char
     */
    private static char findChar(int grayscale){
        int grayscaleInAsciiBase = (int) Math.round(grayscale / 255.0 * baseSize);
        return grayscaleToChar.charAt(grayscaleInAsciiBase);
    }

    /**
     *  Calculate the number of lines needed for the ASCII-String representation
     *  e.g: 1200 x 800 image, 120 linewidth -> 80 linecount
     * @param lineWidth corresponding line width
     * @param img image to be converted
     * @return amount of lines needed
     */
    protected static int findLineCount(int lineWidth, BufferedImage img){
        double ratio = (double) img.getHeight() / img.getWidth();
        double verticalFontStretch = 2;
        return (int) (lineWidth * ratio / verticalFontStretch);
    }
}
