package cn.sh.cares.serialport.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FileUtils {

    public static void writeFile(String data) {

        LocalDate localDate = LocalDate.now();

        Path path = Paths.get(".", localDate.toString(),Long.valueOf(System.currentTimeMillis()).toString());
        StandardOpenOption standardOpenOption;
        path.getParent().toFile().mkdirs();
        if (path.toFile().exists()) {
            standardOpenOption = StandardOpenOption.APPEND;
        } else {
            standardOpenOption = StandardOpenOption.CREATE_NEW;
        }
        try {
            Files.write(path,data.getBytes(StandardCharsets.UTF_8), standardOpenOption);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        FileUtils.writeFile("sdasfasfd");
        FileUtils.writeFile("sdasfasfd23124");
    }
}
