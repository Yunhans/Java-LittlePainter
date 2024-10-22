import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class mainFrame extends JFrame implements ItemListener, ActionListener{
    public JComboBox<String> toolBox;
    public JRadioButton[] rdb;
    public JCheckBox fillCheck;
    public JButton frontBtn, backBtn, clearBtn, eraserBtn;
    public canvasPanel canvas;
    public static JLabel mouselocateLabel;

    static String[] tools = {"筆刷","直線","橢圓形","矩形","圓⾓矩形"};
    public static String toolselect = tools[0];
    public static int paintsize = 4;
    public static boolean fillcheck = false;
    public static Color forecolor = Color.BLACK;
    public static Color backcolor = Color.black;
    public static boolean clear = false;

    public mainFrame(){
        setTitle("小畫家");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750,600);
        setLocationRelativeTo(null);
        setResizable(false); // 設定無法改變視窗大小
        setLayout(new BorderLayout());

        // 上方功能列
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        add(menuPanel, BorderLayout.NORTH);

        // 繪圖工具欄 (2,1)
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new GridLayout(2,1));
        menuPanel.add(toolPanel);

        // 筆刷大小欄 (2,1)
        JPanel brushPanel = new JPanel();
        brushPanel.setLayout(new GridLayout(2,1,0,2));
        menuPanel.add(brushPanel);

        // 填滿工具欄 (2,1)
        JPanel fillPanel = new JPanel();
        fillPanel.setLayout(new GridLayout(2,1,2,2));
        menuPanel.add(fillPanel);

        // 繪圖工具 標籤
        JLabel toolLabel = new JLabel(" 繪圖工具");
        toolPanel.add(toolLabel);

        // 筆刷大小 標籤
        JLabel brushLabel = new JLabel(" 筆刷大小");
        brushPanel.add(brushLabel);

        // 填滿 標籤
        JLabel fillLabel = new JLabel("填滿");
        fillPanel.add(fillLabel);

        // 繪圖工具 選單
        toolBox = new JComboBox<>(tools);
        toolBox.addItemListener(this);
        toolPanel.add(toolBox);

        // 筆刷大小 單選按鈕＋欄
        JPanel brushSizePanel = new JPanel();
        brushSizePanel.setLayout(new GridLayout(1,3));
        brushPanel.add(brushSizePanel);
        ButtonGroup brushSize = new ButtonGroup();
		rdb = new JRadioButton[3];
		rdb[0] = new JRadioButton("大", false);
        rdb[1] = new JRadioButton("中", false);
		rdb[2] = new JRadioButton("小", false);
        for(int i=0; i<3; i++){
            rdb[i].addActionListener(this);
            brushSize.add(rdb[i]);
            brushSizePanel.add(rdb[i]);
        }

        // 填滿 選擇方塊
        fillCheck = new JCheckBox("", false);
        fillCheck.setEnabled(false); // 預設為無法選取因預設繪圖工具是筆刷
        fillCheck.addActionListener(this);
        fillPanel.add(fillCheck);

        // 前景色 按鈕
        frontBtn = new JButton("前景色");
        frontBtn.setPreferredSize(new Dimension(90, 51));
        frontBtn.addActionListener(this);
        menuPanel.add(frontBtn);
        
        // 背景色 按鈕
        backBtn = new JButton("背景色");
        backBtn.setPreferredSize(new Dimension(90, 51));
        backBtn.addActionListener(this);
        menuPanel.add(backBtn);

        // 清除畫面 按鈕
        clearBtn = new JButton("清除畫面");
        clearBtn.setPreferredSize(new Dimension(103, 51));
        clearBtn.addActionListener(this);
        menuPanel.add(clearBtn);

        // 橡皮擦 按鈕
        eraserBtn = new JButton("橡皮擦");
        eraserBtn.setPreferredSize(new Dimension(90, 51));
        eraserBtn.addActionListener(this);
        menuPanel.add(eraserBtn);

        // 畫布 
        canvas = new canvasPanel();
        add(canvas, BorderLayout.CENTER);
        
        // 下方資訊列
        JPanel downInfoPanel = new JPanel();
        downInfoPanel.setLayout(new GridLayout(1,2));
        add(downInfoPanel, BorderLayout.SOUTH);

        // 滑鼠位置資訊欄 左下
        JPanel mouselocatePanel = new JPanel();
        mouselocateLabel = new JLabel("  游標位置：(%d,%d)");
        mouselocatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mouselocatePanel.add(mouselocateLabel);
        downInfoPanel.add(mouselocatePanel);
        
        // 開發者資訊欄 右下
        JPanel showDevloperPanel = new JPanel();
        JLabel showDevloperLabel = new JLabel("中央資管110石昀翰  ");
        showDevloperPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        showDevloperPanel.add(showDevloperLabel);
        downInfoPanel.add(showDevloperPanel);

        setVisible(true);
    }

    public void itemStateChanged(ItemEvent e){
        if(e.getStateChange() == ItemEvent.SELECTED){ // 被選中的而不是被取消選取的按鈕觸發
            fillCheck.setEnabled( (e.getItem()=="筆刷") ? false : true); // 如果繪圖工具是畫筆就不能選填滿，反之則可
            if(e.getItem()=="筆刷"){
                System.out.println("選擇筆刷");
                toolselect = tools[0];
            }else if(e.getItem()=="直線"){
                System.out.println("選擇直線");
                toolselect = tools[1];
            }else if(e.getItem()=="橢圓形"){
                System.out.println("選擇橢圓形");
                toolselect = tools[2];
            }else if(e.getItem()=="矩形"){
                System.out.println("選擇矩形");
                toolselect = tools[3];
            }else if(e.getItem()=="圓角矩形"){
                System.out.println("選擇圓角矩形");
                toolselect = tools[4];
            }
        }
    }

    public void actionPerformed(ActionEvent e){

        // 3種筆刷大小
        if(e.getSource()==rdb[0]){
            System.out.println("選擇 大 筆刷");
            paintsize = 7;
        }else if(e.getSource()==rdb[1]){
            System.out.println("選擇 中 筆刷");
            paintsize = 4;
        }else if(e.getSource()==rdb[2]){
            System.out.println("選擇 小 筆刷");
            paintsize = 1;
        }

        // 填滿
        if(e.getSource()==fillCheck){
            if(fillCheck.isSelected()){
                System.out.println("選擇填滿");
                fillcheck = true;
            }else{
                System.out.println("取消填滿");
                fillcheck = false;
            }
        }

        // 四個按鈕
        if(e.getSource()==frontBtn){
            System.out.println("點選 前景色");
            forecolor = JColorChooser.showDialog(mainFrame.this, "選擇顏色", forecolor);
        }
        if(e.getSource()==backBtn){
            System.out.println("點選 背景色");
            backcolor = JColorChooser.showDialog(mainFrame.this, "選擇顏色", backcolor);
        }
        if(e.getSource()==clearBtn){
            System.out.println("點選 清除畫面");
            canvas.clear();
        }
        if(e.getSource()==eraserBtn){
            System.out.println("點選 橡皮擦");
            forecolor = Color.WHITE;
            backcolor = Color.WHITE;
        }
        
    }
}

