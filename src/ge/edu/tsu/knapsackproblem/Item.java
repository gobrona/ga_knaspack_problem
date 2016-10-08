package ge.edu.tsu.knapsackproblem;

public class Item {

    private int volume;   // ტევადობა
    private int price;    // ფასი

    public Item() {
    }

    public Item(int volume, int price) {
        this.volume = volume;
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
