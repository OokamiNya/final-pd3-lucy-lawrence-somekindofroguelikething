import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class SwingPane extends JLayeredPane {
    private int gridHeight;
    private int gridWidth;
    private Dimension cellDim;
    private Dimension paneDim;
    private boolean[][] changed;
    private BufferedImage[][] tiles;
    private BufferedImage curimg;
    private TilesetFactory tilesetFact = new TilesetFactory();
    private Color defbgcol = Color.BLACK;
    private Color deffgcol = Color.WHITE;

    public void paintComponent(Graphics g) {
        g.drawImage(curimg, 0, 0, null);
        paintComponents(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public SwingPane(int gridWidth, int gridHeight, Font font) {
	this.gridWidth = gridWidth;
	this.gridHeight = gridHeight;
	tiles = new BufferedImage[gridWidth][gridHeight];
	changed = new boolean[gridWidth][gridHeight];
	
	for (int x=0; x<gridWidth; x++) {
	    for (int y=0; y<gridHeight; y++) {
		changed[x][y]=true;
	    }
	}

	tilesetFact.init(font);
	cellDim=tilesetFact.getCellDim();
	int w = gridWidth * cellDim.width;
	int h = gridHeight * cellDim.height;
	paneDim = new Dimension(w,h);
	setSize(paneDim);
	setMinimumSize(paneDim);
	setPreferredSize(paneDim);
	curimg = new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);
    }

    public void putChar(char c, int x, int y, Color fgcol, Color bgcol) {
	tiles[x][y] = tilesetFact.getImage(c,fgcol,bgcol);
	changed[x][y] = true;
    }

    public void putString(String s, int x, int y, Color fgcol, Color bgcol) {
	for (int i=0; i<s.length(); i++) {
	    if (x+i>=gridWidth) {break;}
	    putChar(s.charAt(i),x+i,y,fgcol,bgcol);
	}
    }

    public void putRectangle(char c, int x, int y, int w, int h, Color fgcol, Color bgcol) {
	for (int i=0; i<w; i++) {
	    putChar(c,x+i,y,fgcol,bgcol);
	    putChar(c,x+i,y+h-1,fgcol,bgcol);
	}
	for (int i=0; i<h; i++) {
	    putChar(c,x,y+i,fgcol,bgcol);
	    putChar(c,x+w-1,y+i,fgcol,bgcol);
	}
    }

    public void putString(String s, int x, int y) {putString(s,x,y,deffgcol,defbgcol);}

    public void clearRect(int x, int y, int w, int h) {
	for(int j=0; j<h; j++) {
	    for(int i=0; i<w; i++) {
		putChar(' ',x+i,y+j,Color.WHITE,Color.BLACK);
	    }
	}
    }

    public void refresh() {
	Graphics2D g = curimg.createGraphics();
	for (int x=0; x<gridWidth; x++) {
	    for (int y=0; y<gridHeight; y++) {
		if (changed[x][y]) {
		    g.setColor(defbgcol);
		    g.fillRect(x*cellDim.width, y*cellDim.height, 
			       cellDim.width, cellDim.height);
		    g.drawImage(tiles[x][y], null, 
				x*cellDim.width, y*cellDim.height);
		    changed[x][y]=false;
		}
	    }
	}
	repaint();
    }

    public Dimension getPaneDim() {return paneDim;} 
}
