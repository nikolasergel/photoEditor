package by.sergel.photoeditor.controllers;

import by.sergel.photoeditor.BinaryImage;
import by.sergel.photoeditor.ImageResizer;
import by.sergel.photoeditor.exception.NotBinaryImageException;
import by.sergel.photoeditor.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MainController {
    @Autowired
    private ImageService imageService;
    @Value("${default.image.path}")
    private String defaultImagePath;
    @Value("${default.process.image.path}")
    private String processImagePath;

    @GetMapping("/")
    public String getIndex(){
        try{
            File file = imageService.getFileFromClasspath(defaultImagePath);
            ImageResizer.resizeAndSaveImage(file, 200, 200, "bmp");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "index";
    }

    @PostMapping("/")
    public String testResize(@RequestParam int width, @RequestParam int height){
        try{
            File file = imageService.getFileFromClasspath(defaultImagePath);
            ImageResizer.resizeAndSaveImage(file, width, height, "bmp");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/process")
    public String getHalftone(Model model){
        try{
            File displayImage = imageService.getFileFromClasspath(defaultImagePath);
            File processImage = imageService.getFileFromClasspath(defaultImagePath);
            BinaryImage binaryImage = new BinaryImage(processImage);
            BufferedImage bufferedImage = binaryImage.getHalftoneImage();
            model.addAttribute("binaryMatrix", binaryImage.getMatrix());
            model.addAttribute("halftoneMatrix", binaryImage.getHalftoneMatrix());
            ImageIO.write(bufferedImage, "bmp", processImage);
            bufferedImage = ImageResizer.resize(bufferedImage, 200, 200);
            ImageIO.write(bufferedImage, "bmp", displayImage);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NotBinaryImageException e){
            System.out.println("NotBinaryException");
        }
        return "index";
    }
}
