@import hexlet.code.dto.url.UrlPage
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlPage page
!{var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
page = page,
content = @`
    <div class="container-lg mt-5">
        <h1>Сайт: ${page.getUrl().getName()}</h1>
        <table class="table table-bordered table-hover mt-3">
            <tbody>
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${page.getUrl().getCreatedAt().toLocalDateTime().format(dateFormat)}</td>
            </tr>
            </tbody>
        </table>
        <h2 class="mt-5">Проверки</h2>
        <form method="post" action="${NamedRoutes.urlPathCheck(page.getUrl().getId())}">
            <button type="submit" class="btn btn-primary">Запустить проверку</button>
        </form>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr><th class="col-1">ID</th>
                <th class="col-1">Код ответа</th>
                <th>title</th>
                <th>h1</th>
                <th>description</th>
                <th class="col-2">Дата проверки</th>
            </tr></thead>
            <tbody>
            @for(var checkUrl : page.getCheckUrls())
                <tr>
                    <td>
                        ${checkUrl.getId()}
                    </td>
                    <td>
                        ${checkUrl.getStatusCode()}
                    </td>
                    <td>
                        ${checkUrl.getTitle()}
                    </td>
                    <td>
                        ${checkUrl.getH1()}
                    </td>
                    <td>
                        ${checkUrl.getDescription()}
                    </td>
                    <td>
                        ${checkUrl.getCreatedAt().toLocalDateTime().format(dateFormat)}
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`)