package hexlet.code.controller;

import hexlet.code.dto.url.UrlPage;
import hexlet.code.dto.url.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;

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
        var urlPort = checkUrl.getPort() == -1 ? "" : ":" + checkUrl.getPort();

        var resultUrl = urlProtocol + "://" + urlPath + urlPort;

        var url = UrlRepository.findByName(resultUrl).orElse(null);

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
        var latestUrlCheck = UrlCheckRepository.findLatestCheckUrl();
        var page = new UrlsPage(urls, latestUrlCheck);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("url/show.jte", model("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url не найден"));
        var urlChecks = UrlCheckRepository.findByUrlId(id);
        var page = new UrlPage(url, urlChecks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("url/showurl.jte", model("page", page));
    }

    public static void urlCheck(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url c " + id + "не найден"));
        try {
            HttpResponse<String> jsonResponse = Unirest.get(url.getName()).asString();
            int responseStatus = jsonResponse.getStatus();
            var responseBody = Jsoup.parse(jsonResponse.getBody());
            var responseTitle = responseBody.title();
            var responseH1Element = responseBody.selectFirst("h1");
            var responseH1 = responseH1Element == null ? "" : responseH1Element.text();
            var responseDescriptionElement = responseBody.selectFirst("meta[name=description]");
            var responseDescription = responseDescriptionElement == null ? ""
                    : responseDescriptionElement.attr("content");
            UrlCheck urlCheck = new UrlCheck(responseStatus, responseTitle, responseH1, responseDescription);
            urlCheck.setUrlId(id);
            UrlCheckRepository.saveUrlCheck(urlCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "info");

        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flash-type", "danger");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", e.getMessage());
            ctx.sessionAttribute("flash-type", "danger");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
