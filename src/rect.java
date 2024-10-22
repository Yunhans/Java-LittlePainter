import java.awt.*;
public class rect {
    public Point start, end;
    public BasicStroke thickness;
    public Color forecolor, backcolor;
    public int height, width;
    public boolean fillcheck;
    public rect(Point start, Point end, BasicStroke thickness, Color forecolor, Color backcolor, boolean fillcheck){
        this.start = start;
        this.end = end;
        this.thickness = thickness;
        this.forecolor = forecolor;
        this.backcolor = backcolor;
        this.fillcheck = fillcheck;
        this.height = Math.abs(start.y - end.y);
        this.width = Math.abs(start.x - end.x);

        if(end.x < start.x){
            this.start.x = end.x;
        }
        if(end.y < start.y){
            this.start.y = end.y;
        }
    }
}
