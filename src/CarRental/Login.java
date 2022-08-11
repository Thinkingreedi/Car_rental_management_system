package CarRental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//登录功能页面
public class Login extends JFrame{
    JPanel jPanel1, jPanel2, jPanel3;
    JTextField jTextField;
    JPasswordField jPasswordField;
    JLabel jLabel1, jLabel2;
    JButton jButton1, jButton2;

    //用于限定用户名密码长度
    int TextLength = 14;
    int PasswordLength = 16;

    public Login(){
        this.start();
    }

    public void start(){
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();

        jLabel1 = new JLabel("用户名：");
        jLabel2 = new JLabel("密码：");

        jButton1 = new JButton("登录");
        jButton2 = new JButton("退出");

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(jTextField.getText().trim().length()==0||new String(jPasswordField.getPassword()).trim().length()==0){  //获取文本并删除字符串头尾空白符
                    JOptionPane.showMessageDialog(null,"用户名或密码不允许为空","warning",JOptionPane.ERROR_MESSAGE);

                }
                else if(jTextField.getText().trim().length() > TextLength||new String(jPasswordField.getPassword()).trim().length() > PasswordLength){
                    JOptionPane.showMessageDialog(null, "用户名或密码超出限定位数","warning",JOptionPane.WARNING_MESSAGE);

                }
                else if((jTextField.getText().trim().equals("root")||jTextField.getText().trim().equals("admin")||jTextField.getText().trim().equals("master"))&&new String(jPasswordField.getPassword()).trim().equals("123456")){
                    JOptionPane.showMessageDialog(null, "登录成功");
                    Login.this.dispose();
                    new AdminDialog().setVisible(true);  //创建APP对象并设置GUI窗口可见
                }
                else{
                    JOptionPane.showMessageDialog(null, "用户名或密码错误","error",JOptionPane.QUESTION_MESSAGE);
                }
            }
        });

        jButton2.addActionListener(new ActionListener() {
            //设置监听
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //关闭窗口释放屏幕资源
                System.exit(0);
            }
        });

        //设计对话框及密码框的长度
        jTextField = new JTextField(14);
        jPasswordField = new JPasswordField(16);

        //采用网格布局三行一列
        this.setLayout(new GridLayout(3,1));

        //此JFrame加入三个JPanel
        this.add(jPanel1);
        this.add(jPanel2);
        this.add(jPanel3);

        //第一个JPanel加入JLabel和JTextField
        jPanel1.add(jLabel1);
        jPanel1.add(jTextField);

        //第二个JPanel加入JLabel和JPasswordField
        jPanel2.add(jLabel2);
        jPanel2.add(jPasswordField);

        //第三个JPanel加入两个JButton
        jPanel3.add(jButton1);
        jPanel3.add(jButton2);

        //对窗口的一些设置
        this.setTitle("登录界面"); //设置标题
        this.setSize(400,180);  //设置窗口大小
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((screenWidth - 400)/2, (screenHeight-180)/2);  //设置窗口居中对齐
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //设置窗口关闭
        this.setVisible(true);  //设置窗口可见性
    }
}
