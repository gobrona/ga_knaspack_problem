package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author IGobronidze
 */
public class Chromosome {

    private int size;
    private ArrayList<Boolean> genes;

    public Chromosome() {
    }

    public Chromosome(int size) {
        genes = new ArrayList<Boolean>();
        for (int i = 0; i < size; i++) {
            genes.add(Boolean.FALSE);
        }
    }

    public Chromosome(int size, ArrayList<Boolean> genes) {
        this.size = size;
        this.genes = genes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Boolean> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Boolean> genes) {
        this.genes = genes;
    }

    /**
     * მეთოდი ადგენს შემთხვევით ქრომოსომას
     *
     * @return შემთქვევითად შექმნილი ქრომოსომა
     */
    public static Chromosome getRandomChromosome() {
        Random r = new Random();
        Chromosome c = new Chromosome(DataCreator.numberOfItems);
        for (int i = 0; i < DataCreator.numberOfItems; i++) {
            c.getGenes().set(i, r.nextBoolean());
        }
        return c;
    }

    /**
     * ფიტნეს ფუნქცია. ჯამი ყველა ჩაგდებული ნივთის
     *
     * @return ფიტენს ფუნქციის მნიშვნელობაs
     */
    public int fitness() {
        int f = 0;
        ArrayList<Item> items = DataCreator.knapsack.getItems();
        for (int i = 0; i < DataCreator.numberOfItems; i++) {
            f += items.get(i).getB() * (genes.get(i) ? 1 : 0);
        }
        return f;
    }

    /**
     * მეთოდი ადგენს ჩაგდებული ნივთების დაკავებულ მოცულობას. ეს მოცულობა ვერ
     * იქნება საერო მოცულობაზე მეტი, ხდება ამის კონტროლო
     *
     * @return ქრომოსომით მიღებული ნივთების მოცულობა
     */
    public int totalVolume() {
        int v = 0;
        ArrayList<Item> items = DataCreator.knapsack.getItems();
        for (int i = 0; i < DataCreator.numberOfItems; i++) {
            v += items.get(i).getV() * (genes.get(i) ? 1 : 0);
        }
        if (v > DataCreator.knapsackVolume) {
            Random r = new Random();
            while (true) {
                int x = r.nextInt(size);
                if (genes.get(x)) {
                    genes.set(x, Boolean.FALSE);
                    return this.totalVolume();
                }
            }

        }
        return v;
    }

    @Override
    public String toString() {
        return "Chromosome{" + "genes=" + genes + '}';
    }

}
