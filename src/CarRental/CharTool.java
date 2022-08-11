package CarRental;

import java.util.regex.Pattern;

/* 字符串工具类 */
public class CharTool {
    public static boolean isNumeric(String str) {  //判断字符串是否全是数字 正则表达式
        Pattern pattern = Pattern.compile("[0-9]*"); //零个或者多个数字 等价于\d*
        return pattern.matcher(str).matches();
    }

    public static boolean isABC(String str){  //判断字符串中是否包含字母
        Pattern pattern = Pattern.compile(".*[a-zA-z].*");  //零个或者多个任意字符 a到z A到Z 零个或者多个任意字符
        return pattern.matcher(str).matches();
    }

    public static boolean isEmpty(String str) {  //判断空格
        Pattern pattern = Pattern.compile(" *");  //零个或者多个空格字符
        return pattern.matcher(str).matches();
    }

    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }
}
