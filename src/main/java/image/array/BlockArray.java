package image.array;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class BlockArray {
    private final int[][][][] blocks;
    private final int height;
    private final int width;

    BlockArray(int[][][][] blocks) {
        this.blocks = blocks;
        height = blocks.length;
        width = blocks[0].length;
    }

    public GrayscaleArray toGrayscaleArray(){
        int[][] output = new int[height][width];
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                output[y][x] = (int) getAverageValue(blocks[y][x]);
            }
        }

        return new GrayscaleArray(output);
    }

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

    public static double getAverageValue(int[][] block){
        return Stream.of(block)
                .flatMapToInt(Arrays::stream)
                .average()
                .getAsDouble();
    }

}
