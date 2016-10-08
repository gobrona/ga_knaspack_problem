package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;
import java.util.Random;

public class GA {
    
    private static long generation = 0;                // თაობების რაოდენობა
    
    private static Population population;              // პოპულაცია
    private static ArrayList<Integer> fitnessList;     // ფიტნესის მნიშვნელობები პოპულაციისთვის
    
    /**
     * ამოცანის პირობა - მოცემულიდ ნივთებით შეივსოს ზურგჩანთა ისე,
     * რომ ნივთების ჯამური მოცულობა არ ასცდეს ჩანთის მოცულობას და
     * ნივთების ჯამური ფასი იყოს მაქსიმალური.
     * 
     * ვიყენებთ გენეტიკურ ალგორითმს, რომელიც ყოველ იტერაციაზე ქმნის 
     * ახალი თაობის წარმომადგენლებს, სანამ მთელი თაობა არ იქნება მსგავსი(95%).
     * გამოყენებულია გენეტიკური ოპერატორები - ამორჩევა რულეტკის მეთოდით,
     * crossover ერთი წერთილით და მუტაცია.
     */
    public static void solveProblem() throws InterruptedException{
        population = Population.initPopulation();
        while (true) {
            // თაობის მომატება
            generation++;                                           
            // ფიტნესების დათვლა
            fitnessList = new ArrayList<Integer>();                 
            for (Chromosome c : population.getChromosomes()) {
                fitnessList.add(c.fitness());
            }
            // მშობლების ამორჩევა
            Chromosome parent1 = new Chromosome();
            Chromosome parent2 = new Chromosome();
            RouletteWheelSelection(parent1, parent2);
            // ახალი ინდივიდების შექმნა და ჩანაცვლება
            crossoverAndReplace(parent1, parent2);
            // მუტაციის ოპერაცია
            mutation();
            // ინფორმაციის გამოტანა
            Thread.sleep(Data.sleepTimeBeteenIteration);
            System.out.println("თაობა - " + generation + "     " + 
                    "საუკეთესოს ფიტნესი - " + getBestAnswer().fitness() + "     " + 
                    "დამთხვევა პოპულაციაში - " + 100 * percentageOfSames(fitnessList) + "%");
            // მიზნის სისწორის შემოწმება
            if (percentageOfSames(fitnessList) >= Data.goalCondition) {
                // ალგორითმა იპოვა ამონახსნი
                System.out.println(getBestAnswer());
                break;
            }
        }
    }
    
     /**
     * ამორჩევა რულტკის პრინციპით - ყოველი ქრომოსომის ფიტნესის
     * პროპორციული ალბათობით ირჩევა ორი მშობელი ქრომოსომა
     *
     * @param parent1 პირველი მშობელი
     * @param parent2 მეორე მშობელი
     */
    private static void RouletteWheelSelection(Chromosome parent1, Chromosome parent2) {
        // ვთვლით ფიტნესების ჯამს
        int fSum = 0;
        for (int x : fitnessList) {
            fSum += x;
        }
        // ვირჩევთ შემთხვევით რიცხვს 0-დან ჯამამდე
        Random r = new Random();
        int s = r.nextInt(fSum);
        // ვიღებთ ელემენტს, რომელიც შეესაბამა შემთხვევით რიცხვს
        int i = 0;
        while (true) {
            s = s - fitnessList.get(i);
            if (s < 0) {
                break;
            }
            i++;
        }
        // იგივე მეორე ელემეტისთვის
        int j = 0;
        s = r.nextInt(fSum);
        while (true) {
            s = s - fitnessList.get(j);
            if (s < 0) {
                break;
            }
            j++;
        }
        parent1.setGenes(population.getChromosomes().get(i).getGenes());
        parent2.setGenes(population.getChromosomes().get(j).getGenes());
    }
    
    /**
     * ორი ქრომოსომისგან იქმნება ახალი ორი ქრომოსომა ერთ წერტილოვანი
     * შერწყმის დახმარებით. მიღებული შვილი ქრომოსომები ძველ
     * პოპულაციაში ანაცვლებს ყველაზე "სუსტ" ინდივიდებს.
     *
     * @param parent1 პირველი მშობელი
     * @param parent2 მეორე მშობელი
     */
    private static void crossoverAndReplace(Chromosome parent1, Chromosome parent2) {
        // წყვეტის წერტილის აღება შემთხვევითად
        Random r = new Random();
        int x = r.nextInt(Data.numberOfItems);
        // crossover
        Chromosome child1 = new Chromosome(Data.numberOfItems);
        Chromosome child2 = new Chromosome(Data.numberOfItems);
        for (int k = 0; k < x; k++) {
            child1.getGenes().set(k, parent1.getGenes().get(k));
        }
        for (int k = x; k < Data.numberOfItems; k++) {
            child1.getGenes().set(k, parent2.getGenes().get(k));
        }
        for (int k = 0; k < x; k++) {
            child2.getGenes().set(k, parent2.getGenes().get(k));
        }
        for (int k = x; k < Data.numberOfItems; k++) {
            child2.getGenes().set(k, parent2.getGenes().get(k));
        }
        // ჩანაცვლება
        replaceChromosomes(child1, child2);
    }
    
    /**
     * პოპულაციაში მოიძებნება ორი ყველაზე დაბალი ფიტნესის
     * მქონე ინდივიდი და ჩანაცვლდება ახალი ინდივიდების მიერ,
     * რომლებიც მივიღეთ crossover-ის დახმარებით.
     * 
     * @param child1 პირველი შვილი
     * @param child2 მეორე შვილი
     */
    private static void replaceChromosomes(Chromosome child1, Chromosome child2) {
        // ორი მინიმუმი ფიტნესის მქონე ინდივიდების მოძებნა
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
        // ჩანაცვლება
        population.getChromosomes().set(m1, child1);
        population.getChromosomes().set(m2, child2);
    }
    
    /**
     * მუტაციის მეთოდი - მთლიანად ცვლის შემთხვევითად აღებული ქრომოსომის
     * გენებს (0 -> 1,  1 -> 0). მუტაცია ხდება Data.mutationProbability ალბათობით.
     *
     */
    private static void mutation() {
        Random random = new Random();
        double d = random.nextDouble();
        // ალბათობის შემოწმება
        if (d > Data.mutationProbability) {
            return;
        }
        // გენების შეცვლა
        int index = random.nextInt(population.getChromosomes().size());
        Chromosome chromosome = population.getChromosomes().get(index);
        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            chromosome.getGenes().set(i, !chromosome.getGenes().get(i));
        }
    }
    
    /**
     * საუკეთესო ფიტნესის მქონე ინდივიდის მოძებნა
     * 
     * @return "საუკეთესო" ინდივიდი
     */
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


    /**
     * მეთოდი ადგენს პოპულაციაში რამდენჯერ მეორდება "საუკეთესო"
     * ინდივიდის ფიტნესი.
     *
     * @return პროცენტულად საუკეთესოს დამთხვევა
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
}
