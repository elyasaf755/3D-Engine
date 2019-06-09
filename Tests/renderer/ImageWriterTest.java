package renderer;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToimage() {
        ImageWriter imageWriter = new ImageWriter("writeToImageTest", 500, 500, 500, 500);

        for(int i = 0 ; i < imageWriter.getWidth(); ++i){
            for (int j = 0; j < imageWriter.getHeight(); ++j){
                if (i % 50 == 0 || j % 50 == 0){
                    imageWriter.writePixel(i, j, Color.WHITE);
                }
                else {
                    imageWriter.writePixel(i, j, Color.BLACK);
                }
            }
        }
        imageWriter.writeToimage();
    }
}