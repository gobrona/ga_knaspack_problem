package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author IGobronidze
 */
public class GA {

    /**
     * GA ალგორითმი. ალგორითმი ეძებს საუკეთესო გამოსავალს, მაქსიმუმად გაავსოს
     * ზურგჩანთა ნივთებით, სიე რომ არ აცდეს ზღვარს
     *
     * @return მაქსიმალური შევსების რაოდენობა
     */
    public static Chromosome algorithm() {
        DataCreator.initItems();                                     // მონაცემების ინიცილიზაცია
        Population population = Population.initPopulation();         // საწყისი პოპულაცია
        while (true) {
            int min = Integer.MAX_VALUE;
            int m = 0;
            int k = 0;
            ArrayList<Integer> fitnesses = new ArrayList<Integer>();
            for (Chromosome c : population.getChromosomes()) {
                fitnesses.add(c.fitness());
                if (c.fitness() < min) {
                    m = k;
                    min = c.fitness();
                }
                k++;
            }
            mutation(population.getChromosomes().get(m));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("საუკეთესო ინდივიდი - " + getBestAnswer(population).fitness() + "   ალბათობა პოპულაციაში - " + percentageOfSames(fitnesses));
            if (percentageOfSames(fitnesses) >= 0.95) {
                break;
            }
            RouletteWheelSelection(population, fitnesses);
        }

        return getBestAnswer(population);
    }

    /**
     * მეთოდი ადგენს რამდენი პროცენტია დამთხვევა საუკეთესო ვარიანტების
     *
     * @param arr ფიტნესების სია
     * @return დამთხვევა
     */
    private static Double percentageOfSames(ArrayList<Integer> arr) {
        int max = arr.get(0);
        for (int a : arr) {
            if (a > max) {
                max = a;
            }
        }
        int x = 0;
        for (int a : arr) {
            if (max == a) {
                x++;
            }
        }
        return (double) x / arr.size();
    }

    /**
     * მუტაციის მეთოდი
     *
     * @param chromosome ქრომოსომა რომელისთვისაც ხდება მუტაცია
     * @return ახალი ქრომოსომა
     */
    private static Chromosome mutation(Chromosome chromosome) {
        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            chromosome.getGenes().set(i, !chromosome.getGenes().get(i));
        }
        return chromosome;
    }

    /**
     * ამორჩევა რულტკის პრინციპით
     *
     * @param population პოპულაცია საიდანაც ხდება ამორჩევა
     * @param fitnesses ფიტნესები პოპულაციაში
     */
    private static void RouletteWheelSelection(Population population, ArrayList<Integer> fitnesses) {
        int fSum = 0;
        for (int x : fitnesses) {
            fSum += x;
        }
        Random r = new Random();
        int s = r.nextInt(fSum);
        int i = 0;
        while (true) {
            s = s - fitnesses.get(i);
            if (s < 0) {
                break;
            }
            i++;
        }
        int j = 0;
        s = r.nextInt(fSum);
        while (true) {
            s = s - fitnesses.get(j);
            if (s < 0) {
                break;
            }
            j++;
        }

        crossover(i, j, population, fitnesses);
    }

    /**
     * შერყწმის ოპერაცია
     *
     * @param i პირველი ქრომოსომის ინდექსი
     * @param j მეორე ქრომოსომის ინდექსი
     * @param population პოპულაცია სადაც ხდება შერწყმა
     */
    private static void crossover(int i, int j, Population population, ArrayList<Integer> fitnesses) {
        Chromosome p1 = population.getChromosomes().get(i);
        Chromosome p2 = population.getChromosomes().get(j);
        Random r = new Random();
        int x = r.nextInt(DataCreator.numberOfItems);
        Chromosome c1 = new Chromosome(DataCreator.numberOfItems);
        Chromosome c2 = new Chromosome(DataCreator.numberOfItems);
        for (int k = 0; k < x; k++) {
            c1.getGenes().set(k, p1.getGenes().get(k));
        }
        for (int k = x; k < DataCreator.numberOfItems; k++) {
            c1.getGenes().set(k, p2.getGenes().get(k));
        }
        for (int k = 0; k < x; k++) {
            c2.getGenes().set(k, p2.getGenes().get(k));
        }
        for (int k = x; k < DataCreator.numberOfItems; k++) {
            c2.getGenes().set(k, p1.getGenes().get(k));
        }
        survivorSelection(c1, c2, population, fitnesses);
    }

    private static void survivorSelection(Chromosome c1, Chromosome c2, Population population, ArrayList<Integer> fitnesses) {
        int m1 = 0;
        for (int i = 0; i<fitnesses.size(); i++) {
            if (fitnesses.get(i) < fitnesses.get(m1)) {
                m1 = i;
            }
        }
        int m2 = 0;
        for (int i = 0; i<fitnesses.size(); i++) {
            if (m1 != i && fitnesses.get(i) < fitnesses.get(m2)) {
                m2 = i;
            }
        }
        population.getChromosomes().set(m1, c1);
        population.getChromosomes().set(m2, c2);
    }

    private static Chromosome getBestAnswer(Population population) {
        ArrayList<Chromosome> chromosomes = population.getChromosomes();
        Chromosome best = chromosomes.get(0);
        for (Chromosome c : chromosomes) {
            if (c.fitness() > best.fitness()) {
                best = c;
            }
        }
        return best;
    }

}
