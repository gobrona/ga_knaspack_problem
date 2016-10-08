package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;

/**
 *
 * @author IGobronidze
 */
public class DataCreator {

    public static final int numberOfItems = 20;
    public static final int knapsackVolume = 100;
    public static final int numberOfChromosomes = 100;
    public static ArrayList<Item> items = new ArrayList<Item>();
    public static Knapsack knapsack = new Knapsack();

    public static void initItems() {
        items.add(new Item(7, 13));
        items.add(new Item(10, 12));
        items.add(new Item(15, 7));
        items.add(new Item(3, 4));
        items.add(new Item(9, 15));
        items.add(new Item(9, 9));
        items.add(new Item(8, 6));
        items.add(new Item(12, 7));
        items.add(new Item(7, 11));
        items.add(new Item(1, 10));
        items.add(new Item(4, 23));
        items.add(new Item(32, 22));
        items.add(new Item(23, 70));
        items.add(new Item(12, 14));
        items.add(new Item(9, 15));
        items.add(new Item(15, 5));
        items.add(new Item(22, 16));
        items.add(new Item(18, 17));
        items.add(new Item(11, 21));
        items.add(new Item(20, 20));
        knapsack.setItems(items);
    }

}
