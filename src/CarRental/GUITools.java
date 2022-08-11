package CarRental;

import java.awt.Component;
import java.awt.Toolkit;

//GUI工具类
public class GUITools {
    //JAVA提供的GUI默认工具类对象
    static Toolkit kit = Toolkit.getDefaultToolkit();

    //将指定组件屏幕居中
    public static void center(Component c) {
        int x = (kit.getScreenSize().width - c.getWidth()) / 2;
        int y = (kit.getScreenSize().height - c.getHeight()) / 2;
        c.setLocation(x, y);
    }
}
