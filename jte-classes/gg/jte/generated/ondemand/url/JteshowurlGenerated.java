package gg.jte.generated.ondemand.url;
import hexlet.code.dto.url.UrlPage;
import hexlet.code.util.NamedRoutes;
import java.time.format.DateTimeFormatter;
public final class JteshowurlGenerated {
	public static final String JTE_NAME = "url/showurl.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,3,3,4,4,6,8,8,10,10,10,15,15,15,19,19,19,23,23,23,28,28,28,28,28,28,28,28,28,41,41,44,44,44,47,47,47,50,50,50,53,53,53,56,56,56,59,59,59,62,62,66,66,66,66,66,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		jteOutput.writeContent("\r\n\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n    <div class=\"container-lg mt-5\">\r\n        <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\r\n        <table class=\"table table-bordered table-hover mt-3\">\r\n            <tbody>\r\n            <tr>\r\n                <td>ID</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            <tr>\r\n                <td>Имя</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            <tr>\r\n                <td>Дата создания</td>\r\n                <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt().toLocalDateTime().format(dateFormat));
				jteOutput.writeContent("</td>\r\n            </tr>\r\n            </tbody>\r\n        </table>\r\n        <h2 class=\"mt-5\">Проверки</h2>\r\n        <form method=\"post\"");
				var __jte_html_attribute_0 = NamedRoutes.urlPathCheck(page.getUrl().getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\r\n            <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\r\n        </form>\r\n        <table class=\"table table-bordered table-hover mt-3\">\r\n            <thead>\r\n            <tr><th class=\"col-1\">ID</th>\r\n                <th class=\"col-1\">Код ответа</th>\r\n                <th>title</th>\r\n                <th>h1</th>\r\n                <th>description</th>\r\n                <th class=\"col-2\">Дата проверки</th>\r\n            </tr></thead>\r\n            <tbody>\r\n            ");
				for (var checkUrl : page.getCheckUrls()) {
					jteOutput.writeContent("\r\n                <tr>\r\n                    <td>\r\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(checkUrl.getId());
					jteOutput.writeContent("\r\n                    </td>\r\n                    <td>\r\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(checkUrl.getStatusCode());
					jteOutput.writeContent("\r\n                    </td>\r\n                    <td>\r\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(checkUrl.getTitle());
					jteOutput.writeContent("\r\n                    </td>\r\n                    <td>\r\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(checkUrl.getH1());
					jteOutput.writeContent("\r\n                    </td>\r\n                    <td>\r\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(checkUrl.getDescription());
					jteOutput.writeContent("\r\n                    </td>\r\n                    <td>\r\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(checkUrl.getCreatedAt().toLocalDateTime().format(dateFormat));
					jteOutput.writeContent("\r\n                    </td>\r\n                </tr>\r\n            ");
				}
				jteOutput.writeContent("\r\n            </tbody>\r\n        </table>\r\n    </div>\r\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
