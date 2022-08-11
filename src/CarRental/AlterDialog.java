package CarRental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//修改数据
public class AlterDialog extends JDialog {
    //创建标签
    private JLabel nameLabel = new JLabel("租赁人:");
    private JLabel phoneNumberLabel = new JLabel("电话:");
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

    private String addressData;//接收主界面的查询字段

    //构造函数1
    public AlterDialog(String addressData) {
        this(null, true, addressData);
    }

    //构造函数2
    public AlterDialog(Frame owner, boolean modal, String addressData) {
        super(owner, modal);
        this.addressData = addressData;//注意传参的过程
        this.init();// 初始化操作
        this.addComponent();// 添加组件
        setAlterDialogText();
        this.addListener();// 添加监听器
    }

    // 初始化操作
    private void init() {
        this.setTitle("修改数据");
        this.setSize(300, 250);
        GUITools.center(this);//设置窗口在屏幕上的位置
        this.setResizable(false);// 窗体大小固定
    }

    //添加组件
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
                CarRental carRental = new CarRental();
                carRental.setName(nameText.getText());
                carRental.setPhoneNumber(phoneNumberText.getText());
                carRental.setAddress(addressText.getText());
                carRental.setCarID(carIDText.getText());
                carRental.setPriceAndDay(priceAndDayText.getText());

                int sign = alterData(carRental);
                if (sign == 1) {
                    AlterDialog.this.dispose();
                }
            }
        });
    }

    private void setAlterDialogText() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        CarRental leaseForm = new CarRental();
        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();

            if (!CharTool.isNumeric(addressData)) {
                String sql = "select * from lease_form.lease_form where name = '" + addressData + "'";
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    nameText.setText(resultSet.getString("name"));
                    phoneNumberText.setText(resultSet.getString("phone_number"));
                    addressText.setText(resultSet.getString("address"));
                    carIDText.setText(resultSet.getString("car_id"));
                    priceAndDayText.setText(resultSet.getString("price_and_day"));
                }
            } else {
                String sql = "select * from lease_form.lease_form where phone_number = '" + addressData + "'";
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    nameText.setText(resultSet.getString("name"));
                    phoneNumberText.setText(resultSet.getString("phone_number"));
                    addressText.setText(resultSet.getString("address"));
                    carIDText.setText(resultSet.getString("car_id"));
                    priceAndDayText.setText(resultSet.getString("price_and_day"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(resultSet, statement, connection);
        }
    }

    private int alterData(CarRental AlterData) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int sign = 0;
        String nameData;

        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();
            nameData = AlterData.getName();

            if (!CharTool.isEmpty(nameData)) {
                if (!CharTool.isNumeric(addressData)) {
                    if(!CharTool.isABC(AlterData.getPhoneNumber())){
                        String sql = "update lease_form.lease_form set name = '" + AlterData.getName() + "', phone_number = '"
                                + AlterData.getPhoneNumber() + "', address = '" + AlterData.getAddress() + "', car_id = '"
                                + AlterData.getCarID() + "', price_and_day = '" + AlterData.getPriceAndDay() + "' "
                                + " where name = '" + addressData + "'";//注意字符串要用单引号
                        sign = statement.executeUpdate(sql);
                        if (sign > 0) {
                            JOptionPane.showMessageDialog(this, "数据修改成功");
                        }
                    }else{
                        isABCDialog();
                    }
                }else{
                    String sql = "update lease_form.lease_form set name = '" + AlterData.getName() + "', phone_number = '"
                            + AlterData.getPhoneNumber() + "', address = '" + AlterData.getAddress() + "', car_id = '"
                            + AlterData.getCarID() + "', price_and_day = '" + AlterData.getPriceAndDay() + "' "
                            + " where phone_number = '" + addressData + "'";//注意字符串要用单引号
                    sign = statement.executeUpdate(sql);
                    if (sign > 0) {
                        JOptionPane.showMessageDialog(this, "数据修改成功");
                    }
                }
            } else {
                isEmptyDialog();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JDBCUtils.release(resultSet, statement, connection);
        return sign==0 ? 0 : 1;
    }

    public void isEmptyDialog() {
        JOptionPane.showMessageDialog(this, "该字段不能为空或空格");
    }

    public void isABCDialog() {
        JOptionPane.showMessageDialog(this, "电话字段不能包含字母");
    }
}
