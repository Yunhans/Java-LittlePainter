import java.awt.*;
public class line {
    public Point start, end;
    public BasicStroke thickness;
    public Color color;
    public line(Point start, Point end, BasicStroke thickness, Color color){
        this.start = start;
        this.end = end;
        this.thickness = thickness;
        this.color = color;
    }
}
