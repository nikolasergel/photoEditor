package by.sergel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageResizer {
    public static BufferedImage resize(BufferedImage inputImage, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
//        String formatName = outputImagePath.substring(outputImagePath
//                .lastIndexOf(".") + 1);

        // writes to output file
//        ImageIO.write(outputImage, formatName, new File(outputImagePath));
        return outputImage;
    }
}
