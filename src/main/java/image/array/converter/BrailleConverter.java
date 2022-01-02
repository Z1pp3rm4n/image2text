package image.array.converter;

import image.Grayscale;
import image.MyImageIO;
import image.array.BinaryArray;
import image.array.BlockArray;
import image.array.GrayscaleArray;

import java.awt.image.BufferedImage;

public class BrailleConverter {
    private static final int UNICODE_OFFSET = 10240;
    private static final int[][] binaryWeight = {{0, 3}
                                                ,{1, 4}
                                                ,{2, 5}
                                                ,{6, 7}};

    public static void main (String[] args){
        BufferedImage img = MyImageIO.read("stuff.png");
        System.out.println(convert(img, 50, 128, false , true));
    }
    /**
     * @param img
     * @param invert   Whether or not to invert the binary image
     * @param fillGaps Whether or not to replace empty braille characters '⠀' with a single braille value '⠄', used for
     *                 equal width in case that the target application does not support it
     * @return A 2D pixel array of braille unicode
     */
    public static String convert(BufferedImage img, int stringWidth, int threshold, boolean invert, boolean fillGaps) {
        int stringHeight = AsciiConverter.findStringHeight(stringWidth, img);
        GrayscaleArray grayscaleArr = Grayscale.getArray(img);

        GrayscaleArray compressed = grayscaleArr.toCompressedArray(2 * stringWidth, 4 * stringHeight);
        BinaryArray binaryArr = compressed.toBinaryArray(threshold);
        if (invert) {binaryArr = binaryArr.getInvertArray();}

        BlockArray brailleBlock = binaryArr.toBlockArray(stringWidth, stringHeight);
        String output = brailleBlock.toString(BrailleConverter::blockToBraille);
        if (fillGaps) {output = output.replaceAll("⠀", "⠄");}

        return output;
    }


    // Converts a 2 by 4 int array of 1s and 0s to a braille unicode char
    private static char blockToBraille(int[][] block) {
        int unicodeValue = UNICODE_OFFSET + blockToBrailleValue(block);
        return Character.toChars(unicodeValue)[0];
    }

    private static int blockToBrailleValue(int[][] block){
        int value = 0;
        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 2; x++){
                value += block[y][x] << binaryWeight[y][x];
            }
        }

        return value;
    }

}
