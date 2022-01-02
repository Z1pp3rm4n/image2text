package image.array;

import java.util.function.Function;

/**
 * 2D luminosity array. Each luminosity value is represented in 0 or 1.
 */
public class BinaryArray extends GrayscaleArray {
    /** Constructor */
    BinaryArray(int[][] binaryValues) {
        super(binaryValues);
    }

    /** Invert the array */
    public BinaryArray getInvertArray(){
        Function<Integer, Integer> invert = i -> 1 - i;
        int[][] inverted = getModifiedArray(invert);
        return new BinaryArray(inverted);
    }
}
