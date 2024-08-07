package gg.jte.generated.ondemand.url;
import hexlet.code.dto.url.UrlsPage;
import hexlet.code.util.NamedRoutes;
import java.time.format.DateTimeFormatter;
public final class JteshowGenerated {
	public static final String JTE_NAME = "url/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,3,3,4,4,6,8,8,22,22,24,24,27,27,27,30,30,30,30,30,30,30,30,30,30,30,30,33,33,33,36,36,36,39,39,43,43,43,43,43,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		var dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		jteOutput.writeContent("\n\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n\n    <div class=\"container-lg mt-5\">\n        <h1>Сайты</h1>\n        <table class=\"table table-bordered table-hover mt-3\">\n            <thead>\n            <tr>\n                <th class=\"col-1\">ID</th>\n                <th>Имя</th>\n                <th class=\"col-2\">Последняя проверка</th>\n                <th class=\"col-1\">Код ответа</th>\n            </tr>\n            </thead>\n            <tbody>\n            ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n\n                ");
					var urlCheck = page.getLatestCheckUrl().get(url.getId());
					jteOutput.writeContent("\n                <tr>\n                    <td>\n                        ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("\n                    </td>\n                    <td>\n                        <a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\n                    </td>\n                    <td>\n                        <a>");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(urlCheck == null ? "" : urlCheck.getCreatedAt().toLocalDateTime().format(dateFormat));
					jteOutput.writeContent("</a>\n                    </td>\n                    <td>\n                        <a>");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(urlCheck == null ? "" : String.valueOf(urlCheck.getStatusCode()));
					jteOutput.writeContent("</a>\n                    </td>\n                </tr>\n            ");
				}
				jteOutput.writeContent("\n            </tbody>\n        </table>\n    </div>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
