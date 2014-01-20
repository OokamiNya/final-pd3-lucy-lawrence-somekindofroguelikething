public class Position implements Comparable<Position> {
    private int x;
    private int y;
    
    public Position(int x, int y) {
	this.x = x;
	this.y = y;
    }
    
    public int getx() {return x;}
    public int gety() {return y;}

    public boolean equals(Object o) {
	if (o==null) {return false;}
	if (o.getClass() != this.getClass()) {return false;}
	else {
	    Position p = (Position) o;
	    if (p.getx()==this.x) {
		if (p.gety()==this.y) {return true;}
	    }
	}
	return false;
    }

    public int compareTo(Position p) {
	if (this.y == p.gety() && this.x == p.getx()) {return 0;}
	if (this.y < p.gety()) {return -1;}
	if (this.y > p.gety()) {return 1;}
	if (this.x < p.getx()) {return -1;}
	return 1;
    }

    public String toString() {
	return "Position at ("+x+", "+y+")";
    }
}
