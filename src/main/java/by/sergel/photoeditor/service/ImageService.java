package by.sergel.photoeditor.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {
    File getFileFromClasspath(String path) throws IOException;
    String getPathFileParent(String path) throws IOException;
}
