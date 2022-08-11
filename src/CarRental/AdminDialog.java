package CarRental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class AdminDialog extends JFrame {
    //定义界面使用到的组件作为成员变量
    private Container container = getContentPane();
    private JPanel jPanel = new JPanel();
    private JLabel tableLabel = new JLabel("汽车租赁表");
    private JScrollPane tablePane = new JScrollPane();
    protected JTable table = new JTable();

    private JButton addBtn = new JButton("添加数据");
    private JButton alterBtn = new JButton("修改数据");
    private JButton delBtn = new JButton("删除数据");
    private JButton searchBtn = new JButton("查询数据");
    private JButton searchAllBtn = new JButton("显示全部");
    private JButton sortBtn = new JButton("姓氏排序");//按首字母排序

    private JLabel delNameLable = new JLabel("租赁人姓名/电话");
    private JTextField delNameText = new JTextField();
    private JLabel searchLable = new JLabel("租赁人姓名/电话");
    private JTextField searchText = new JTextField();
    private JLabel alterLable = new JLabel("租赁人姓名/电话");
    private JTextField alterText = new JTextField();

    //无参构造函数
    public AdminDialog() {
        this.init();// 初始化操作
        this.addComponent();// 添加组件
        this.addListener();// 添加监听器
        displayData(loadData("SELECT * FROM lease_form.lease_form"));
    }

    // 初始化操作
    private void init() {
        this.setTitle("汽车租赁管理系统");
        this.setSize(600, 400);
        GUITools.center(this);//设置窗口在屏幕上的位置
        this.setResizable(false);// 窗体大小固定
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//关闭事件
    }

    //添加组件
    private void addComponent() {
        //取消布局
        jPanel.setLayout(null);
        jPanel.setBackground(new Color(135, 205, 250,50));

        //表格标题
        tableLabel.setBounds(265, 20, 70, 25);
        tableLabel.setBackground(new Color(135, 205, 250,50));

        jPanel.add(tableLabel);
        //表格
        table.getTableHeader().setReorderingAllowed(false);    //列不能移动
        table.getTableHeader().setResizingAllowed(false);      //不可拉动表格
        table.setEnabled(false);                               //不可更改数据
        tablePane.setBounds(50, 50, 500, 200);
        tablePane.setViewportView(table);                      //视口装入表格

        jPanel.add(tablePane);

        addBtn.setBounds(60, 300, 90, 25);
        alterBtn.setBounds(155, 300, 90, 25);
        delBtn.setBounds(250, 300, 90, 25);
        searchBtn.setBounds(345, 300, 90, 25);
        searchAllBtn.setBounds(250,330, 90, 25);
        sortBtn.setBounds(440, 300, 90, 25);

        jPanel.add(addBtn);
        jPanel.add(alterBtn);
        jPanel.add(delBtn);
        jPanel.add(searchBtn);
        jPanel.add(searchAllBtn);
        jPanel.add(sortBtn);

        delNameLable.setBounds(245, 250, 90, 25);
        delNameText.setBounds(250, 275, 90, 25);
        jPanel.add(delNameLable);
        jPanel.add(delNameText);

        searchLable.setBounds(340, 250, 100, 25);
        searchText.setBounds(345, 275, 90, 25);
        jPanel.add(searchLable);
        jPanel.add(searchText);

        alterLable.setBounds(150, 250, 100, 25);
        alterText.setBounds(155, 275, 90, 25);
        jPanel.add(alterLable);
        jPanel.add(alterText);

        container.add(jPanel);
    }

    public ArrayList<CarRental> loadData(String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;//数据库给我们返回一个set字典
        ArrayList<CarRental> list = new ArrayList<>();
        //JDBC
        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                CarRental carRental = new CarRental();
                carRental.setName(resultSet.getString("name"));
                carRental.setPhoneNumber(resultSet.getString("phone_number"));
                carRental.setAddress(resultSet.getString("address"));
                carRental.setCarID(resultSet.getString("car_id"));
                carRental.setPriceAndDay(resultSet.getString("price_and_day"));
                list.add(carRental);
            }
            return list;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(resultSet, statement, connection);
        }
        return null;
    }

    public void displayData(ArrayList<CarRental> dataList) {
        String[] title = new String[]{"租赁人", "电话号码", "地址", "车辆编号", "价格/天数"};
        String[][] toStringArrayData = listToArray(dataList);
        TableModel dataModel = new DefaultTableModel(toStringArrayData, title);
        table.setModel(dataModel);
    }

    public String[][] listToArray(ArrayList<CarRental> list) {
        String[][] toStringArrayData = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            CarRental carRental = list.get(i);
            toStringArrayData[i][0] = carRental.getName();
            toStringArrayData[i][1] = carRental.getPhoneNumber();
            toStringArrayData[i][2] = carRental.getAddress();
            toStringArrayData[i][3] = carRental.getCarID();
            toStringArrayData[i][4] = carRental.getPriceAndDay();
        }
        return toStringArrayData;
    }

    // 添加监听器
    private void addListener() {
        //添加数据
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddDialog().setVisible(true);
            }
        });
        //修改数据
        alterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String alterNamePhone = alterText.getText();
                if (CharTool.isEmpty(alterNamePhone)) {
                    isEmptyDialog();
                } else if (!SearchTool.hasData(alterNamePhone)) {
                    noHasData();
                } else {
                    new AlterDialog(alterNamePhone).setVisible(true);
                }
            }
        });
        //删除数据
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String delName = delNameText.getText();
                if (CharTool.isEmpty(delName)) {
                    isEmptyDialog();
                } else if (!SearchTool.hasData(delName)) {
                    noHasData();
                } else {
                    delAddressItem(delName);
                }
            }
        });
        //查询数据
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchData = searchText.getText();
                if (CharTool.isEmpty(searchData)) {
                    isEmptyDialog();
                } else if (!SearchTool.hasData(searchData)) {
                    noHasData();
                } else {
                    searchAddressItem(searchData);
                }
            }
        });
        //显示全部
        searchAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayData(loadData("SELECT * FROM lease_form.lease_form"));
            }
        });
        //姓氏排序
        sortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<CarRental> sortData = loadData("SELECT * FROM lease_form.lease_form");
                Collections.sort(sortData);
                String[][] sortStringData = listToArray(sortData);
                String[] title = new String[]{"租赁人", "电话号码", "地址", "车辆编号", "价格/天数"};
                TableModel dataModel = new DefaultTableModel(sortStringData, title);
                table.setModel(dataModel);
            }
        });
    }

    private void noHasData() {
        JOptionPane.showMessageDialog(this, "没有所输入字段的数据,操作失败");
    }

    private void isEmptyDialog() {
        JOptionPane.showMessageDialog(this, "该字段不能为空或空格");
    }

    private void delAddressItem(String name) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        int sign;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.createStatement();

            if (!CharTool.isNumeric(name)) {
                String sql = "delete from lease_form.lease_form where name =" + "'" + name + "'";//注意字符串要用单引号//!
                sign = stmt.executeUpdate(sql);
                if (sign > 0) {
                    JOptionPane.showMessageDialog(this, "数据删除成功");
                }
            } else if(!CharTool.isABC(name)){
                String sql = "delete from lease_form.lease_form where phone_number =" + "'" + name + "'";//注意字符串要用单引号//!
                sign = stmt.executeUpdate(sql);
                if (sign > 0) {
                    JOptionPane.showMessageDialog(this, "数据删除成功");
                }
            } else{
                isEmptyDialog();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }
    }

    private void searchAddressItem(String namePhoneData) {
        if (!CharTool.isNumeric(namePhoneData)) {
            String sql = "select * from lease_form.lease_form where name = '" + namePhoneData + "'";
            displayData(loadData(sql));
        } else {
            String sql = "select * from lease_form.lease_form where phone_number = '" + namePhoneData + "'";
            displayData(loadData(sql));
        }
    }
}