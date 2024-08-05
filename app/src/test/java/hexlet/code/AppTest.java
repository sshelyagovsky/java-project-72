package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.Utils;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    private static Javalin app;
    private static MockWebServer mockWebServer;

    @BeforeAll
    public static void startMock() throws IOException {
        mockWebServer = new MockWebServer();
        MockResponse response = new MockResponse()
                .setBody(Utils.readResourceFile("fixtures/url_check.html"));
        mockWebServer.enqueue(response);
        mockWebServer.start();
    }

    @AfterAll
    public static void stopMock() throws IOException {
        mockWebServer.shutdown();
        app.stop();
    }

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var request = "url=https://www.example.com";
            client.post(NamedRoutes.urlsPath(), request);
            var response = client.get(NamedRoutes.urlsPath());
            var responseBody = response.body().string();

            assertEquals("https://www.example.com",
                    UrlRepository.findByName("https://www.example.com").get().getName());

            assertThat(response.code()).isEqualTo(200);
            assertThat(responseBody).contains("Сайты");
            assertThat(responseBody).contains("https://www.example.com");
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        Url url = new Url("https://www.example1.com");
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            var responseBody = response.body().string();

            assertThat(responseBody).contains("Сайт: https://www.example1.com");
            assertThat(responseBody).contains("Проверки");

        });
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath("999999"));
            assertThat(response.code()).isEqualTo(404);
            assertThat(response.body().string()).contains("Url не найден");
        });
    }

    @Test
    public void testMock() {
        String mockServerUrl = mockWebServer.url("/").toString();

        JavalinTest.test(app, (server, client) -> {

            Url url = new Url(mockServerUrl);
            UrlRepository.save(url);
            client.post(NamedRoutes.urlPathCheck(url.getId()));

            var checkUrl = UrlCheckRepository.findByUrlId(url.getId());
            var title = checkUrl.getFirst().getTitle();
            var h1 = checkUrl.getFirst().getH1();
            assertThat(title).isEqualTo("Анализатор страниц");
            assertThat(h1).isEqualTo("Сайт: https://www.example.com");
        });
    }
}
