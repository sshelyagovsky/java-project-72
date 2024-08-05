package hexlet.code.util;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Utils {

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project");
    }

    public static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
}
