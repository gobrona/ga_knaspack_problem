package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author IGobronidze
 */
public class GA {
    
    private static long generation = 0;
    
    private static Population population;
    private static ArrayList<Integer> fitnessList;
    
    /**
     * GA ალგორითმი. ალგორითმი ეძებს საუკეთესო გამოსავალს, მასიმალურად გაავსოს
     * ზურგჩანთა ნივთებით ისე, რომ არ ასცდეს ზღვარს
     *
     */
    public static void solveProblem() {
        population = Population.initPopulation();         // საწყისი პოპულაცია
        while (true) {
            generation++;
            int min = Integer.MAX_VALUE;
            int m = 0;
            int k = 0;
            fitnessList = new ArrayList<Integer>();
            for (Chromosome c : population.getChromosomes()) {
                fitnessList.add(c.fitness());
                if (c.fitness() < min) {
                    m = k;
                    min = c.fitness();
                }
                k++;
            }
            mutation(population.getChromosomes().get(m));
            try {
                Thread.sleep(Data.sleepTimeBeteenIteration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("საუკეთესო ინდივიდი - " + getBestAnswer().fitness() + 
                    "      ალბათობა პოპულაციაში - " + percentageOfSames(fitnessList) + "      თაობა - " + generation);
            if (percentageOfSames(fitnessList) >= 0.95) {
                break;
            }
            RouletteWheelSelection(fitnessList);
        }

        System.out.println(System.lineSeparator() + "საუკეთესო ინდივიდი - " + getBestAnswer().fitness() + 
                "      საჭირო თაობების რაოდენობა " + generation);
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
    private static void RouletteWheelSelection(ArrayList<Integer> fitnesses) {
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

        crossover(i, j);
    }

    /**
     * შერყწმის ოპერაცია
     *
     * @param i პირველი ქრომოსომის ინდექსი
     * @param j მეორე ქრომოსომის ინდექსი
     * @param population პოპულაცია სადაც ხდება შერწყმა
     */
    private static void crossover(int i, int j) {
        Chromosome p1 = population.getChromosomes().get(i);
        Chromosome p2 = population.getChromosomes().get(j);
        Random r = new Random();
        int x = r.nextInt(Data.numberOfItems);
        Chromosome c1 = new Chromosome(Data.numberOfItems);
        Chromosome c2 = new Chromosome(Data.numberOfItems);
        for (int k = 0; k < x; k++) {
            c1.getGenes().set(k, p1.getGenes().get(k));
        }
        for (int k = x; k < Data.numberOfItems; k++) {
            c1.getGenes().set(k, p2.getGenes().get(k));
        }
        for (int k = 0; k < x; k++) {
            c2.getGenes().set(k, p2.getGenes().get(k));
        }
        for (int k = x; k < Data.numberOfItems; k++) {
            c2.getGenes().set(k, p1.getGenes().get(k));
        }
        survivorSelection(c1, c2);
    }

    private static void survivorSelection(Chromosome c1, Chromosome c2) {
        int m1 = 0;
        for (int i = 0; i<fitnessList.size(); i++) {
            if (fitnessList.get(i) < fitnessList.get(m1)) {
                m1 = i;
            }
        }
        int m2 = 0;
        for (int i = 0; i<fitnessList.size(); i++) {
            if (m1 != i && fitnessList.get(i) < fitnessList.get(m2)) {
                m2 = i;
            }
        }
        population.getChromosomes().set(m1, c1);
        population.getChromosomes().set(m2, c2);
    }

    private static Chromosome getBestAnswer() {
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
