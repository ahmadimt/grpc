package com.imti;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

public class FileResponse {

  public static String readContentFromFile(String filePath) {
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    File file = new File(classLoader.getResource(filePath).getFile());
    StringBuilder contentBuilder = new StringBuilder();
    try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
      stream.forEach(s -> contentBuilder.append(s).append("\n"));
    } catch (IOException e) {
    }
    return contentBuilder.toString();
  }
}
