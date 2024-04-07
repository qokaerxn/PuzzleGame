package com.qa.ui;

import cn.hutool.core.io.FileUtil;
import domain.User;
import utile.CodeUtil;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LoginJframe extends JFrame implements MouseListener {

    ArrayList<User> allUser = new ArrayList<>();
    JButton login = new JButton();

    JButton register = new JButton();
    JTextField username = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JTextField code = new JTextField();
    JLabel rightCode = new JLabel();

    public LoginJframe() {

        //1.读取本地文件中的用户信息
        readUserInfo();
        //初始化界面
        initJFrame();

        //交互窗口
        initView();

        //让当前界面可见
        this.setVisible(true);

    }

    private void readUserInfo() {
        //1.读取数据
        List<String> userInfoList = FileUtil.readUtf8Lines("C:\\Users\\86155\\Java\\java_pro_loc\\PuzzleGame\\userinfo.txt");
        //2.遍历集合
        for (String string : userInfoList) {
            String[] userInfo = string.split("&");
            String username = userInfo[0].split("=")[1];
            String password = userInfo[1].split("=")[1];
            allUser.add(new User(username,password));
        }
    }

    public void initView() {
        //1.创建用户名输入图片
        JLabel usernameText = new JLabel(new ImageIcon("image\\login\\用户名.png"));
        usernameText.setBounds(116, 135, 47, 17);
        getContentPane().add(usernameText);

        //2.创建用户名输入框
        username.setBounds(195, 134, 200, 30);
        getContentPane().add(username);

        //3.创建用户名密码输入图片
        JLabel passwordText = new JLabel(new ImageIcon("image\\login\\密码.png"));
        passwordText.setBounds(130, 195, 32, 16);
        getContentPane().add(passwordText);

        //4.创建用户密码输入框
        passwordField.setBounds(195, 195, 200, 30);
        getContentPane().add(passwordField);

        //验证码提示
        JLabel codeText = new JLabel(new ImageIcon("image\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);

        //验证码的输入框
        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);

        //生成验证码
        String codestr = CodeUtil.getCode();
        //将要输入的验证码显示出来
        rightCode.setText(codestr);
        //添加鼠标事件
        rightCode.addMouseListener(this);
        //位置和宽高
        rightCode.setBounds(300, 256, 50, 30);
        //添加到界面
        this.getContentPane().add(rightCode);

        //5.添加用户登录按钮
        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        //去除添加按钮边框
        login.setBorderPainted(false);
        //去除按钮的背景
        login.setContentAreaFilled(false);
        //给登录按钮添加鼠标事件
        login.addMouseListener(this);
        this.getContentPane().add(login);

        //6.添加用户注册按钮
        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon("image\\login\\注册按钮.png"));

        //去除按钮的边框
        register.setBorderPainted(false);
        //去除按钮的背景
        register.setContentAreaFilled(false);

        //给注册按钮添加鼠标事件
        register.addMouseListener(this);
        this.getContentPane().add(register);

        //7.添加背景图片
        JLabel background = new JLabel(new ImageIcon("image\\login\\background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);

    }

    private void initJFrame() {
        //设置登陆界面的长和宽
        this.setSize(488, 430);
        //设置标题
        this.setTitle("qa的拼图小游戏登录界面V 1.0");
        //置顶
        this.setAlwaysOnTop(true);
        //窗口最开始的位置(居中)
        this.setLocationRelativeTo(null);
        //退出方式
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //取消默认布局
        this.setLayout(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == login) {

            //获取用户输入的用户名和密码
            String usernameInput = username.getText();
            String passwordInput = passwordField.getText();

            //获取用户输入的验证码
            String codeInput = code.getText();

            User userinfo = new User(usernameInput, passwordInput);

            if (codeInput.length() == 0) {
                showJDialog("验证码不能为空");
            } else if (usernameInput.length() == 0 || passwordInput.length() == 0) {
                showJDialog("用户名或者密码为空");
            } else if (!codeInput.equalsIgnoreCase(rightCode.getText())) {
                showJDialog("验证码输入错误");
            } else if (contains(userinfo)) {
                //判断用户名信息是否正确，正确则关闭该界面
                //关闭当前界面
                this.setVisible(false);
                //打开游戏界面
                new GameJframe();
            } else {
                showJDialog("用户名或密码错误");
            }

        } else if (e.getSource() == register) {
            //关闭当前的登录界面
            this.setVisible(false);
            //打开注册界面
            new RegisterJframe(allUser);
        }
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

    public void showJDialog(String content) {
        //创建一个弹框对象
        JDialog jDialog = new JDialog();
        //给弹框设置大小
        jDialog.setSize(200, 150);
        //让弹框置顶
        jDialog.setAlwaysOnTop(true);
        //让弹框居中
        jDialog.setLocationRelativeTo(null);
        //弹框不关闭永远无法操作下面的界面
        jDialog.setModal(true);

        //创建Jlabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        //让弹框展示出来
        jDialog.setVisible(true);
    }

    public boolean contains(User userInput) {
        for (int i = 0; i < allUser.size(); i++) {
            User rightUser = allUser.get(i);
            if (userInput.getName().equals(rightUser.getName()) && userInput.getPassword().equals(rightUser.getPassword())) {
                //有相同的代表存在，返回true，后面的不需要再比了
                return true;
            }
        }
        //循环结束之后还没有找到就表示不存在
        return false;
    }
}


