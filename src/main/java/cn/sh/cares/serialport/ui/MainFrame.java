package cn.sh.cares.serialport.ui;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import cn.sh.cares.serialport.manager.SerialPortManager;
import cn.sh.cares.serialport.model.ComboItem;
import cn.sh.cares.serialport.utils.ByteUtils;
import cn.sh.cares.serialport.utils.FileUtils;
import cn.sh.cares.serialport.utils.ShowUtils;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主界面
 *
 */
@SuppressWarnings("all")
public class MainFrame extends JFrame {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 程序界面宽度
    public final int WIDTH = 530;
    // 程序界面高度
    public final int HEIGHT = 495;

    // 数据显示区
    private JTextArea mDataView = new JTextArea();
    private JScrollPane mScrollDataView = new JScrollPane(mDataView);

    // 串口设置面板
    private JPanel mSerialPortPanel = new JPanel();
    private JLabel mSerialPortLabel = new JLabel("串口");
    private JLabel mBaudrateLabel = new JLabel("波特率");
    private JLabel mParityLabel = new JLabel("校验位");
    private JLabel mDataBitLabel = new JLabel("数据位");
    private JLabel mStopBitLabel = new JLabel("停止位");
    private JComboBox mCommChoice = new JComboBox();
    private JTextField mBaudrateChoice = new JTextField();
    private ButtonGroup mDataChoice = new ButtonGroup();
    private JRadioButton mDataASCIIChoice = new JRadioButton("ASCII", true);
    private JRadioButton mDataHexChoice = new JRadioButton("Hex");

    // 数据位
    private JComboBox mDataBitChoice = new JComboBox();

    // 校验位
    private JComboBox mParityBitChoice = new JComboBox();
    // 停止位

    private JComboBox mStopBitChoice = new JComboBox();


    // 操作面板
    private JPanel mOperatePanel = new JPanel();
    private JTextArea mDataInput = new JTextArea();
    private JButton mSerialPortOperate = new JButton("打开串口");
    private JButton mSendData = new JButton("发送数据");

    // 串口列表
    private List<String> mCommList = null;
    // 串口对象
    private SerialPort mSerialport;

    public MainFrame() {
        initView();
        initComponents();
        actionListener();
        initData();
    }

    /**
     * 初始化窗口
     */
    private void initView() {
        // 关闭程序
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // 禁止窗口最大化
        setResizable(false);

        // 设置程序窗口居中显示
        Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        setBounds(p.x - WIDTH / 2, p.y - HEIGHT / 2, WIDTH, HEIGHT);
        this.setLayout(null);

        setTitle("串口通信");
    }

