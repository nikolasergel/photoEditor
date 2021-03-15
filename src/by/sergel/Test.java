package by.sergel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;


public class Test {
    private BufferedImage bufferedImage;
    private String imagePath;

    public Test(String imagePath) throws IOException {
        this.bufferedImage = ImageIO.read(new File(imagePath));
        this.imagePath = imagePath;
    }

    public void getMatrix() {
        Rectangle rectangle = new Rectangle(bufferedImage.getWidth(), bufferedImage.getHeight());
        byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        for (byte b : pixels) {
            System.out.println(b);
        }
    }

    public void test() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        byte[] imageInByte = baos.toByteArray();

        for (int i = 0; i < imageInByte.length; i++) {
            System.out.println(imageInByte[i]);
        }
    }

    public void writeMatrix2() throws IOException {
        FileWriter writer = new FileWriter("pixel_values.txt");

        BufferedImage img = bufferedImage;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x, y);

                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                writer.append(red + ":");
                writer.append(green + ":");
                writer.append(blue + "");
                writer.append("\n");
                writer.flush();
            }
        }
    }

    public String[][] getMatrix2String() throws IOException {
//        FileWriter writer = new FileWriter("pixel_values.txt");
        BufferedImage img = bufferedImage;
        String[][] bytes = new String[img.getWidth()][img.getHeight()];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x, y);

                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                if(color.getBlue() == color.getGreen() && color.getBlue() == color.getRed() && color.getBlue() == 255){
                    bytes[x][y] = "0";
                }
                if(color.getBlue() == color.getGreen() && color.getBlue() == color.getRed() && color.getBlue() == 0){
                    bytes[x][y] = "1";
                }
//                int red = color.getRed();
//                int green = color.getGreen();
//                int blue = color.getBlue();
            }
        }
        return bytes;
    }

    public void test2() throws IOException {
        System.out.println(bufferedImage.getRaster().getDataBuffer());


    }

    public void editMatrix3() throws IOException {
        BufferedImage im = ImageIO.read(new File(imagePath));

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 7; j++) {
                im.setRGB(i, j, Color.WHITE.getRGB());
            }
        }


        try {
            ImageIO.write(im, "bmp", new File("fixed.bmp"));
        } catch (IOException e) {
            System.out.println("Some exception occured " + e);
        }

    }
}
