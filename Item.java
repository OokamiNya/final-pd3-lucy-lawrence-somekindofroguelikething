public class Item {
    private String name;
    private char dispChar;

    public Item() {
	name = "placeholder";
	dispChar = '/';
    }

    public char getChar() {return dispChar;}
    public String getName() {return name;}

    public void setName(String s) {name=s;}
    public void setChar(char c) {dispChar=c;}
}