    /**
     * 初始化控件
     */
    private void initComponents() {
        // 数据显示
        mDataView.setFocusable(false);
        mScrollDataView.setBounds(10, 10, 505, 200);
        add(mScrollDataView);

        // 串口设置
        mSerialPortPanel.setBorder(BorderFactory.createTitledBorder("串口设置"));
        mSerialPortPanel.setBounds(10, 220, 170, 235);
        mSerialPortPanel.setLayout(null);
        add(mSerialPortPanel);

        mSerialPortLabel.setForeground(Color.gray);
        mSerialPortLabel.setBounds(10, 25, 40, 20);
        mSerialPortPanel.add(mSerialPortLabel);

        mCommChoice.setFocusable(false);
        mCommChoice.setBounds(60, 25, 100, 20);
        mSerialPortPanel.add(mCommChoice);

        mBaudrateLabel.setForeground(Color.gray);
        mBaudrateLabel.setBounds(10, 60, 40, 20);
        mSerialPortPanel.add(mBaudrateLabel);

        mBaudrateChoice.setEnabled(true);
        mBaudrateChoice.setBounds(60, 60, 100, 20);
        mBaudrateChoice.setText("600");
        mSerialPortPanel.add(mBaudrateChoice);


        mParityLabel.setForeground(Color.gray);
        mParityLabel.setBounds(10, 95, 40, 20);
        mSerialPortPanel.add(mParityLabel);


        mParityBitChoice.addItem(new ComboItem("EVEN",SerialPort.PARITY_EVEN));
        mParityBitChoice.addItem(new ComboItem("MARK",SerialPort.PARITY_MARK));
        mParityBitChoice.addItem(new ComboItem("NONE",SerialPort.PARITY_NONE));
        mParityBitChoice.addItem(new ComboItem("ODD",SerialPort.PARITY_ODD));
        mParityBitChoice.addItem(new ComboItem("SPACE",SerialPort.PARITY_SPACE));
        mParityBitChoice.setFocusable(false);
        mParityBitChoice.setBounds(60, 95, 100, 20);
        mSerialPortPanel.add(mParityBitChoice);


        mDataBitLabel.setForeground(Color.gray);
        mDataBitLabel.setBounds(10, 130, 40, 20);
        mSerialPortPanel.add(mDataBitLabel);
        mDataBitChoice.addItem(new ComboItem("5",SerialPort.DATABITS_5));
        mDataBitChoice.addItem(new ComboItem("6",SerialPort.DATABITS_6));
        mDataBitChoice.addItem(new ComboItem("7",SerialPort.DATABITS_7));
        mDataBitChoice.addItem(new ComboItem("8",SerialPort.DATABITS_8));
        mDataBitChoice.setFocusable(false);
        mDataBitChoice.setBounds(60, 130, 100, 20);
        mSerialPortPanel.add(mDataBitChoice);

        mStopBitLabel.setForeground(Color.gray);
        mStopBitLabel.setBounds(10, 165, 40, 20);
        mSerialPortPanel.add(mStopBitLabel);
        mStopBitChoice.addItem(new ComboItem("1",SerialPort.STOPBITS_1));
        mStopBitChoice.addItem(new ComboItem("2",SerialPort.STOPBITS_2));
        mStopBitChoice.addItem(new ComboItem("1-5",SerialPort.STOPBITS_1_5));
        mStopBitChoice.setFocusable(false);
        mStopBitChoice.setBounds(60, 165, 100, 20);
        mSerialPortPanel.add(mStopBitChoice);


        mDataASCIIChoice.setBounds(20, 200, 55, 20);
        mDataHexChoice.setBounds(95, 200, 55, 20);
        mDataChoice.add(mDataASCIIChoice);
        mDataChoice.add(mDataHexChoice);
        mSerialPortPanel.add(mDataASCIIChoice);
        mSerialPortPanel.add(mDataHexChoice);


        // 操作
        mOperatePanel.setBorder(BorderFactory.createTitledBorder("操作"));
        mOperatePanel.setBounds(200, 220, 315, 235);
        mOperatePanel.setLayout(null);
        add(mOperatePanel);

        mDataInput.setBounds(25, 25, 265, 120);
        mDataInput.setLineWrap(true);
        mDataInput.setWrapStyleWord(true);
        mOperatePanel.add(mDataInput);

        mSerialPortOperate.setFocusable(false);
        mSerialPortOperate.setBounds(45, 195, 90, 20);
        mOperatePanel.add(mSerialPortOperate);

        mSendData.setFocusable(false);
        mSendData.setBounds(180, 195, 90, 20);
        mOperatePanel.add(mSendData);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mCommList = SerialPortManager.findPorts();
        // 检查是否有可用串口，有则加入选项中
        if (mCommList == null || mCommList.size() < 1) {
            ShowUtils.warningMessage("没有搜索到有效串口！");
        } else {
            for (String s : mCommList) {
                mCommChoice.addItem(s);
            }
        }

    }

