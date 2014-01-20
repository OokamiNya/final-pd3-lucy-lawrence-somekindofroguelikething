import java.util.*;

public class Pathfinder {
    private ArrayList<Position> path = new ArrayList<Position>();
    private int startx, starty, endx, endy;
    private boolean[][] obstacles;
    private int[][] movecosts;
    private int[][] heurcosts;
    private TreeSet<Position> done = new TreeSet<Position>();
    private TreeSet<Position> doing = new TreeSet<Position>();
    private TreeSet<Position> later = new TreeSet<Position>();
    
    public Pathfinder(int startx, int starty, int endx, int endy, boolean[][] obstacles) {
	this.startx = startx;
	this.starty = starty;
	this.endx = endx;
	this.endy = endy;
	this.obstacles = obstacles;
        movecosts = new int[obstacles.length][obstacles[0].length];
        heurcosts = new int[obstacles.length][obstacles[0].length];
	
	for (int j=0; j<movecosts[0].length; j++) {
	    for (int i=0; i<movecosts.length; i++) {
	        if (obstacles[i][j]) {
		    movecosts[i][j] = Integer.MAX_VALUE;
		    done.add(new Position(i,j));
		} else {
		    movecosts[i][j] = -1;
		    later.add(new Position(i,j));
		}
	    }
	}
	System.out.println(done);
	System.out.println(doing);
	System.out.println(later);
	    
	movecosts[startx][starty] = 0;
	finish(new Position(startx,starty));
	
	calcHeurCosts();
    }

    public void generatePath() {
	//System.out.println(done);
	//System.out.println(doing);
	//System.out.println(later);
	while (!done.contains(new Position(endx, endy))) {
	    //System.out.println("swag");
	    finish(nextCheapest());
	}
	debugshow();

	for (int j=0; j<movecosts[0].length; j++) {
	    for (int i=0; i<movecosts.length; i++) {
		if(movecosts[i][j]>900000) {System.out.print("XX ");}
		else if(movecosts[i][j]<10 && movecosts[i][j]>-1) {
		    System.out.print(" "+movecosts[i][j]+" ");
		}else {System.out.print(movecosts[i][j]+" ");}
	    }
	    System.out.println();
	}
        Position p = new Position(endx,endy);
	while (!p.equals(new Position(startx,starty))) {
	    path.add(p);
	    p = prevPosition(p);
	}  
	path.add(new Position(startx,starty));
    }

    private Position nextCheapest() {
	Position r = new Position(-1,-1);
	int mincost = Integer.MAX_VALUE;
	Iterator it = doing.iterator();
	//System.out.println(doing);
	System.out.println(doing.size());
	//System.out.println(done.contains(new Position(startx,starty)));
        while (it.hasNext()) {
	    Position curpos = (Position) it.next();
	    int curx = curpos.getx();
	    int cury = curpos.gety();
	    int nextcost = heurcosts[curx][cury] + movecosts[curx][cury];
	    if (nextcost<mincost) {
		mincost = nextcost;
		r = curpos;
	    }
	    //System.out.println(curpos);
	}
	return r;
    }

    private Position prevPosition(Position p) {
	for (int i=0; i<Direction.ORTHO.length; i++) {
	    Direction curdir = Direction.ORTHO[i];
	    int curx = p.getx()+curdir.getx();
	    int cury = p.gety()+curdir.gety();
	    if(movecosts[curx][cury]==movecosts[p.getx()][p.gety()]-1) {
		return new Position(curx,cury);
	    }
	}
	return new Position(-1,-1);
    }

    private int heuristic(int sx, int sy, int ex, int ey) {
	return Math.abs(ex - sx) + Math.abs(ey - sy);
    }

    private void calcHeurCosts() {
	for (int j=0; j<heurcosts[0].length; j++) {
	    for (int i=0; i<heurcosts.length; i++) {
		heurcosts[i][j] = heuristic(i,j,endx,endy);
	    }   
	}
    }

    private void finish(Position p) {
	doing.remove(p);
	done.add(p);
	System.out.println("p: "+p);
	for (int i=0; i<Direction.ORTHO.length; i++) {
	    Direction curdir = Direction.ORTHO[i];
	    Position curpos = new Position(p.getx()+curdir.getx(), p.gety()+curdir.gety());
	    int curx = curpos.getx();
	    int cury = curpos.gety();
	    if (!done.contains(curpos)) {
	    if (curx>=0 && curx<obstacles.length && cury>=0 && cury<obstacles.length) {
		if (!obstacles[curx][cury]) {
		    System.out.println(curpos);
		    later.remove(curpos);
		    doing.add(curpos);
		    movecosts[curx][cury] = movecosts[p.getx()][p.gety()] + 1;
		}
	    }
	    }
	}
    }

    public ArrayList<Position> getPath() {
	return path;
    }

    private void debugshow() {
	char[][] c = new char[obstacles.length][obstacles[0].length];
	Iterator it;
	it = done.iterator();
        while (it.hasNext()) {
	    Position p = (Position) it.next();
	    c[p.getx()][p.gety()] = '+';
	}
	it = doing.iterator();
        while (it.hasNext()) {
	    Position p = (Position) it.next();
	    c[p.getx()][p.gety()] = 'o';
	}
	it = later.iterator();
        while (it.hasNext()) {
	    Position p = (Position) it.next();
	    c[p.getx()][p.gety()] = '.';
	}
	for (int y=0; y<c[0].length; y++) {
	    for (int x=0; x<c.length; x++) {
		System.out.print(c[x][y]);
	    }
	    System.out.println();
	}
    }
}
