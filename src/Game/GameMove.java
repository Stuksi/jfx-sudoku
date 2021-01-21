package Game;

public class GameMove {

    private final int row;
    private final int col;
    private final int previous;
    private final int next;

    public GameMove(int row, int col, int previous, int next) {
        this.row = row;
        this.col = col;
        this.previous = previous;
        this.next = next;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getPrevious() {
        return previous;
    }

    public int getNext() {
        return next;
    }
}
