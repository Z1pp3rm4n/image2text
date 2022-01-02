package image.array;

import java.util.function.Function;

public class GrayscaleArray {
    private final int[][] pixels;
    private final int width;
    private final int height;

    public GrayscaleArray(int[][] pixels) {
        this.pixels = pixels;
        width = pixels[0].length;
        height = pixels.length;
    }

    public GrayscaleArray toCompressedArray(int newWidth, int newHeight){
        BlockArray blockArr = toBlockArray(newWidth, newHeight);
        return blockArr.toGrayscaleArray();
    }

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

    private int[][] extractBlock(int xOrigin, int yOrigin, int blockWidth, int blockHeight){
        int[][] block = new int[blockHeight][blockWidth];
        for (int y = 0; y < blockHeight; y++){
            for (int x = 0; x < blockWidth; x++){
                block[y][x] = pixels[yOrigin + y][xOrigin + x];
            }
        }

        return block;
    }

    public BinaryArray toBinaryArray(int threshold){
        Function<Integer, Integer> binaryFunction = i -> (i > threshold) ? 1 : 0;
        int[][] binary = getModifiedArray(binaryFunction);

        return new BinaryArray(binary);
    }

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
