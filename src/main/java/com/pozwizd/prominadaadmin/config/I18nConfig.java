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

        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

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

        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        List<Locale> supportedLocales = List.of(
                Locale.ENGLISH,
                new Locale("ru"),
                new Locale("uk")
        );
        resolver.setSupportedLocales(supportedLocales);
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

}