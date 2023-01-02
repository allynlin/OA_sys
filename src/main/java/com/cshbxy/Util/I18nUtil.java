package com.cshbxy.Util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class I18nUtil {
    public static String getMessage(String code) {
        return getMessage(code, null);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("i18n/messages");
        String content;
        try {
            content = messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            e.printStackTrace();
            content = defaultMessage;
        }
        return content;

    }
}
