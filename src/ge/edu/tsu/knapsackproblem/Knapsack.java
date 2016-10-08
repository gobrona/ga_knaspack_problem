package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;

/**
 *
 * @author IGobronidze
 */
public class Knapsack {

    private ArrayList<Item> items;

    public Knapsack() {
        items = new ArrayList<Item>();
    }

    public Knapsack(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

}