    /**
     * 按钮监听事件
     */
    private void actionListener() {
        // 串口
        mCommChoice.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                mCommList = SerialPortManager.findPorts();
                // 检查是否有可用串口，有则加入选项中
                if (mCommList == null || mCommList.size() < 1) {
                    ShowUtils.warningMessage("没有搜索到有效串口！");
                } else {
                    int index = mCommChoice.getSelectedIndex();
                    mCommChoice.removeAllItems();
                    for (String s : mCommList) {
                        mCommChoice.addItem(s);
                    }
                    mCommChoice.setSelectedIndex(index);
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // NO OP
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // NO OP
            }
        });

        // 打开|关闭串口
        mSerialPortOperate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if ("打开串口".equals(mSerialPortOperate.getText()) && mSerialport == null) {
                    openSerialPort(e);
                } else {
                    closeSerialPort(e);
                }
            }
        });

        // 发送数据
        mSendData.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sendData(e);
            }
        });
    }

    /**
     * 打开串口
     *
     * @param evt 点击事件
     */
    private void openSerialPort(ActionEvent evt) {
        // 获取串口名称
        String commName = (String) mCommChoice.getSelectedItem();
        // 获取波特率，默认为9600
        int baudrate = 9600;

        // 获取停止位
        int stop = (int) ((ComboItem)mStopBitChoice.getSelectedItem()).getValue();

        // 获取数据位
        int data = (int) ((ComboItem)mDataBitChoice.getSelectedItem()).getValue();

        // 获取校验位
        int parity = (int) ((ComboItem)mParityBitChoice.getSelectedItem()).getValue();

        logger.info("串口=[{}],波特率=[{}],校验位=[{}],数据位=[{}],停止位=[{}],",commName,baudrate,parity,data,stop);

        String bps = (String) mBaudrateChoice.getText();
        try {
            baudrate = Integer.parseInt(bps);
        } catch (Exception e) {
            ShowUtils.warningMessage("波特率必须为正整数！");
            mBaudrateChoice.setText("");
            return;
        }

        logger.info("串口=[{}],波特率=[{}],校验位=[{}],数据位=[{}],停止位=[{}],",commName,baudrate,parity,data,stop);

        // 检查串口名称是否获取正确
        if (commName == null || commName.equals("")) {
            ShowUtils.warningMessage("没有搜索到有效串口！");
            return;
        } else {
            try {
                mSerialport = SerialPortManager.openPort(commName, baudrate,parity,data,stop);
                if (mSerialport != null) {
                    mDataView.setText("串口已打开" + "\r\n");
                    mSerialPortOperate.setText("关闭串口");
                }
            } catch (PortInUseException e) {
                ShowUtils.warningMessage("串口已被占用！");
            }
        }

        // 添加串口监听
        SerialPortManager.addListener(mSerialport, new SerialPortManager.DataAvailableListener() {

            @Override
            public void dataAvailable() {
                byte[] data = null;
                try {
                    if (mSerialport == null) {
                        ShowUtils.errorMessage("串口对象为空，监听失败！");
                    } else {
                        // 读取串口数据
                        data = SerialPortManager.readFromPort(mSerialport);

                        // 以字符串的形式接收数据
                        if (mDataASCIIChoice.isSelected()) {
                            String recv = new String(data);
                            mDataView.append(recv);
                            FileUtils.writeFile(recv);
                        }

                        // 以十六进制的形式接收数据
                        if (mDataHexChoice.isSelected()) {
                            mDataView.append(ByteUtils.byteArrayToHexString(data) + "\r\n");
                        }
                    }
                } catch (Exception e) {
                    ShowUtils.errorMessage(e.toString());
                    // 发生读取错误时显示错误信息后退出系统
                    System.exit(0);
                }
            }
        });
    }

    /**
     * 关闭串口
     *
     * @param evt 点击事件
     */
    private void closeSerialPort(ActionEvent evt) {
        SerialPortManager.closePort(mSerialport);
        mDataView.setText("串口已关闭" + "\r\n");
        mSerialPortOperate.setText("打开串口");
        mSerialport = null;
    }

    /**
     * 发送数据
     *
     * @param evt 点击事件
     */
    private void sendData(ActionEvent evt) {
        // 待发送数据
        String data = mDataInput.getText().toString();

        if (mSerialport == null) {
            ShowUtils.warningMessage("请先打开串口！");
            return;
        }

        if ("".equals(data) || data == null) {
            ShowUtils.warningMessage("请输入要发送的数据！");
            return;
        }

        // 以字符串的形式发送数据
        if (mDataASCIIChoice.isSelected()) {
            SerialPortManager.sendToPort(mSerialport, data.getBytes());
        }

        // 以十六进制的形式发送数据
        if (mDataHexChoice.isSelected()) {
            SerialPortManager.sendToPort(mSerialport, ByteUtils.hexStr2Byte(data));
        }
    }

    public static void main(String args[]) {

    }
}