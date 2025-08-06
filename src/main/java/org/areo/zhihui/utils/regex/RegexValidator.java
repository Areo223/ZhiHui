package org.areo.zhihui.utils.regex;


import lombok.Getter;

import java.util.regex.Pattern;


public class RegexValidator {


    @Getter
    public enum RegexPattern {
        // 用户名: 字母开头，允许字母数字下划线，长度4-16
        USERNAME("^[a-zA-Z]\\w{3,15}$"),

        //学号
        IDENTIFIER("^20\\d{2}\\d{3,6}$"),

        // 密码: 至少8位，包含大小写字母和数字
        PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"),

        // 强密码: 至少8位，包含大小写字母、数字和特殊字符
        STRONG_PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"),

        // 邮箱
        EMAIL("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),

        // 手机号(中国)
        PHONE_CHINA("^1[3-9]\\d{9}$"),

        // 身份证号(中国)
        ID_CARD("^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$"),

        // IP地址
        IP_ADDRESS("^(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$"),

        // URL
        URL("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$"),

        // 日期(yyyy-MM-dd)
        DATE("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$"),

        // 时间(HH:mm:ss)
        TIME("^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$"),

        // 数字
        NUMBER("^-?\\d+(\\.\\d+)?$"),

        // 正整数
        POSITIVE_INTEGER("^[1-9]\\d*$"),

        // 负整数
        NEGATIVE_INTEGER("^-[1-9]\\d*$"),

        // 中文
        CHINESE("^[\\u4e00-\\u9fa5]+$"),

        // 邮政编码(中国)
        POSTAL_CODE("^[1-9]\\d{5}$");

        private final String pattern;

        RegexPattern(String pattern) {
            this.pattern = pattern;
        }

    }

    /**
     * 验证输入字符串是否符合指定的正则模式
     * @param input 要验证的字符串
     * @param pattern 正则表达式
     * @return 验证结果
     */
    public static boolean validate(String input, String pattern) {
        if (input == null) {
            return false;
        }
        return Pattern.matches(pattern, input);
    }

    /**
     * 验证输入字符串是否符合预定义的正则模式
     * @param input 要验证的字符串
     * @param regexPattern 预定义的正则模式
     * @return 验证结果
     */
    public static boolean validate(String input, RegexPattern regexPattern) {
        return validate(input, regexPattern.getPattern());
    }

    /**
     * 验证输入字符串是否符合指定的正则模式，不符合时抛出异常
     * @param input 要验证的字符串
     * @param pattern 正则表达式
     * @param errorMessage 验证失败时的错误信息
     * @throws IllegalArgumentException 如果验证失败
     */
    public static void validateWithException(String input, String pattern, String errorMessage) {
        if (!validate(input, pattern)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * 验证输入字符串是否符合预定义的正则模式，不符合时抛出异常
     * @param input 要验证的字符串
     * @param regexPattern 预定义的正则模式
     * @param errorMessage 验证失败时的错误信息
     * @throws IllegalArgumentException 如果验证失败
     */
    public static void validateWithException(String input, RegexPattern regexPattern, String errorMessage) {
        validateWithException(input, regexPattern.getPattern(), errorMessage);
    }

//    /**
//     * 自定义验证器构建器
//     */
//    public static class ValidatorBuilder {
//        private String input;
//        private String pattern;
//        private String errorMessage;
//
//        public ValidatorBuilder input(String input) {
//            this.input = input;
//            return this;
//        }
//
//        public ValidatorBuilder pattern(String pattern) {
//            this.pattern = pattern;
//            return this;
//        }
//
//        public ValidatorBuilder pattern(RegexPattern regexPattern) {
//            this.pattern = regexPattern.getPattern();
//            return this;
//        }
//
//        public ValidatorBuilder errorMessage(String errorMessage) {
//            this.errorMessage = errorMessage;
//            return this;
//        }
//
//        public boolean validate() {
//            return RegexValidator.validate(input, pattern);
//        }
//
//        public void validateWithException() {
//            if (errorMessage == null) {
//                errorMessage = "输入不符合要求";
//            }
//            RegexValidator.validateWithException(input, pattern, errorMessage);
//        }
//    }

    // 常用验证方法的快捷方式

    public static boolean isEmail(String input) {
        return validate(input, RegexPattern.EMAIL);
    }

    public static boolean isPhoneChina(String input) {
        return validate(input, RegexPattern.PHONE_CHINA);
    }

    public static boolean isIdCard(String input) {
        return validate(input, RegexPattern.ID_CARD);
    }

    public static boolean isUrl(String input) {
        return validate(input, RegexPattern.URL);
    }

    public static boolean isDate(String input) {
        return validate(input, RegexPattern.DATE);
    }

    public static boolean isChinese(String input) {
        return validate(input, RegexPattern.CHINESE);
    }
}
