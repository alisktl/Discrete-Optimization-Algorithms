package knapsack;

/**
 * @author alisher
 * @project 1. Genetic Algorithm
 */
public class Population {

    int maxFit1Index = 0;
    int maxFit2Index = 0;
    private Chromosome[] chromosomes;
    private long fittestValue = 0;
    private long fittestWeight = 0;
    private long[] weights;
    private long[] values;
    private long capacity;

    public Population(int populationSize, int geneLength, long[] weights, long[] values, long capacity) {
        this.chromosomes = new Chromosome[populationSize];

        this.weights = weights;
        this.values = values;
        this.capacity = capacity;

        // Создаем популяцию
        for (int i = 0; i < chromosomes.length; i++) {
            chromosomes[i] = new Chromosome(geneLength, weights, values, capacity);
        }
    }

    public void selectTwoFittestChromosomes() {
        maxFit1Index = 0;
        maxFit2Index = 0;

        for (int i = 0; i < chromosomes.length; i++) {
            if (chromosomes[i].getFitness() > chromosomes[maxFit1Index].getFitness()) {
                maxFit2Index = maxFit1Index;
                maxFit1Index = i;
            } else if (chromosomes[i].getFitness() > chromosomes[maxFit2Index].getFitness()) {
                maxFit2Index = i;
            }
        }

        fittestValue = chromosomes[maxFit1Index].getFitness();
        fittestWeight = chromosomes[maxFit1Index].getWeight();
    }

    public int getFittest1Index() {
        return maxFit1Index;
    }

    public int getFittest2Index() {
        return maxFit2Index;
    }

    public Chromosome getFittest1Chromosome() {
        try {
            return (Chromosome) chromosomes[maxFit1Index].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Chromosome getFittest2Chromosome() {
        try {
            return (Chromosome) chromosomes[maxFit2Index].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public long getFittestValue() {
        return fittestValue;
    }

    public long getFittestWeight() {
        return fittestWeight;
    }

    public int getLeastFittestIndex() {
        long minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;

        for (int i = 0; i < chromosomes.length; i++) {
            if (minFitVal >= chromosomes[i].getFitness()) {
                minFitVal = chromosomes[i].getFitness();
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }

    public void calculateFitness() {
        for (Chromosome chromosome : chromosomes) {
            chromosome.calculateFitness();
        }

        selectTwoFittestChromosomes();
    }

    public int getPopulationSize() {
        return chromosomes.length;
    }

    public Chromosome[] getChromosomes() {
        return chromosomes;
    }
}
