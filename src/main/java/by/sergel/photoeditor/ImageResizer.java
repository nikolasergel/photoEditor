package by.sergel.photoeditor;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageResizer {
    private static Path absolutePath;

    public static File getFile(String path) throws IOException {
        File file = new ClassPathResource(path).getFile();
        if (!file.isFile()){
            throw new FileNotFoundException();
        }
        return file;
    }

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

    public static void saveImage(BufferedImage image, Path path) throws IOException {
        ImageIO.write(image, "bmp", path.toFile());
    }

    public static void resizeAndSaveImage(File file, int scaledWidth, int scaledHeight, String imageType) throws IOException {
//        Path path = Path.of(file.getParent());
        BufferedImage bufferedImage = ImageIO.read(file);
        System.out.println(bufferedImage.getType());
        bufferedImage = resize(bufferedImage, scaledWidth, scaledHeight);
        ImageIO.write(bufferedImage, imageType, file);
    }

    public static void resizeAndSaveImage(BufferedImage bufferedImage, String path , int scaledWidth,
                                          int scaledHeight, String imageType) throws IOException {

        bufferedImage = resize(bufferedImage, scaledWidth, scaledHeight);
        ImageIO.write(bufferedImage, imageType, new File(path));
    }
}
