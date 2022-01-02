import image.MyImageIO;
import image.array.converter.AsciiConverter;
import image.array.converter.BrailleConverter;
import picocli.CommandLine;
import picocli.CommandLine.*;
import util.TextIO;

import java.awt.image.BufferedImage;
import java.io.File;

@Command(name = "Image2Text", version = "Image2Text 1.0", mixinStandardHelpOptions = true
    ,subcommands = {ConvertToAscii.class
                    ,ConvertToBraille.class
                    ,CommandLine.HelpCommand.class}
    ,description = "Convert a PNG/JPG/BMP image to either ascii/braille")
public class Image2Text {

    public static void main(String[] args){
        int exitCode = new CommandLine(new Image2Text()).execute(args);
        System.exit(exitCode);
    }
}

@Command(name = "ascii", description = "Convert the image to ascii characters")
class ConvertToAscii implements Runnable{
    @Parameters(description = "image file")
    File imageFile;

    @Option(names = {"-w", "--string-width"}, description = "width of output string")
    int stringWidth = 30;

    @Option(names = {"-o", "--output-file"}, description = "a file to print the result to")
    File output;

    @Override
    public void run() {
        BufferedImage img = MyImageIO.read(imageFile);
        String result = AsciiConverter.convert(img, stringWidth);

        System.out.println(result);
        if (output != null) { TextIO.write(result, output);}
    }


}

@Command(name = "braille", description = "Convert the image to braille characters")
class ConvertToBraille implements Runnable{
    @Parameters(description = "input image")
    File imageFile;

    @Option(names = {"-w", "--string-width"}, description = "width of output string")
    int stringWidth = 30;

    @Option(names = {"-i", "--invert"}, description = "invert color of braille string")
    boolean invert;

    @Option(names = {"-f", "--fill-gaps"}, description = "fill blank braille with 1-dot braille")
    boolean fillGaps;

    @Option(names = {"-t", "--threshold"}, description = "an 8-bit value (0-255) for thresholding image to binary")
    int threshold = 127;

    @Option(names = {"-o", "--output-file"}, description = "a file to print the result to")
    File output;

    @Override
    public void run() {
        BufferedImage img = MyImageIO.read(imageFile);
        String result = BrailleConverter.convert(img, stringWidth, threshold, invert, fillGaps);

        System.out.println(result);
        if (output != null) { TextIO.write(result, output);}
    }
}

