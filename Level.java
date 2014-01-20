import java.util.*;

public class Level {
    final private int gridWidth = 40;
    final private int gridHeight = 20;
    private Tile[][] tiles = new Tile[gridWidth][gridHeight];
    private Room[] rooms;
    private boolean[][] valid = new boolean[gridWidth][gridHeight];
    private boolean[][] nocorridor = new boolean[gridWidth][gridHeight];
    private Random rng = new Random();
    
    private class Room {
	int w,h,x,y;
        Tile[] walls;
	Tile[] wallsok;
        boolean connected = false;

	public Room(int w, int h, int x, int y) {
	    this.w=w;
	    this.h=h;
	    this.x=x;
	    this.y=y;
	    walls = new Tile[w+w+h+h];
	    int nope = 0;
	    if (x<2 || x+w>gridWidth-2) {nope+=h;}
	    if (y<2 || y+h>gridHeight-2) {nope+=w;}
	    wallsok = new Tile[w+w+h+h-nope];
	    int n=0;
	    int m=0;
	    for (int i=0; i<w; i++) {
		walls[n]=tiles[x+i][y-1];
		n++;
		if(y-1>0) {
		    wallsok[m]=tiles[x+i][y-1]; 
		    m++;
		}
	    }
	    for (int i=0; i<w; i++) {
		walls[n]=tiles[x+i][y+h];
		n++;
		if(y+h+1<gridHeight) {
		    wallsok[m]=tiles[x+i][y+h]; 
		    m++;
		}
	    }
	    for (int i=0; i<h; i++) {
		walls[n]=tiles[x-1][y+i];
		n++;
		if(x-1>0) {
		    wallsok[m]=tiles[x-1][y+i]; 
		    m++;
		}
	    }
	    for (int i=0; i<h; i++) {
		walls[n]=tiles[x+w][y+i];
		n++;
		if(x+w+1<gridWidth) {
		    wallsok[m]=tiles[x+w][y+i]; 
		    m++;
		}
	    }
	    for (int i=0; i<wallsok.length; i++) {
		wallsok[i].setType(2);
	    }
	    System.out.println(toString());
	}

	public int yoff(Tile t) {
	    if(t.gety()<y){
	    System.out.println(-1);return -1;}
	    if(t.gety()>y+h-1){
	    System.out.println(1);return 1;}
	    System.out.println(0);
	    return 0;
	}

	public int xoff(Tile t) {
	    if(t.getx()<x){
	    System.out.println(-1);return -1;}
	    if(t.getx()>x+w-1){
	    System.out.println(1);return 1;}
	    System.out.println(0);
	    return 0;
	}

	public String toString() {
	    return w+"x"+h+" room at ("+x+","+y+")";
	}
    }

    public Level() {
	for (int y=0; y<tiles[0].length; y++) {
	    for (int x=0; x<tiles.length; x++) {
	        tiles[x][y] = new Tile(x,y);
	        valid[x][y] = true;
		nocorridor[x][y] = false;
	    }
	}

	rooms = new Room[rng.nextInt(5)+5];
	//rooms = new Room[2];
	int curroom = 0;
	Room r;
	while(curroom < rooms.length) {
	    r=makeRoom(0);
	    if (r!=null) {
		rooms[curroom]=r;
		curroom++;
	    } else {
		break;
	    }
	}

	for (int y=0; y<tiles[0].length; y++) {
	    for (int x=0; x<tiles.length; x++) {
	        if (tiles[x][y].getType() != -1) {
		    nocorridor[x][y] = true;
		}
	    }
	}
	
	for (int i=1; i<rooms.length; i++) {
	    if(rooms[i]==null) {break;}
	    makeCorridor(rooms[rng.nextInt(i)],rooms[i]);
	}
    }

    public Room makeRoom(int fails) {
	int width = rng.nextInt(8)+2;
	int height = rng.nextInt(8)+2;
	
	int x = rng.nextInt(gridWidth-width-1)+1;
	int y = rng.nextInt(gridHeight-height-1)+1;
	
	for (int i=y-3; i<y+height+3; i++) {
	    for (int j=x-3; j<x+width+3; j++) {
		if (j>=0 && j<gridWidth && i>=0 && i<gridHeight) {
		    if (!valid[j][i]) {
			if (fails>100) {return null;}
			else {return makeRoom(fails+1);}
		    }
		}
	    }
	}

	for (int i=y; i<y+height; i++) {
	    for (int j=x; j<x+width; j++) {
		tiles[j][i].setType(0);
		valid[j][i] = false;
	    }
	}
        
	for (int i=y-1; i<y+height+1; i++) {
	    for (int j=x-1; j<x+width+1; j++) {
	        if (tiles[j][i].getType()!=0) 
		    tiles[j][i].setType(1);
	    }
	}
	return new Room(width,height,x,y);
    }

    public void makeCorridor(Room start, Room end) {
	Tile startdoor = start.wallsok[rng.nextInt(start.wallsok.length)];
	Tile enddoor   =   end.wallsok[rng.nextInt(end.wallsok.length)];
	Tile starttile = tiles[startdoor.getx()+start.xoff(startdoor)]
	                      [startdoor.gety()+start.yoff(startdoor)];
	Tile endtile   = tiles[enddoor.getx()+end.xoff(enddoor)]
	                      [enddoor.gety()+end.yoff(enddoor)];
	
	Pathfinder p = new Pathfinder(starttile.getx(),starttile.gety(),
				      endtile.getx(),endtile.gety(),
				      nocorridor);

	p.generatePath();
	System.out.println("yolo");
	for (int i=0; i<p.getPath().size(); i++) {
	    Position curpos = p.getPath().get(i);
	    tiles[curpos.getx()][curpos.gety()].setType(3);
	}
	startdoor.setType(3);
	enddoor.setType(3);
	int curx = starttile.getx();
	int cury = starttile.gety();
       	for (int i=0; i<p.getPath().size(); i++) {
	    curx=p.getPath().get(i).getx();
	    cury=p.getPath().get(i).gety();
	    tiles[curx][cury].setType(3);
	}

	/*
	for (int i=Math.min(starttile.getx(),endtile.getx()); 
	     i<Math.max(starttile.getx(),endtile.getx()); i++) {
	    if(tiles[i][starttile.gety()].getType()!=0)
		tiles[i][starttile.gety()].setType(3);
	}

	for (int i=Math.min(starttile.gety(),endtile.gety());
	     i<Math.max(starttile.gety(),endtile.gety()); i++) {
	    if(tiles[endtile.getx()][i].getType()!=0)
		tiles[endtile.getx()][i].setType(3);
	}
	*/
    }

    public Tile[][] getTiles() {return tiles;}
    public Tile getTile(int x, int y) {return tiles[x][y];}
}
