package image;

public class Rgb {
    /** Returns the luminosity corresponding to a rgb value in hex.
    *   Account for gamma-correction.
    */
    public static double getLuminosityGamma(int rgb){
        int red   = (rgb >> 16) & 0xff;
        int green = (rgb >> 8) & 0xff;
        int blue  = rgb & 0xff;

        double redEx = gammaExpand(red / 255.0);
        double greenEx = gammaExpand(green / 255.0);
        double blueEx = gammaExpand(blue / 255.0);

        double linearLum = 0.2126 * redEx + 0.7152 * greenEx + 0.0722 * blueEx;

        double result = gammaCompress(linearLum) * 255.0;
        if (result > 255.0) System.out.println(result);
        return result;
    }

    /** Returns the luminosity corresponding to a rgb value in hex.
     *  Uses a weighted linear function.
     *  (weight specified in https://en.wikipedia.org/wiki/Relative_luminance)
     */
    public static double getLuminosityLinear(int rgb){
        int red = (rgb >> 16) & 0xff;
        int green  = (rgb >> 8) & 0xff;
        int blue  = rgb & 0xff;

        return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
    }

    /** Returns the luminosity corresponding to a rgb value in hex.
     *  Uses the average value of the 3 colors.
     */
    public static double getLuminosityUnweighted(int rgb){
        int red = (rgb >> 16) & 0xff;
        int green  = (rgb >> 8) & 0xff;
        int blue  = rgb & 0xff;

        return (red + green + blue) / 3.0;
    }

    /** Returns the hex value of the color gray corresponding to a certain luminosity & alpha value */
    public static int getGray(int lum, int alpha){
        return (alpha << 24) | (lum << 16) | (lum << 8) | lum;
    }

    /** Returns the gamma-compressed value of a color value in range [0,1]
     * https://en.wikipedia.org/wiki/SRGB#Theory_of_the_transformation
     */
    private static double gammaCompress(double colorValue){
        if (colorValue <= 0.0031308) {
            return 12.92 * colorValue;
        }

        return 1.055 * Math.pow(colorValue, 0.41666) - 0.055;
    }

    /** Returns the gamma-expanded value of a color value in range [0,1]
     * https://en.wikipedia.org/wiki/SRGB#Theory_of_the_transformation
     */
    private static double gammaExpand(double colorValue){
        if (colorValue <= 0.04045) {
            return colorValue / 12.92;  // https://en.wikipedia.org/wiki/SRGB
        }

        return Math.pow((colorValue + 0.055)/1.055, 2.4);
    }
}
