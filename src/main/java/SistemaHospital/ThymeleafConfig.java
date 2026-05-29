package SistemaHospital;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

public class ThymeleafConfig {

    private static final TemplateEngine engine = buildEngine();

    private static TemplateEngine buildEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");   // onde ficam os HTMLs
        resolver.setSuffix(".html");          // extensão dos arquivos
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        TemplateEngine te = new TemplateEngine();
        te.setTemplateResolver(resolver);
        return te;
    }

    public static String render(String template, Map<String, Object> model) {
        Context ctx = new Context();
        ctx.setVariables(model);
        return engine.process(template, ctx);
    }
}