package hexlet.code.util;

import hexlet.code.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Utils {


    public static int getPort() {
        String port = System.getenv()
                .getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static String readResourceFile(String fileName) throws IOException {
        var url = App.class.getClassLoader()
                .getResourceAsStream(fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url, StandardCharsets.UTF_8))) {
            return reader.lines()
                    .collect(Collectors.joining("\n"));
        }
    }
}
