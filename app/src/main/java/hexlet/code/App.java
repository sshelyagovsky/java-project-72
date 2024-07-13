package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.Utils;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.sql.SQLException;


@Slf4j
public class App {

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(Utils.getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        String databaseUrl = System.getenv()
                .getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project");

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseUrl);

        var dataSource = new HikariDataSource(hikariConfig);
        var sql = Utils.readResourceFile("schema.sql");

        log.info(sql);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> ctx.result("Hello World"));
        return app;
    }
}
