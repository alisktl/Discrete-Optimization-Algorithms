package knapsack;

import java.util.Random;

/**
 * @author alisher
 * @project 1. Genetic Algorithm
 */
public class Chromosome implements Cloneable {

    private static Random random = new Random();

    private int[] genes;
    private long fitness;
    private long weight;

    private long[] weights;
    private long[] values;
    private long capacity;

    public Chromosome(int geneLength, long[] weights, long[] values, long capacity) {
        this.genes = new int[geneLength];
        fitness = 0;
        weight = 0;

        this.weights = weights;
        this.values = values;
        this.capacity = capacity;

        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(random.nextInt(101)) < 99 ? 0 : 1;
        }
    }

    public void calculateFitness() {
        fitness = 0;
        weight = 0;

        for (int i = 0; i < genes.length; i++) {
            int gene = genes[i];

            if (gene == 1) {
                fitness += values[i];
                weight += weights[i];
            }

            if (weight > capacity) {
                fitness = 0;
            }
        }
    }

    public long getFitness() {
        return fitness;
    }

    public long getWeight() {
        return weight;
    }

    public int[] getGenes() {
        return genes;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Chromosome chromosome = (Chromosome) super.clone();
        chromosome.genes = new int[this.genes.length];

        System.arraycopy(this.genes, 0, chromosome.genes, 0, chromosome.genes.length);
        return chromosome;
    }
}
