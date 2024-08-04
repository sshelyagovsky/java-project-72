@import hexlet.code.dto.url.UrlsPage
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlsPage page

!{var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

@template.layout.page(
page = page,
content = @`

    <div class="container-lg mt-5">
        <h1>Сайты</h1>
        <table class="table table-bordered table-hover mt-3">
            <thead>
            <tr>
                <th class="col-1">ID</th>
                <th>Имя</th>
                <th class="col-2">Последняя проверка</th>
                <th class="col-1">Код ответа</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getUrls())

                !{var urlCheck = page.getLatestCheckUrl().get(url.getId());}
                <tr>
                    <td>
                        ${url.getId()}
                    </td>
                    <td>
                        <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                    </td>
                    <td>
                        <a>${urlCheck == null ? "" : urlCheck.getCreatedAt().toLocalDateTime().format(dateFormat)}</a>
                    </td>
                    <td>
                        <a>${urlCheck == null ? "" : String.valueOf(urlCheck.getStatusCode())}</a>
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`,
)