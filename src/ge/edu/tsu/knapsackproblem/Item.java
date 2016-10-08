package ge.edu.tsu.knapsackproblem;

/**
 *
 * @author IGobronidze
 */
public class Item {

    private int v;   // ტევადობა
    private int b;   // მოგება

    public Item() {
    }

    public Item(int v, int b) {
        this.v = v;
        this.b = b;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
