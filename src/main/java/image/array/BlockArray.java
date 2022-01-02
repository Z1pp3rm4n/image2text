package image.array;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A 2D array of blocks (block is a 2D array)
 */
public class BlockArray {
    private final int[][][][] blocks;
    private final int height;
    private final int width;

    /** Constructor from a 4D array */
    BlockArray(int[][][][] blocks) {
        this.blocks = blocks;
        height = blocks.length;
        width = blocks[0].length;
    }

    /**
     * Convert to a GrayScale array by averaging each block.
     * @return a GrayScaleArray
     */
    public GrayscaleArray toGrayscaleArray(){
        int[][] output = new int[height][width];
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                output[y][x] = (int) getAverageValue(blocks[y][x]);
            }
        }

        return new GrayscaleArray(output);
    }

    /**
     * Convert each block to corresponding character.
     * @param blockToChar character supplier for each block
     * @return string representation of the BlockArray
     */
    public String toString(Function<int[][], Character> blockToChar){
        StringBuilder output = new StringBuilder();

        for (int[][][] line: blocks){
            for (int[][] block: line){
                char ch = blockToChar.apply(block);
                output.append(ch);
            }
            output.append('\n');
        }

        return output.toString();
    }

    /**
     * Calculate average luminosity of a block
     * @param block the block
     * @return average luminosity
     */
    public static double getAverageValue(int[][] block){
        return Stream.of(block)
                .flatMapToInt(Arrays::stream)
                .average()
                .getAsDouble();
    }

}
