package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {

    private ArrayList<Boolean> genes;      // გენები (0 ან 1)

    public Chromosome() {
        genes = new ArrayList<Boolean>();
    }

    public Chromosome(int size) {
        this();
        for (int i = 0; i < size; i++) {
            genes.add(Boolean.FALSE);
        }
    }

    public Chromosome(ArrayList<Boolean> genes) {
        this.genes = genes;
    }

    public ArrayList<Boolean> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Boolean> genes) {
        this.genes = genes;
    }

    /**
     * მეთოდი შემთქვევითად ქმნის ქრომოსომას.
     *
     * @return შემთქვევითად შექმნილი ქრომოსომა
     */
    public static Chromosome getRandomChromosome() {
        Random r = new Random();
        Chromosome c = new Chromosome(Data.numberOfItems);
        for (int i = 0; i < Data.numberOfItems; i++) {
            c.getGenes().set(i, r.nextBoolean());
        }
        return c;
    }

    /**
     * ფიტნეს ფუნქცია - ჯამი ყველა ჩაგდებული ნივთის.
     * ასევე მოწმდება ნივთების ჯამური მოცულობა, თუ ის აღემატება
     * ჩანთის მოცულობას, ფიტნეს ფუნქცია ხდება -1.
     *
     * @return ფიტნეს ფუნქციის მნიშვნელობა
     */
    public int fitness() {
        ArrayList<Item> items = Data.items;
        int volume = 0;
        for (int i = 0; i<Data.numberOfItems; i++) {
            volume += items.get(i).getVolume()* (genes.get(i) ? 1 : 0);
        }
        if (volume > Data.knapsackVolume) {
            return -1;
        }
        int fitness = 0;
        for (int i = 0; i < Data.numberOfItems; i++) {
            fitness += items.get(i).getPrice()* (genes.get(i) ? 1 : 0);
        }
        return fitness;
    }
    
    @Override
    public String toString() {
        ArrayList<Item> items = Data.items;
        int volume = 0;
        for (int i = 0; i<Data.numberOfItems; i++) {
            volume += items.get(i).getVolume()* (genes.get(i) ? 1 : 0);
        }
        int fitness = 0;
        for (int i = 0; i < Data.numberOfItems; i++) {
            fitness += items.get(i).getPrice()* (genes.get(i) ? 1 : 0);
        }
        String text = "";
        text += "დაკავებული მოცულობა - " + volume + "     " +
                "ჯამური ფასი - " + fitness + "     ";
        return text;
    }
}
