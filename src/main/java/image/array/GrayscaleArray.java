package image.array;

import java.util.function.Function;

/**
 * 2D Grayscale Array. Luminosity scale is usually either 256 or 2 (a binary array)
 */
public class GrayscaleArray {
    private final int[][] pixels;
    private final int width;
    private final int height;

    public GrayscaleArray(int[][] pixels) {
        this.pixels = pixels;
        width = pixels[0].length;
        height = pixels.length;
    }

    /**
     * Compress a grayscale array by averaging each "block"
     * @param newWidth new array width
     * @param newHeight new array height
     * @return new grayscale array
     */
    public GrayscaleArray toCompressedArray(int newWidth, int newHeight){
        BlockArray blockArr = toBlockArray(newWidth, newHeight);
        return blockArr.toGrayscaleArray();
    }

    /**
     * Convert to a BlockArray. May lose information
     * @param newWidth width of the Blockarray
     * @param newHeight heigh of the BlockArray
     * @return new BlockArray
     */
    public BlockArray toBlockArray(int newWidth, int newHeight){
        int[][][][] blocks = new int[newHeight][newWidth][][];

        int blockWidth = width / newWidth;
        int blockHeight = height / newHeight;
        for (int y = 0; y < newHeight; y++){
            for (int x = 0; x < newWidth; x++){
                blocks[y][x] = extractBlock(x * blockWidth, y * blockHeight
                            ,blockWidth, blockHeight);
            }
        }

        return new BlockArray(blocks);
    }

    /**
     * Extract a block (sub 2D array)
     * @param xOffset starting position in the x dimension
     * @param yOffset starting position in the y dimension
     * @param blockWidth block width
     * @param blockHeight block height
     * @return smaller block
     */
    private int[][] extractBlock(int xOffset, int yOffset, int blockWidth, int blockHeight){
        int[][] block = new int[blockHeight][blockWidth];
        for (int y = 0; y < blockHeight; y++){
            for (int x = 0; x < blockWidth; x++){
                block[y][x] = pixels[yOffset + y][xOffset + x];
            }
        }

        return block;
    }

    /**
     * Convert to a binary array
     * Values above the threshold gets converted to 1, otherwise to 0
     * @param threshold the threshold
     * @return a binary array
     */
    public BinaryArray toBinaryArray(int threshold){
        Function<Integer, Integer> binaryFunction = i -> (i > threshold) ? 1 : 0;
        int[][] binary = getModifiedArray(binaryFunction);

        return new BinaryArray(binary);
    }

    /**
     * Map over each array value with given function
     * @param getNewValue modifying function
     * @return new modified array
     */
    protected int[][] getModifiedArray(Function<Integer, Integer> getNewValue){
        int[][] modified = new int[height][width];

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                modified[y][x] = getNewValue.apply(pixels[y][x]);
            }
        }

        return modified;
    }
}
