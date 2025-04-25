package com.pozwizd.prominadaadmin.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * Конфигурационный класс для настройки интернационализации (i18n) в Spring Boot приложении.
 * Определяет бины MessageSource (для доступа к файлам переводов) и LocaleResolver (для определения локали пользователя).
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    /**
     * Создает и настраивает бин MessageSource.
     * MessageSource отвечает за загрузку и предоставление локализованных сообщений
     * из файлов ресурсов (например, messages_ru.properties).
     * @return Настроенный экземпляр MessageSource.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
//        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /**
     * Создает и настраивает бин LocaleResolver.
     * LocaleResolver определяет, какую локаль (язык и регион) использовать для текущего запроса пользователя.
     * @return Настроенный экземпляр LocaleResolver.
     */
    @Bean
    public LocaleResolver customLocaleResolver() {
        // Original logic from search result
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        List<Locale> supportedLocales = List.of(
                new Locale("uk","UA"),
                new Locale("en","US"),
                new Locale("ru","RU")
        );
        resolver.setSupportedLocales(supportedLocales);
        resolver.setDefaultLocale(new Locale("uk","UA")); // Устанавливаем украинский как язык по умолчанию
        return resolver;
    }

}