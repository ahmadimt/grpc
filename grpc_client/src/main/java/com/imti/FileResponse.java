package com.imti;

import com.google.protobuf.TextFormat;
import com.google.protobuf.TextFormat.ParseException;
import gnmi.Gnmi.SubscribeRequest;
import gnmi.Gnmi.Update;
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

  public static Update from(String filePath) {
    Update.Builder builder = Update.newBuilder();
    try {
      TextFormat.merge(readContentFromFile(filePath), builder);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return builder.build();
  }
}
