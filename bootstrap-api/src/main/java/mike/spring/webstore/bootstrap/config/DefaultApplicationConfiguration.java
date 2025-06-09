package mike.spring.webstore.bootstrap.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class DefaultApplicationConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        Locale.setDefault(Locale.ENGLISH);

        var resolver = new CookieLocaleResolver ();
        resolver.setDefaultLocale(Locale.getDefault());

        return resolver;
    }
}
