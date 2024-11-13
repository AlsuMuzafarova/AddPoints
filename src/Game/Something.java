package Game;

public class Something extends Creature {
    @Override
    public Point move() {
        this.currentPosition =
                new Point((int)(Math.random() * 1000), (int)(Math.random() * 1000));
        return currentPosition;
    }

}
