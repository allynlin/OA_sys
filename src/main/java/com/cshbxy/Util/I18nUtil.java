package com.cshbxy.Util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class I18nUtil {

    /**
     * 获取信息，根据 key 获取对应的 value
     *
     * @param code 对应 messages 配置的 key
     * @return string
     */

    public static String getMessage(String code) {

        return getMessage(code, null);
    }

    public static String getMessage(String code, Object[] args) {

        return getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        // 获取本地语言环境
        Locale locale = LocaleContextHolder.getLocale();
        // 获取国际化资源文件
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 设置国际化资源编码
        messageSource.setDefaultEncoding("UTF-8");
        // 设置国际化资源文件
        messageSource.setBasename("i18n/messages");
        String content;
        // 获取国际化资源文件中的内容
        try {
            // 尝试根据对应语言获取对应的内容
            content = messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果获取不到对应的内容，则返回默认内容
            content = defaultMessage;
        }
        return content;
    }
}
