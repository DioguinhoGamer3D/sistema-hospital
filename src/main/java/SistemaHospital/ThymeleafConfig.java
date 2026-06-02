package SistemaHospital;

import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

public class ThymeleafConfig {

    private static final TemplateEngine engine = buildEngine();

    public static TemplateEngine buildEngine() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
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

    public static JavalinThymeleaf javalinThymeleaf() {
        return new JavalinThymeleaf(engine);
    }
}