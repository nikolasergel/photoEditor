package by.sergel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Test1 extends JPanel {

    public void paint(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\nikol\\Desktop\\Федосова\\Bit.bmp"));
            image = ImageResizer.resize(image, 200, 200);

        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image, 20,10,this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Test test = null;
        try {
            test = new Test("C:\\Users\\nikol\\Desktop\\Федосова\\Bit.bmp");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JTable table = null;
        try {
            String[] strings = new String[test.getMatrix2String().length];
            Arrays.fill(strings,"LOL");
            table = new JTable(test.getMatrix2String(), strings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel panel = new Test1();
        panel.add(new JScrollPane(table));
        frame.getContentPane().add(panel);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}