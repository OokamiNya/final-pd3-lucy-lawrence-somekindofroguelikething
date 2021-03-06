import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Game {
    private Gui gui = new Gui(this);
    private Level[] levels;
    private Level curlevel;
    private Player p1 = new Player(); 
    private Random rng = new Random();
    private ArrayList<Item> items = new ArrayList<Item>();  
    private int turncount = 0;
    private String curmessage = "Welcome to yoloRL, home of the at sign. May I take your order?";
    
    public Game() {
	levels = new Level[10];
	levels[0] = new Level();
	curlevel = levels[0];
	initGame();
	getPane().refresh();
    }

    public void readItems() {
	File itemdata = new File("Items.txt");
        Scanner s = null;
	Item curitem = null;
	try {
	    s = new Scanner(itemdata);
	} catch (IOException e) {
	    System.out.println(e);
	}
	while (s.hasNextLine()) {
	    String line = s.nextLine();
	    System.out.println(line);
	    if (line.length()>0) {
		if (line.charAt(0)=='[' && line.charAt(line.length()-1)==']') {
		    if (curitem!=null) {items.add(curitem);} 
		    curitem = new Item();
		    curitem.setName(line.substring(1,line.length()-1));
		} else if (line.indexOf(':')!=-1) {
		    int colon = line.indexOf(':');
		    String stat = line.substring(0,colon).trim().toLowerCase();
		    String value = line.substring(colon+1,line.length()).trim();
		    switch (stat) {
		    case "char": curitem.setChar(value.charAt(0));
		    }
		}
	    }
	}
    }

    public void refreshMap() {
	Tile curtile;
	for (int y=0; y<curlevel.getTiles()[0].length; y++) {
	    for (int x=0; x<curlevel.getTiles().length; x++) {
		curtile = curlevel.getTiles()[x][y];
		getPane().putChar(curtile.getChar(),
				  x+1,y+1,
				  curtile.getFGCol(),curtile.getBGCol());
	    }
	}
    }

    public void refreshStats() {
	getPane().putString("yolo",42,1);
	getPane().putString("Turn: "+turncount,42,2);
	getPane().putString("HP: "+p1.getHP()+"/"+p1.getMaxHP(),42,3);
	getPane().putString("Inventory: ",42,4);
	for(int i=0; i<p1.getInventory().size(); i++) {
	    getPane().putString(p1.getInventory().get(i).getName(),42,5+i);
	}
    }

    public void refreshMessages() {
	getPane().clearRect(1,22,78,2);
	getPane().putString(curmessage,1,22);
    }

    public void initGame() {
	readItems();
        spawnMob(p1);
	for (int i=0; i<20; i++) {spawnItem(randomItem());}
	getPane().putRectangle(' ',0,0,42,22,Color.WHITE,Color.WHITE);
	getPane().putRectangle(' ',0,21,80,4,Color.WHITE,Color.WHITE);
	getPane().putRectangle(' ',41,0,39,22,Color.WHITE,Color.WHITE);
	refreshEverything();
    }

    public void gameLoop(KeyEvent e) {
	Command.keyEventToCommand(e);
        if(doCommand(Command.keyEventToCommand(e))) {
	    stuff();
	}
	refreshEverything();
    }
    
    public void refreshEverything() {
	refreshMap();
	refreshStats();
	refreshMessages();
	getPane().refresh();
    }

    public void stuff() {
	turncount++;
	String tileDesc = p1.getTile().getDescription();
	//if (tileDesc.length()>0) {curmessage=tileDesc;}
	curmessage=tileDesc;
    }

    public boolean doCommand(Command c) {
	switch (c) {
	case MOVE_UP: return p1.move(Direction.UP);
	case MOVE_DOWN: return p1.move(Direction.DOWN);
	case MOVE_LEFT: return p1.move(Direction.LEFT);
	case MOVE_RIGHT: return p1.move(Direction.RIGHT);
	case MOVE_UPLEFT: return p1.move(Direction.UPLEFT);
	case MOVE_UPRIGHT: return p1.move(Direction.UPRIGHT);
	case MOVE_DOWNLEFT: return p1.move(Direction.DOWNLEFT);
	case MOVE_DOWNRIGHT: return p1.move(Direction.DOWNRIGHT);
	case GET_ITEM: return p1.pickUpItem();
	case DROP_ITEM: return p1.dropItem();
	case WAIT: return p1.move(Direction.NONE);
	case INVALID_COMMAND: System.out.println("mfw"); return false;
	default: System.out.println("whatever"); return false;
	}
    }

    public void spawnMob(Creature c) {
	Tile t = curlevel.getRoomTiles()[rng.nextInt(curlevel.getRoomTiles().length)];
        t.setBaka(c);
	c.setTile(t);
    }

    public void spawnItem(Item i) {
	Tile t = curlevel.getRoomTiles()[rng.nextInt(curlevel.getRoomTiles().length)];
	t.addItem(i);
    }

    public Item randomItem() {
	return items.get(rng.nextInt(items.size()));
    }
	

    public Gui getGui() {return gui;}
    public SwingPane getPane() {return gui.getPane();}
    public Level[] getLevels() {return levels;}
    public Level getCurLevel() {return curlevel;}
    
}
