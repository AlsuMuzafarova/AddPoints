package Game;

public abstract class Creature implements Moveable, Printeable {

    protected String name;

    public Point currentPosition;

    public void init() {
        this.currentPosition =
                new Point((int)(Math.random() * 1000), (int)(Math.random() * 1000));
    }
    public void setName(String newName) {
        this.name = newName;
    }

    @Override
    public abstract Point move();

    @Override
    public void print() {
        System.out.println(name + " - " + currentPosition);
    }
}
