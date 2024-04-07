package com.qa.ui;

import cn.hutool.core.io.FileUtil;
import domain.User;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RegisterJframe extends JFrame implements MouseListener {


    ArrayList<User> allUser = new ArrayList<>();
    JTextField username = new JTextField();

    JPasswordField password = new JPasswordField();

    JPasswordField repassword = new JPasswordField();

    JButton regist = new JButton();

    JButton seepassword = new JButton();

    JButton reset = new JButton();

    public RegisterJframe(ArrayList<User> allUser) {
        this.allUser = allUser;
        //初始化菜单
        initJFrame();

        initView();

        setVisible(true);
    }

    private void initView() {
        //创建用户名注册图片
        JLabel usernameText = new JLabel(new ImageIcon("image\\register\\注册用户名.png"));
        usernameText.setBounds(85, 135, 80, 20);

        //创建用户名输入框
        username.setBounds(195, 134, 200, 30);

        //创建用户注册密码输入图片
        JLabel passwordText = new JLabel(new ImageIcon("image\\register\\注册密码.png"));
        passwordText.setBounds(97, 193, 70, 20);

        //创建密码输入框
        password.setBounds(195, 195, 200, 30);

        //确认密码输入输入图片
        JLabel repasswordText = new JLabel(new ImageIcon("image\\register\\再次输入密码.png"));
        repasswordText.setBounds(64, 255, 95, 20);

        //创建密码再次输入框
        repassword.setBounds(195, 255, 200, 30);

        //添加用户注册按钮
        regist.setIcon(new ImageIcon("image\\register\\注册按钮.png"));
        regist.setBounds(123, 310, 128, 47);
        regist.setBorderPainted(false);
        regist.setContentAreaFilled(false);
        regist.addMouseListener(this);


        //添加重置按钮
        reset.setIcon(new ImageIcon("image\\register\\重置按钮.png"));
        reset.setBounds(256, 310, 128, 47);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.addMouseListener(this);


        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("image\\register\\background.png"));
        background.setBounds(0, 0, 470, 390);

        this.getContentPane().add(usernameText);
        this.getContentPane().add(username);
        this.getContentPane().add(passwordText);
        this.getContentPane().add(password);
        this.getContentPane().add(repasswordText);
        this.getContentPane().add(repassword);
        this.getContentPane().add(regist);
        this.getContentPane().add(reset);
        this.getContentPane().add(background);
    }

    private void initJFrame() {
        setSize(488, 500);

        setTitle("qa的拼图小游戏 V 1.0 注册界面");

        setAlwaysOnTop(true);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == regist) {
            //点击了注册按钮
            //1.用户名，密码不能为空
            if (username.getText().length() == 0 || password.getText().length() == 0 || repassword.getText().length() == 0) {
                showJDialog("用户名和密码不能为空");
                return;
            }
            //2.判断两次密码输入是否一致
            if (!password.getText().equals(repassword.getText())) {
                showJDialog("两次密码输入不一致");
                return;
            }
            //3.判断用户名和密码的格式是否正确
            if (!username.getText().matches("[a-zA-Z0-9]{4,16}")) {
                showJDialog("用户名不符合规则");
                return;
            }
            if (!password.getText().matches("\\S*(?=\\S{6,})(?=\\S*\\d)(?=\\S*[a-z])\\S*")) {
                showJDialog("密码不符合规则，至少包含1个小写字母，1个数字，长度至少6位");
                return;
            }
            //4.判断用户名是否已经重复
            if (containsUsername(username.getText())) {
                showJDialog("用户名已经存在，请重新输入");
                return;
            }
            //5.添加用户
            allUser.add(new User(username.getText(), password.getText()));
            //6.写入文件
            FileUtil.writeLines(allUser, "userinfo.txt", "UTF-8");
            //7.提示注册成功
            showJDialog("注册成功");
            //关闭注册界面，打开登录界面
            this.setVisible(false);
            new LoginJframe();

        } else if (e.getSource() == reset) {
            //点击了重置按钮
            //清空输入框
            username.setText("");
            password.setText("");
            repassword.setText("");
        }
    }

    /*
     * 作用：
     *       判断username在集合中是否存在
     * 参数：
     *       用户名
     * 返回值：
     *       true：存在
     *       false：不存在
     *
     * */
    public boolean containsUsername(String username){
        for (User u : allUser) {
            if(u.getName().equals(username)){
                return true;
            }
        }
        return false;
    }



    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //只创建一个弹框对象
    JDialog jDialog = new JDialog();

    //因为展示弹框的代码，会被运行多次
    //所以，我们把展示弹框的代码，抽取到一个方法中。以后用到的时候，就不需要写了
    //直接调用就可以了。
    public void showJDialog(String content) {
        if (!jDialog.isVisible()) {
            //把弹框中原来的文字给清空掉。
            jDialog.getContentPane().removeAll();
            JLabel jLabel = new JLabel(content);
            jLabel.setBounds(0, 0, 200, 150);
            jDialog.add(jLabel);
            //给弹框设置大小
            jDialog.setSize(200, 150);
            //要把弹框在设置为顶层 -- 置顶效果
            jDialog.setAlwaysOnTop(true);
            //要让jDialog居中
            jDialog.setLocationRelativeTo(null);
            //让弹框
            jDialog.setModal(true);
            //让jDialog显示出来
            jDialog.setVisible(true);
        }
    }
}

