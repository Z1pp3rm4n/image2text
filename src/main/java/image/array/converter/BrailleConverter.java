package image.array.converter;

import image.Grayscale;
import image.MyImageIO;
import image.array.BinaryArray;
import image.array.BlockArray;
import image.array.GrayscaleArray;

import java.awt.image.BufferedImage;

public class BrailleConverter {
    /** Offset to first Unicode-Braille character*/
    private static final int UNICODE_OFFSET = 10240;

    /** Used to calculate Braille binary value */
    private static final int[][] binaryWeight = {{0, 3}
                                                ,{1, 4}
                                                ,{2, 5}
                                                ,{6, 7}};

    /**
     * Convert given image to a Braille-String
     * @param img image to be converted
     * @param lineWidth line width of the new string
     * @param threshold threshold to convert to BinaryArray
     * @param invert Whether or not to invert the binary image
     * @param fillGaps Whether or not to replace empty braille characters '⠀' with a single braille value '⠄'
     used to conserve line width in case the target application does not support blank braille.
     * @return Braille-String
     */
    public static String convert(BufferedImage img, int lineWidth, int threshold, boolean invert, boolean fillGaps) {
        // Convert to GrayScaleArray
        int lineCount = AsciiConverter.findLineCount(lineWidth, img);
        GrayscaleArray grayscaleArr = Grayscale.getArray(img);

        // Compress and convert to BinaryArray
        GrayscaleArray compressed = grayscaleArr.toCompressedArray(2 * lineWidth, 4 * lineCount);
        BinaryArray binaryArr = compressed.toBinaryArray(threshold);
        if (invert) {binaryArr = binaryArr.getInvertArray();}

        // Convert to an array of braille blocks and then to output string
        BlockArray brailleBlockArray = binaryArr.toBlockArray(lineWidth, lineCount);
        String output = brailleBlockArray.toString(BrailleConverter::blockToBraille);
        if (fillGaps) {output = output.replaceAll("⠀", "⠄");}

        return output;
    }


    /** Converts a 2 by 4 int array of 1s and 0s to a braille unicode char */
    private static char blockToBraille(int[][] block) {
        int unicodeValue = UNICODE_OFFSET + blockToBrailleValue(block);
        return Character.toChars(unicodeValue)[0];
    }

    /** Convert a 2 by 4 int array of 1s and 0s to its Braille binary value
     *  by shifting each cell by its binaryWeight
     */
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
