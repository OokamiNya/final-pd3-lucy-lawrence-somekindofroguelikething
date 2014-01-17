import java.awt.Color;

public class Player implements Creature {
    final private char displayChar = '@';
    final private Color displayColor = Color.LIGHT_GRAY;

    public char getDispChar() {return displayChar;}
    public Color getDispColor() {return displayColor;}
}
