package by.sergel.photoeditor;

import by.sergel.photoeditor.exception.NotBinaryImageException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;


public class BinaryImage {
    private BufferedImage bufferedImage;
    private int[][]matrix;
    private int N;
    private int M;
    private double[][]halftoneMatrix;
    private List<Double> gradations;
    private int adding;


    public BinaryImage(File image) throws IOException, NotBinaryImageException {
        this.bufferedImage = ImageIO.read(image);
        this.matrix = getBinaryMatrix();
        this.N = matrix.length;
        this.M = matrix[0].length;
        if(!isBinary()){
            throw new NotBinaryImageException();
        }

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
        return matrix.clone();
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

    public double[][] getHalftoneMatrix(){
        if(halftoneMatrix == null || halftoneMatrix.length == 0){
            halftoneMatrix = new double[N][M];
            for(int i = 0; i < N; i++){
                for(int j = 0; j < M; j++){
                    halftoneMatrix[i][j] = getMinDistance(matrix, i, j);
                }
            }
        }
        return halftoneMatrix.clone();
    }

    private double getMinDistance(int[][] matrix, int x, int y){
        double ret = Double.MAX_VALUE;
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
        BufferedImage bufferedImage = new BufferedImage(N, M, BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){

                int colorValue = gradations.indexOf(halftoneMatrix[i][j]) * adding;
                bufferedImage.setRGB(i, j, new Color(colorValue, colorValue, colorValue).getRGB());
            }
        }
        return bufferedImage;
    }

    public boolean isBinary(){
        Set<Integer> set = new HashSet<>();
        for(int[] i : matrix) {
            for (int j : i) {
                set.add(j);
            }
        }
        set.forEach(el -> System.out.println("el: " + el));
        System.out.println("set size: " + set.size());
        return (set.size() > 0) && (set.size() <= 2);
    }

    public void initGradations(){
        Set<Double> set = new TreeSet<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o1 < o2 ? 1 : -1;
            }
        });
        for(double[] i : halftoneMatrix){
            for(double j : i){
                set.add(j);
            }
        }
        gradations = new ArrayList<>(set);

        adding = 256 / (gradations.size() - 1);
    }
}
