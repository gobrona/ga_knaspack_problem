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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.v;
        hash = 29 * hash + this.b;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.v != other.v) {
            return false;
        }
        if (this.b != other.b) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "v=" + v + ", b=" + b + '}';
    }

}
