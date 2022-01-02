package image.array;

import java.util.function.Function;

public class BinaryArray extends GrayscaleArray {
    BinaryArray(int[][] binaryValues) {
        super(binaryValues);
    }

    public BinaryArray getInvertArray(){
        Function<Integer, Integer> invert = i -> 1 - i;
        int[][] inverted = getModifiedArray(invert);
        return new BinaryArray(inverted);
    }
}
