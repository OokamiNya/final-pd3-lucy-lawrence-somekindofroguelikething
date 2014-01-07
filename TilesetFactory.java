import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
//import java.util.ArrayList;
import java.util.TreeMap;

import javax.imageio.*;
import java.io.*;

public class TilesetFactory {
    private Font font;
    private char[] allowed;
    private Dimension cellDim = new Dimension(1,1);
    private TreeMap<String,BufferedImage> tiles = 
	new TreeMap<String,BufferedImage>();
    
    public TilesetFactory() {
	//Useful printable ASCII codes: 126 - 32
	allowed = new char[126-32];
	for(char c=32; c<=125; c++) {
	    allowed[c-32]=c;
	}
    }

    public void clearTiles() {tiles.clear();}

    public void init(Font font) {
	this.font=font;
	clearTiles();
	calcCellDim();
	clearTiles();
    }

    private void calcCellDim() {
	int xmax = Integer.MIN_VALUE;
	int xmin = Integer.MAX_VALUE;
	int ymax = Integer.MIN_VALUE;
	int ymin = Integer.MAX_VALUE;
        
	BufferedImage img = new BufferedImage(cellDim.width, cellDim.height, 
					      BufferedImage.TYPE_4BYTE_ABGR);

	Graphics2D g = img.createGraphics();
	g.setFont(font);
	FontRenderContext frc = g.getFontRenderContext();

	for(int i=0; i<allowed.length; i++) {
	    GlyphVector vector = 
		font.createGlyphVector(frc,new char[]{allowed[i]});
	    Rectangle rect = vector.getGlyphPixelBounds(0,frc,0,0);

	    if (rect.x < xmin) {xmin=rect.x;}
	    if (rect.y < ymin) {ymin=rect.y;}
	    if (rect.x+rect.width > xmax) {xmax = rect.x+rect.width;}
	    if (rect.y+rect.height > ymax) {ymax = rect.y+rect.height;}
	}

	cellDim.width=(xmax-xmin);
	cellDim.height=(ymax-ymin);
    }

    public BufferedImage getImage(char c, Color fgcol, Color bgcol) {
	String s = c+":"+
	           Integer.toHexString(fgcol.getRGB())+":"+
	           Integer.toHexString(bgcol.getRGB());
	BufferedImage tile = tiles.get(s);
	if (tile==null) {
	    tile=makeImage(c,fgcol,bgcol);
	    tiles.put(s,tile);
	}
	return tile;
    }

    public BufferedImage makeImage(char c, Color fgcol, Color bgcol) {
	BufferedImage r = new BufferedImage(cellDim.width, cellDim.height, 
					    BufferedImage.TYPE_4BYTE_ABGR);
	Graphics2D g = r.createGraphics();
	g.setBackground(bgcol);
	g.clearRect(0,0,cellDim.width,cellDim.height);
	g.setColor(fgcol);
	g.setFont(font);

	g.setRenderingHint(RenderingHints.KEY_RENDERING, 
			   RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_OFF);
	
	g.drawString(String.valueOf(c),0,cellDim.height); //I HAVE NO IDEA WHAT I'M EVEN--

	return r;
    }

    public Dimension getCellDim(){return cellDim;}

}
