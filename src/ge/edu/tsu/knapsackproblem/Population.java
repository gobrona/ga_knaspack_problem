package ge.edu.tsu.knapsackproblem;

import java.util.ArrayList;

public class Population {

    private ArrayList<Chromosome> chromosomes;       // პოპულაციაში არსებული ქრომოსომები

    public Population() {
        chromosomes = new ArrayList<Chromosome>();
    }

    public Population(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public ArrayList<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    /**
     * საწყისი, შემთხვევითი პოპულაციის მიღება.
     *
     * @return საწყისი პოპულაცია
     */
    public static Population initPopulation() {
        Population p = new Population();
        for (int i = 0; i < Data.numberOfChromosomes; i++) {
            p.getChromosomes().add(Chromosome.getRandomChromosome());
        }
        return p;
    }
}
