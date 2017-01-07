import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

public enum Stone { 

    BLACK(Color.BLACK), WHITE(Color.WHITE), NONE(null);

    final Color color;

    private Stone(Color c) {
        color = c;
    }

    public void paint(Graphics g, int dimension) {
        if(this == NONE) return;
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        int x = 5;
        g2d.fillOval(0,0, dimension-x, dimension-x);
    }
}
