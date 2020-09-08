package cn.sh.cares.serialport.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

public class FileUtils {

    public static void writeFile(String data) {

        LocalDate localDate = LocalDate.now();

        Path path = Paths.get(".", localDate.toString());
        StandardOpenOption standardOpenOption;
        if (path.toFile().exists()) {
            standardOpenOption = StandardOpenOption.APPEND;
        } else {
            standardOpenOption = StandardOpenOption.CREATE;
        }
        try {
            Files.write(path,data.getBytes(StandardCharsets.UTF_8), standardOpenOption);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
