package hexlet.code.controller;

import hexlet.code.dto.url.UrlPage;
import hexlet.code.dto.url.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlController {

    public static void create(Context ctx) throws SQLException {
        var inputUrl = ctx.formParam("url");
        URL checkUrl;
        try {
            checkUrl = new URI(inputUrl).toURL();
        } catch (URISyntaxException | MalformedURLException | NullPointerException | IllegalArgumentException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        var urlProtocol = checkUrl.getProtocol();
        var urlPath = checkUrl.getHost();
        var urlPort = checkUrl.getPort() == -1 ? "" : checkUrl.getPort();

        var resultUrl = urlProtocol + "://" + urlPath + urlPort;

        var url = UrlRepository.findByName(resultUrl);

        if (url != null) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "info");
        } else {
            Url newUrl = new Url(resultUrl.toLowerCase());
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }
    public static void show(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("url/show.jte", model("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url не найден"));
        var page = new UrlPage(url);
        ctx.render("url/showurl.jte", model("page", page));

    }
}
