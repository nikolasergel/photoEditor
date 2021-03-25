package by.sergel.photoeditor.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService{
    @Override
    public File getFileFromClasspath(String path) throws IOException {
        File file = new ClassPathResource(path).getFile();
        if (!file.isFile()){
            throw new FileNotFoundException();
        }
        return file;
    }

    @Override
    public String getPathFileParent(String path) throws IOException{
        return getFileFromClasspath(path).getParent();
    }
}
