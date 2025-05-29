package org.areo.zhihui.utils;

public class CodeGeneratorUtil {
    //验证码生成
    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append((int) (Math.random() * 10));
        }
        return code.toString();
    }
}
