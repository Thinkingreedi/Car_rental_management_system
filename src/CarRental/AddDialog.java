package CarRental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//增加数据
public class AddDialog extends JDialog {
    //创建标签
    private JLabel nameLabel = new JLabel("租赁人:");
    private JLabel phoneNumberLabel = new JLabel("电话号码:");
    private JLabel addressLabel = new JLabel("地址:");
    private JLabel carIDLabel = new JLabel("车辆编号:");
    private JLabel priceAndDayLable = new JLabel("价格/天数:");

    //创建文本框
    protected JTextField nameText = new JTextField(6);
    protected JTextField phoneNumberText = new JTextField(6);
    protected JTextField addressText = new JTextField(6);
    protected JTextField carIDText = new JTextField(6);
    protected JTextField priceAndDayText = new JTextField(6);
    private JButton submitBtn = new JButton("提交数据");

    //无参构造函数
    public AddDialog() {
        this(null, true);
    }

    //有参构造函数
    public AddDialog(Frame owner, boolean modal) {
        super(owner, modal);
        this.init();// 初始化操作
        this.addComponent();// 添加组件
        this.addListener();// 添加监听器
    }

    // 初始化操作
    private void init() {
        this.setTitle("添加数据");
        this.setSize(300, 250);
        GUITools.center(this);//设置窗口在屏幕上的位置
        this.setResizable(false);// 窗体大小固定
    }

    // 添加组件
    private void addComponent() {
        this.setLayout(null);//取消布局！！！！
        nameLabel.setBounds(60, 10, 60, 25);
        phoneNumberLabel.setBounds(60, 40, 60, 25);
        addressLabel.setBounds(60, 70, 60, 25);
        carIDLabel.setBounds(60, 100, 60, 25);
        priceAndDayLable.setBounds(60, 130, 60, 25);

        nameText.setBounds(125, 10, 80, 25);
        phoneNumberText.setBounds(125, 40, 80, 25);
        addressText.setBounds(125, 70, 80, 25);
        carIDText.setBounds(125, 100, 80, 25);
        priceAndDayText.setBounds(125, 130, 80, 25);

        submitBtn.setBounds(105, 170, 90, 25);

        this.add(nameLabel);
        this.add(phoneNumberLabel);
        this.add(addressLabel);
        this.add(carIDLabel);
        this.add(priceAndDayLable);

        this.add(nameText);
        this.add(phoneNumberText);
        this.add(addressText);
        this.add(carIDText);
        this.add(priceAndDayText);

        this.add(submitBtn);
    }

    //添加监听器
    private void addListener() {
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String phoneNumber = phoneNumberText.getText();
                String address = addressText.getText();
                String emailAddress = carIDText.getText();
                String remarks = priceAndDayText.getText();
                CarRental carRental = new CarRental();
                carRental.setName(name);
                carRental.setPhoneNumber(phoneNumber);
                carRental.setAddress(address);
                carRental.setCarID(emailAddress);
                carRental.setPriceAndDay(remarks);

                int sign = addAddressItem(carRental);
                if (sign == 1) {
                    AddDialog.this.dispose();
                }
            }
        });
    }

    //数据库操作
    private int addAddressItem(CarRental addressBookItem) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        int sign = 0;

        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();

            if (!CharTool.isEmpty(addressBookItem.getName())) {
                if(!CharTool.isABC(addressBookItem.getPhoneNumber())) {
                    String sql = "INSERT INTO lease_form.lease_form(name, phone_number, address, car_id, price_and_day)"
                            + "VALUES('" + addressBookItem.getName() + "','" + addressBookItem.getPhoneNumber()
                            + "','" + addressBookItem.getAddress() + "','" + addressBookItem.getCarID()
                            + "','" + addressBookItem.getPriceAndDay() + "')";
                    try{
                        sign = statement.executeUpdate(sql);
                        if (sign > 0) {
                            JOptionPane.showMessageDialog(this, "数据添加成功");
                        }
                    }catch (SQLException e){
                        //e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "用户名重复！！","warning",JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "电话字段不能出现字母");
                }
            } else {
                JOptionPane.showMessageDialog(this, "姓名字段不能为空或空格");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCUtils.release(resultSet, statement, connection);
        return sign==0 ? 0 : 1;
    }
}
