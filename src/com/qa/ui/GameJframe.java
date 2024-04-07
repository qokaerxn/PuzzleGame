package com.qa.ui;

import cn.hutool.core.io.IoUtil;
import domain.GameInfo;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Random;

//下面的代码是关于游戏主界面的内容
public class GameJframe extends JFrame implements KeyListener, ActionListener {

    int[][] data = new int[4][4];

    int x = 0;
    int y = 0;

    //C:\Users\86155\Java\java_pro_loc\PuzzleGame\image\animal\animal1\1.jpg
    String path = "image\\animal\\animal1\\";

    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0},
    };

    int step = 0;

    //更换图片相关的条目
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem sport = new JMenuItem("运动");

    //<功能>按钮相关条目
    JMenuItem restartGame = new JMenuItem("重启游戏");
    JMenuItem reRrgister = new JMenuItem("重新登陆");
    JMenuItem closeGmae = new JMenuItem("关闭游戏");
    JMenuItem Gongzhonghao = new JMenuItem("公众号");

    JMenu saveJMenu = new JMenu("存档");
    JMenu loadJMenu = new JMenu("读档");

    JMenuItem saveItem0 = new JMenuItem("存档0(空)");
    JMenuItem saveItem1 = new JMenuItem("存档1(空)");
    JMenuItem saveItem2 = new JMenuItem("存档2(空)");
    JMenuItem saveItem3 = new JMenuItem("存档3(空)");
    JMenuItem saveItem4 = new JMenuItem("存档4(空)");

    JMenuItem loadItem0 = new JMenuItem("读档0(空)");
    JMenuItem loadItem1 = new JMenuItem("读档1(空)");
    JMenuItem loadItem2 = new JMenuItem("读档2(空)");
    JMenuItem loadItem3 = new JMenuItem("读档3(空)");
    JMenuItem loadItem4 = new JMenuItem("读档4(空)");

    Random r = new Random();


    public GameJframe() {

        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据
        initData();

        //初始化图片（根据初始化后的数据）
        initImage();

        //设置成窗口可见
        this.setVisible(true);

    }


    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,};
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    private void initImage() {

        //清空原本已经出现的所有图片
        this.getContentPane().removeAll();

        if (victory()) {
            ImageIcon winpng = new ImageIcon("image\\win.png");
            JLabel jLabel3 = new JLabel(winpng);
            jLabel3.setBounds(203,283,197,73);
            this.getContentPane().add(jLabel3);
        }

        //显示计步器
        JLabel stepCount = new JLabel("步数："+ step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);

        //循环加载图片
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = data[i][j];
                //创建一个ImageIcon的对象
                ImageIcon icon = new ImageIcon(path + num + ".jpg");

                //创建一个JLable的对象（管理容器）
                JLabel jLabel = new JLabel(icon);

                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);

                //设置图片凹或者凸
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));

                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);
            }
        }
        //设置背景
        ImageIcon background = new ImageIcon("image\\background.png");
        JLabel jLabel2 = new JLabel(background);
        jLabel2.setBounds(40, 40, 508, 560);
        //把背景图片添加到界面当中
        this.getContentPane().add(jLabel2);

        //刷新页面
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        //初始化菜单
        //创建整个菜单对象
        JMenuBar jMenuBar = new JMenuBar();

        //菜单上的两个选项对象（功能 关于我们）
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutusJMenu = new JMenu("关于我们");

        //创建更换图片选项对象
        JMenu changeImage = new JMenu("更换图片");

        //把5个存档，添加到saveJMenu中
        saveJMenu.add(saveItem0);
        saveJMenu.add(saveItem1);
        saveJMenu.add(saveItem2);
        saveJMenu.add(saveItem3);
        saveJMenu.add(saveItem4);

        //把5个读档，添加到loadJMenu中
        loadJMenu.add(loadItem0);
        loadJMenu.add(loadItem1);
        loadJMenu.add(loadItem2);
        loadJMenu.add(loadItem3);
        loadJMenu.add(loadItem4);


        //将与更换照片相关的条目添加到条目<更换条目>
        changeImage.add(girl);
        changeImage.add(sport);
        changeImage.add(animal);

        //将条目对象添加到相应选项对象当中去
        functionJMenu.add(changeImage);
        functionJMenu.add(restartGame);
        functionJMenu.add(reRrgister);
        functionJMenu.add(closeGmae);
        functionJMenu.add(saveJMenu);
        functionJMenu.add(loadJMenu);

        //<关于我们>的功能增加条目公众号
        aboutusJMenu.add(Gongzhonghao);

        //给条目绑定事件
        changeImage.addActionListener(this);
        restartGame.addActionListener(this);
        reRrgister.addActionListener(this);
        closeGmae.addActionListener(this);

        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);


        Gongzhonghao.addActionListener(this);
        saveItem0.addActionListener(this);
        saveItem1.addActionListener(this);
        saveItem2.addActionListener(this);
        saveItem3.addActionListener(this);
        saveItem4.addActionListener(this);
        loadItem0.addActionListener(this);
        loadItem1.addActionListener(this);
        loadItem2.addActionListener(this);
        loadItem3.addActionListener(this);
        loadItem4.addActionListener(this);

        //将选项对象添加到菜单中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutusJMenu);
        
        //读取存档信息
        getGameInfo();

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void getGameInfo() {
        //1.创建File对象表示所有存档所在的文件夹
        File file = new File("save");
        //2.进入文件夹获取到里面所有的存档文件
        File[] files = file.listFiles();
        //3.遍历数组，得到每一个存档
        for (File f : files) {
            //f ：依次表示每一个存档文件
            //获取每一个存档文件中的步数
            GameInfo gi = null;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                 gi = (GameInfo)ois.readObject();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //获取到了步数
            int step = gi.getStep();

            //把存档的步数同步到菜单当中
            //save0 ---> 0
            //save1 ---> 1
            //...

            //获取存档的文件名 save0.data
            String name = f.getName();
            //获取当存档的序号（索引）
            int index = name.charAt(4) - '0';
            //修改菜单上所表示的文字信息
            saveJMenu.getItem(index).setText("存档" + index + "(" + step + ")步");
            loadJMenu.getItem(index).setText("存档" + index + "(" + step + ")步");
        }
    }

    private void initJFrame() {
        //页面的基本内容，其他两个窗口类似
        //设置长和宽
        this.setSize(603, 680);
        //设置标题
        this.setTitle("qa的拼图小游戏 V 1.0");
        //置顶
        this.setAlwaysOnTop(true);
        //窗口最开始的位置
        this.setLocationRelativeTo(null);
        //设置退出方式
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //取消默认居中放置，只有取消了才会按照XY轴的形式添加组件
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) {
            //把界面中所有的图片全部删除
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all = new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);

            //加载背景图片
            //添加背景图片
            ImageIcon background = new ImageIcon("image\\background.png");
            JLabel jLabel2 = new JLabel(background);
            jLabel2.setBounds(40, 40, 508, 560);

            //把背景图片添加到界面当中
            this.getContentPane().add(jLabel2);

            //刷新界面
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利，如果胜利，此方法需要直接结束，不能再执行下面的移动代码了
        if (victory()) {
            return;
        }

        //获取键盘按得是那个按钮
        int code = e.getKeyCode();

        //对上下左右进行判断
        //左：37 上：38 右：39 下40
        if (code == 37) {
            if (y == 3) {
                return;
            }
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            initImage();
        } else if (code == 38) {
            if (x == 3) {
                return;
            }
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
            initImage();
        } else if (code == 39) {
            if (y == 0) {
                return;
            }
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            initImage();
        } else if (code == 40) {
            if (x == 0) {
                return;
            }
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
            initImage();
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 16},
            };
            initImage();
        }
    }

    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取被点击的对象
        Object obj = e.getSource();
        if(obj==restartGame){
            //计步器清零
            step = 0;
            //在此打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        } else if (obj == reRrgister) {
            //关闭当前游戏界面
            this.setVisible(false);
            //打开登录界面
            new LoginJframe();
        } else if (obj == girl) {
            step = 0;
            //生成一个1到13的随机数增加到路径后面
            int r1 = r.nextInt(13)+1;
            char randomchar = (char)(r1+'0');
            path = "image\\girl\\girl"+randomchar+"\\";
            initData();
            initImage();
        } else if (obj == sport) {
            int r2 = r.nextInt(10)+1;
            char randomchar2 = (char)(r2+'0');
            path = "image\\sport\\sport"+randomchar2+"\\";
            initData();
            initImage();
        } else if (obj == animal) {
            int r3 = r.nextInt(8)+1;
            char randomchar3 = (char)(r3+'0');
            path = "image\\animal\\animal"+randomchar3+"\\";
            initData();
            initImage();
        } else if (obj == closeGmae) {
            System.exit(0);
        }else if(obj == Gongzhonghao){
            //先设置一个弹框
            JDialog Dialog = new JDialog();

            JLabel account = new JLabel(new ImageIcon("image\\acount.png"));
            account.setBounds(203,283,266,132);

            //
            Dialog.getContentPane().add(account);

            Dialog.setSize(344,344);

            //置顶
            Dialog.setAlwaysOnTop(true);

            //居中
            Dialog.setLocationRelativeTo(null);

            //弹框不关闭则不能执行其他操作
            Dialog.setModal(true);

            Dialog.setVisible(true);

        }else if (obj == saveItem0 || obj == saveItem1 || obj == saveItem2 || obj == saveItem3 || obj == saveItem4) {
            //获取当前是哪个存档被点击了，获取其中的序号
            JMenuItem item = (JMenuItem) obj;
            String str = item.getText();
            int index = str.charAt(2) - '0';

            //直接把游戏的数据写到本地文件中
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save\\save" + index + ".data"));
                GameInfo gi = new GameInfo(data, x, y, path, step);
                IoUtil.writeObj(oos, true, gi);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            //修改一下存档item上的展示信息
            //存档0(XX步)
            item.setText("存档" + index + "(" + step + "步)");
            //修改一下读档item上的展示信息
            loadJMenu.getItem(index).setText("存档" + index + "(" + step + "步)");
        } else if (obj == loadItem0 || obj == loadItem1 || obj == loadItem2 || obj == loadItem3 || obj == loadItem4) {
            //获取当前是哪个读档被点击了，获取其中的序号
            JMenuItem item = (JMenuItem) obj;
            String str = item.getText();
            int index = str.charAt(2) - '0';

            GameInfo gi = null;
            try {
                //可以到本地文件中读取数据
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save\\save" + index + ".data"));
                gi = (GameInfo)ois.readObject();
                ois.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }

            data = gi.getData();
            path = gi.getPath();
            step = gi.getStep();
            x = gi.getX();
            y = gi.getY();

            //重新刷新界面加载游戏
            initImage();

        }
    }
}
