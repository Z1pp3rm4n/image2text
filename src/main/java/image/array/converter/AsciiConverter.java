package image.array.converter;

import image.Grayscale;
import image.MyImageIO;
import image.array.BlockArray;
import image.array.GrayscaleArray;

import java.awt.image.BufferedImage;

public class AsciiConverter {
    private static final String grayscaleToChar = "@$#*!=;:~-,. ";

    /** Number of luminosity levels (e.g, */
    private static final int baseSize = grayscaleToChar.length() - 1;

    public static String convert(BufferedImage img, int stringWidth) {
        int stringHeight = findStringHeight(stringWidth, img);

        GrayscaleArray grayArr = Grayscale.getArray(img);
        BlockArray blockArr = grayArr.toBlockArray(stringWidth, stringHeight);
        return blockArr.toString(AsciiConverter::findChar);
    }

    private static char findChar(int[][] grayscaleBlock){
        int average = (int) BlockArray.getAverageValue(grayscaleBlock);
        return findChar(average);
    }

    private static char findChar(int grayscale){
        int grayscaleInAsciiBase = (int) Math.round(grayscale / 255.0 * baseSize);
        return grayscaleToChar.charAt(grayscaleInAsciiBase);
    }

    /** */
    protected static int findStringHeight(int stringWidth, BufferedImage img){
        double ratio = (double) img.getHeight() / img.getWidth();
        double verticalFontStretch = 2;
        return (int) (stringWidth * ratio / verticalFontStretch);
    }
}
