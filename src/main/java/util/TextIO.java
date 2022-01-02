package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextIO {

    /**
     * Append string to given file. Create file if it doesn't exist.
     * @param s the string
     * @param file the file
     */
    public static void write(String s, File file){
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))){
            file.createNewFile();
            writer.write(s);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
