package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;

public class Data {

    public static final int numberOfItems = 20;                       // ნივთების რაოდენობა
    public static final int knapsackVolume = 100;                     // ჩანთის მოცულობა
    public static final int numberOfChromosomes = 1000;               // ქრომოსომების რაოდენობა პოპულაციაში              
    public static double mutationProbability = 0.2;                   // მუტაციის ალბათობა ახალი თაობის მიღებისას
    public static double goalCondition = 0.98;                        // მიზნის სისწორის შესამოწმებელი პროცენტი
    
    public static ArrayList<Item> items = new ArrayList<Item>();      // ნივთების მოცულობა და ფასი
    
    public static long sleepTimeBeteenIteration = 20;                 // იტერაციებს შორის პერიოდი
    
    // ნივთების ინიციალიზაციას
    static {
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
    }
}
