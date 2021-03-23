package by.sergel.photoeditor;

import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryImage {
    private BufferedImage bufferedImage;
    private int[][]matrix;
    private int N;
    private int M;
    private int[][]halftoneMatrix;
    private List<Integer> gradations;
    private int adding;


    public BinaryImage(String imagePath) throws IOException {
        this.bufferedImage = ImageIO.read(new File(imagePath));
        this.matrix = getBinaryMatrix();
        this.N = matrix.length;
        this.M = matrix[0].length;
    }

    public int[][] getMatrix(){
        return matrix;
    }

    private int[][] getBinaryMatrix() throws IOException {
        BufferedImage img = bufferedImage;
        int[][] matrix = new int[img.getWidth()][img.getHeight()];
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x, y);

                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                if(color.getBlue() == color.getGreen() && color.getBlue() == color.getRed() && color.getBlue() == 255){
                    matrix[x][y] = 0;
                }
                if(color.getBlue() == color.getGreen() && color.getBlue() == color.getRed() && color.getBlue() == 0){
                    matrix[x][y] = 1;
                }
            }
        }
        return matrix;
    }

    protected void testEditMatrix(String path) throws IOException {
        BufferedImage im = ImageIO.read(new File(path));

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                im.setRGB(i, j, Color.WHITE.getRGB());
            }
        }
        try {
            ImageIO.write(im, "bmp", new File("fixed.bmp"));
        } catch (IOException e) {
            System.out.println("Some exception occured " + e);
        }
    }

    public int[][] getHalftoneMatrix(){
        if(halftoneMatrix == null){
            halftoneMatrix = new int[N][M];
            int[][] ret = new int[N][M];
            for(int i = 0; i < N; i++){
                for(int j = 0; j < M; j++){
                    ret[i][j] = getMinDistance(matrix, i, j);
                }
            }
        }
        return halftoneMatrix;
    }

    private int getMinDistance(int[][] matrix, int x, int y){
        int ret = Integer.MAX_VALUE;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(matrix[x][y] != matrix[i][j]){
                    ret = Math.min(ret, Math.abs(i - x) + Math.abs(j - y));
                }
            }
        }
        if(matrix[x][y] == 0){
            ret *= -1;
        }
        return ret;
    }

    public BufferedImage getHalftoneImage(){
        getHalftoneMatrix();
        initGradations();
        BufferedImage bufferedImage = new BufferedImage(N, M, BufferedImage.TYPE_USHORT_GRAY);
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                int colorValue = gradations.indexOf(halftoneMatrix[i][j]) * adding;
                Color color = new Color(colorValue, colorValue, colorValue);
                bufferedImage.setRGB(i, j, color.getRGB());
            }
        }
        return bufferedImage;
    }

    private void initGradations(){
        Set<Integer> set = new TreeSet<>();
        for(int[] i : halftoneMatrix){
            for(int j : i){
                gradations.add(j);
            }
        }
        gradations = new ArrayList<>(set);
        adding = 256 / (gradations.size() + 1);
    }
}
