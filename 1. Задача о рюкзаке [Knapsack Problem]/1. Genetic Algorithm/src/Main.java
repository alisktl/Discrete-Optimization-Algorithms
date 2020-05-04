import knapsack.Chromosome;
import knapsack.Population;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author alisher
 * @project 1. Genetic Algorithm
 */
public class Main {

    private static Random random = new Random();

    private static Population population;
    private static Chromosome fittestChromosome;
    private static Chromosome secondFittestChromosome;
    private static int generationCount;
    private static int geneLength;

    private static long capacity;
    private static int mutationProbability;

    public static void main(String[] args) throws IOException {

        File fout = new File("input/input.txt");

        Scanner sc = new Scanner(fout);

        geneLength = sc.nextInt();
        capacity = sc.nextLong();
        mutationProbability = sc.nextInt();
        int populationSize = sc.nextInt();

        long[] weights = new long[geneLength];
        long[] values = new long[geneLength];

        for (int i = 0; i < geneLength && sc.hasNext(); i++) {
            weights[i] = sc.nextLong();
        }

        for (int i = 0; i < geneLength && sc.hasNext(); i++) {
            values[i] = sc.nextLong();
        }

        population = new Population(populationSize, geneLength, weights, values, capacity);
        generationCount = 0;

        population.calculateFitness();

        long prevFittestWeight = 0;
        long prevFittestWeightCount = 0;

        while (population.getFittestWeight() <= capacity) {
            ++generationCount;

            // Выбираем два лучшие хромосомы (набор предметов)
            selection();

            // Скрещиваем
            crossover();

            // Мутируем с вероятностью mutationProbability
            if (random.nextInt(101) <= mutationProbability) {
                mutation();
            }

            // Добавляем нового потомка
            addFittestOffspring();

            // Считаем новое fitness значение
            population.calculateFitness();

            if (prevFittestWeight == population.getFittestWeight()) {
                ++prevFittestWeightCount;
            } else {
                prevFittestWeightCount = 0;
                prevFittestWeight = population.getFittestWeight();
            }

            if (prevFittestWeightCount >= values.length * 2) {
                break;
            }
        }

        System.out.println("\nОтвет найден в поколении № " + generationCount);
        System.out.println("Ценность рюкзака: " + population.getFittestValue());
        System.out.print("Гены (рюкзак): ");
        for (int i = 0; i < geneLength; i++) {
            System.out.print(population.getFittest1Chromosome().getGenes()[i]);
        }
    }

    // Выбираем два лучшие хромосомы (набор предметов)
    private static void selection() {
        fittestChromosome = population.getFittest1Chromosome();
        secondFittestChromosome = population.getFittest2Chromosome();
    }

    // Скрещивание
    private static void crossover() {

        // Случайный разделитель
        int crossOverPoint = random.nextInt(geneLength);

        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittestChromosome.getGenes()[i];
            fittestChromosome.getGenes()[i] = secondFittestChromosome.getGenes()[i];
            secondFittestChromosome.getGenes()[i] = temp;
        }
    }

    // Мутация
    private static void mutation() {

        // Случайный разделитель
        int mutationPoint = random.nextInt(geneLength);

        if (fittestChromosome.getGenes()[mutationPoint] == 0) {
            fittestChromosome.getGenes()[mutationPoint] = 1;
        } else {
            fittestChromosome.getGenes()[mutationPoint] = 0;
        }

        mutationPoint = random.nextInt(geneLength);

        if (secondFittestChromosome.getGenes()[mutationPoint] == 0) {
            secondFittestChromosome.getGenes()[mutationPoint] = 1;
        } else {
            secondFittestChromosome.getGenes()[mutationPoint] = 0;
        }
    }

    // Добавление нового потомка в популяцию
    private static void addFittestOffspring() {

        fittestChromosome.calculateFitness();
        secondFittestChromosome.calculateFitness();

        int leastFittestIndex = population.getLeastFittestIndex();

        population.getChromosomes()[leastFittestIndex] = getFittestOffspring();
    }

    private static Chromosome getFittestOffspring() {
        if (fittestChromosome.getFitness() > secondFittestChromosome.getFitness()) {
            return fittestChromosome;
        }
        return secondFittestChromosome;
    }
}
