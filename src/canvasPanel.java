import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
public class canvasPanel extends JPanel implements MouseListener, MouseMotionListener{
    public Point tempPoint, pressedPoint; 
    public ArrayList<line> brushs = new ArrayList<>(); 
    public ArrayList<line> lines = new ArrayList<>();
    public ArrayList<oval> ovals = new ArrayList<>();
    public ArrayList<rect> rects = new ArrayList<>();

    public canvasPanel(){
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setBackground(Color.WHITE);
    }

    public void mouseEntered(MouseEvent e){}
    
    public void mouseExited(MouseEvent e){
        tempPoint = new Point();
        pressedPoint = new Point();
        mainFrame.mouselocateLabel.setText("  游標位置：超出畫布範圍");
    }

    public void mouseClicked(MouseEvent e){
        tempPoint = e.getPoint();
    }

    public void mouseDragged(MouseEvent e){
        switch(mainFrame.toolselect){
            case "筆刷":
                brushs.add(new line(tempPoint, e.getPoint(), new BasicStroke(mainFrame.paintsize), mainFrame.forecolor));
                break;
            case"直線":
            case"橢圓形":
            case"矩形":
            case"圓角矩形":
                tempPoint = e.getPoint();
                break;
        }
        repaint();
        tempPoint = e.getPoint();
        mainFrame.mouselocateLabel.setText(String.format("  游標位置：(%d,%d)", e.getX(), e.getY()));
    }

    public void mousePressed(MouseEvent e){
        pressedPoint = e.getPoint();
        mainFrame.mouselocateLabel.setText(String.format("  游標位置：(%d,%d)", e.getX(), e.getY()));
    }

    public void mouseReleased(MouseEvent e){
        switch(mainFrame.toolselect){
            case "筆刷":
                break;
            case"直線":
                if(mainFrame.fillcheck){
                    lines.add(new line(pressedPoint, e.getPoint(), new BasicStroke(mainFrame.paintsize), mainFrame.forecolor));
                }else{
                    lines.add(new line(pressedPoint,e.getPoint(),new BasicStroke(mainFrame.paintsize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0),mainFrame.forecolor));
                }
                break;
            case"橢圓形":
                ovals.add(new oval(pressedPoint, e.getPoint(), new BasicStroke(mainFrame.paintsize), mainFrame.forecolor, mainFrame.backcolor, mainFrame.fillcheck));
                break;
            case"矩形":
                rects.add(new rect(pressedPoint, e.getPoint(), new BasicStroke(mainFrame.paintsize), mainFrame.forecolor, mainFrame.backcolor, mainFrame.fillcheck));
                break;
            case"圓角矩形":
                break;
        }
    }

    public void mouseMoved(MouseEvent e){
        tempPoint = e.getPoint();
        mainFrame.mouselocateLabel.setText(String.format("  游標位置：(%d,%d)", e.getX(), e.getY()));
    }

    public void clear(){
        brushs.clear();
        lines.clear();
        ovals.clear();
        rects.clear();
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for(line brush: brushs){
            g2.setColor(brush.color);
            g2.setStroke(brush.thickness);
            g2.drawLine(brush.start.x, brush.start.y, brush.end.x, brush.end.y);
        }

        for(line line: lines){
            g2.setColor(line.color);
            g2.setStroke(line.thickness);
            g2.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
        }

        for(oval oval: ovals){
            if(oval.fillcheck){
                g2.setColor(oval.backcolor);
                g2.fillOval(oval.start.x, oval.start.y, oval.width, oval.height);
            }
            g2.setColor(oval.forecolor);
            g2.setStroke(oval.thickness);
            g2.drawOval(oval.start.x, oval.start.y, oval.width, oval.height);
        }

        for(rect rect: rects){
            if(rect.fillcheck){
                g2.setColor(rect.backcolor);
                g2.fillRect(rect.start.x, rect.start.y, rect.width, rect.height);
            }
            g2.setColor(rect.forecolor);
            g2.setStroke(rect.thickness);
            g2.drawRect(rect.start.x, rect.start.y, rect.width, rect.height);
        }

        switch(mainFrame.toolselect){
            case "筆刷":
                break;
            case"直線":
                g2.setColor(mainFrame.forecolor);
                if(mainFrame.fillcheck){
                    g2.setStroke(new BasicStroke(mainFrame.paintsize));
                }else{
                    g2.setStroke(new BasicStroke(mainFrame.paintsize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                }
                g2.drawLine(pressedPoint.x, pressedPoint.y, tempPoint.x, tempPoint.y);
                break;
            case"橢圓形":
                g2.setStroke(new BasicStroke(mainFrame.paintsize));
                if(mainFrame.fillcheck){
                    g2.setColor(mainFrame.backcolor);
                    g2.fillOval(Math.min(pressedPoint.x, tempPoint.x), Math.min(pressedPoint.y, tempPoint.y), Math.abs(pressedPoint.x - tempPoint.x), Math.abs(pressedPoint.y - tempPoint.y));
                }
                g2.setColor(mainFrame.forecolor);
                g2.drawOval(Math.min(pressedPoint.x, tempPoint.x), Math.min(pressedPoint.y, tempPoint.y), Math.abs(pressedPoint.x - tempPoint.x), Math.abs(pressedPoint.y - tempPoint.y));
                break;
            case"矩形":
                g2.setStroke(new BasicStroke(mainFrame.paintsize));
                if(mainFrame.fillcheck){
                    g2.setColor(mainFrame.backcolor);
                    g2.fillRect(Math.min(pressedPoint.x, tempPoint.x), Math.min(pressedPoint.y, tempPoint.y), Math.abs(pressedPoint.x - tempPoint.x), Math.abs(pressedPoint.y - tempPoint.y));
                }
                g2.setColor(mainFrame.forecolor);
                g2.drawRect(Math.min(pressedPoint.x, tempPoint.x), Math.min(pressedPoint.y, tempPoint.y), Math.abs(pressedPoint.x - tempPoint.x), Math.abs(pressedPoint.y - tempPoint.y));
                break;
            case"圓角矩形":
                break;
        }
    }
}
