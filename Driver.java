import javax.swing.*;
import java.awt.*;

public class Driver {
    public static void main(String[] args) {
	Gui gui = new Gui();
	SwingPane pane = gui.getPane();
	pane.refresh();
	for (int x=0; x<80; x++) {
	    for (int y=0; y<25; y++) {
		if ((x+y)%2==1) {pane.putChar(' ',x,y,Color.WHITE,Color.DARK_GRAY);}
	        else {pane.putChar(' ',x,y,Color.WHITE,Color.BLACK);}
		if (x%5==4 || y%5 ==4) {pane.putChar(' ',x,y,Color.WHITE,Color.LIGHT_GRAY);}
	    }
	}

	gui.getPane().putChar('@',4,4,Color.WHITE,Color.BLACK);
	gui.getPane().putChar('@',4,5,Color.WHITE,Color.BLACK);
	gui.getPane().putChar('@',5,4,Color.WHITE,Color.BLACK);
	gui.getPane().putChar('@',5,5,Color.WHITE,Color.BLACK);
	gui.getPane().putChar('X',17,9,Color.BLUE,Color.RED);
	gui.getPane().putChar('O',1,5,Color.RED,Color.YELLOW);
	gui.getPane().putChar('~',64,19,Color.GREEN,Color.MAGENTA);
	gui.getPane().putChar('f',40,12,Color.YELLOW,Color.BLUE);
	gui.getPane().putChar('l',11,1,Color.BLACK,Color.WHITE);
	gui.getPane().putChar('#',55,16,Color.ORANGE,Color.GREEN);
	gui.getPane().putChar('G',55,16,Color.BLUE,Color.GREEN);
	gui.getPane().refresh();
	

    }
}
