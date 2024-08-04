package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.Utils;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static hexlet.code.util.Utils.createTemplateEngine;
import static hexlet.code.util.Utils.getPort;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException, SQLException {
        setDataSource();
        var app = getApp();
        app.start(getPort());
    }

    public static void setDataSource() throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        HikariDataSource dataSource;
        String sql;
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null) {
            hikariConfig.setJdbcUrl(dbUrl);
            hikariConfig.setUsername(System.getenv().get("JDBC_DATABASE_USERNAME"));
            hikariConfig.setPassword(System.getenv().get("JDBC_DATABASE_PASSWORD"));
            hikariConfig.setDriverClassName(org.postgresql.Driver.class.getName());
        } else {
            hikariConfig.setJdbcUrl("jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
        }
        sql = Utils.readResourceFile("schema.sql");
        dataSource = new HikariDataSource(hikariConfig);

        log.info(sql);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        BaseRepository.dataSource = dataSource;
    }

    public static Javalin getApp()  {

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), RootController::index);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::show);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::showUrl);
        app.post(NamedRoutes.urlPathCheck("{id}"), UrlController::urlCheck);

        return app;
    }

}
