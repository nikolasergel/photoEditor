package by.sergel.photoeditor.controllers;

import by.sergel.photoeditor.ImageResizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Value("${default.image.path}")
    private String defaultImagePath;

    @GetMapping("/")
    public String getIndex(){
        try{
            ImageResizer.resizeAndSaveImage(defaultImagePath, 200, 200, "bmp");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "index";
    }

    @PostMapping("/")
    public String testResize(@RequestParam int width, @RequestParam int height){
        try{
            ImageResizer.resizeAndSaveImage(defaultImagePath, width, height, "bmp");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "index";
    }
}
