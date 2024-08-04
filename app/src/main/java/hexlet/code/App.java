package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

import static hexlet.code.util.Utils.createTemplateEngine;
import static hexlet.code.util.Utils.getDatabaseUrl;
import static hexlet.code.util.Utils.getPort;
import static hexlet.code.util.Utils.readResourceFile;

@Slf4j
public class App {

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        String sql = readResourceFile("schema.sql");
        log.info(sql);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(javalinConfig -> {
            javalinConfig.bundledPlugins.enableDevLogging();
            javalinConfig.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), RootController::index);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::show);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::showUrl);
        app.post(NamedRoutes.urlPathCheck("{id}"), UrlController::urlCheck);

        return app;
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }
}
