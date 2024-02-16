import java.util.Arrays;
import java.util.Random;

public class GAAlgorithm {
    // Hàm mục tiêu (fitness function)
    public static double fitnessFunction(double[] coefficients, double x) {
        double Fx = 0;
        for (int i = 0; i < coefficients.length; i++) {
            Fx += coefficients[i] * Math.pow(x, coefficients.length - 1 -i);

        }
        return Math.abs(Fx);
    }

    // Hàm tạo quần thể ban đầu
    public static int[][] createPopulation(int populationSize, int individualSize) {
        Random random = new Random();
        int[][] population = new int[populationSize][individualSize];
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < individualSize; j++) {
                population[i][j] = random.nextInt(2);
            }
        }
        return population;
    }

    // Hàm chọn lọc cá thể tốt nhất
    public static int[][] selection(int[][] population, double[] fitness, int numParents) {
        int[][] parents = new int[numParents][population[0].length];
        for (int i = 0; i < numParents; i++) {
            int minIndex = findMinIndex(fitness);
            parents[i] = population[minIndex];
            fitness[minIndex] = Double.MAX_VALUE; // Đánh dấu cá thể đã được chọn để không chọn lại
        }
        return parents;
    }

    // Hàm tìm index của giá trị nhỏ nhất trong mảng
    public static int findMinIndex(double[] arr) {
        int minIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Hàm lai ghép (crossover) giữa các cá thể
    public static int[][] crossover(int[][] parents, int offspringSize) {
        int[][] offspring = new int[offspringSize][parents[0].length];
        Random random = new Random();
        for (int i = 0; i < offspringSize; i++) {
            int parent1Index = random.nextInt(parents.length);
            int parent2Index = random.nextInt(parents.length);
            int crossoverPoint = random.nextInt(parents[0].length);
            for (int j = 0; j < crossoverPoint; j++) {
                offspring[i][j] = parents[parent1Index][j];
            }
            for (int j = crossoverPoint; j < parents[0].length; j++) {
                offspring[i][j] = parents[parent2Index][j];
            }
        }
        return offspring;
    }

    // Hàm đột biến (mutation) trong quần thể
    public static void mutation(int[][] offspringCrossover, double mutationRate) {
        Random random = new Random();
        for (int i = 0; i < offspringCrossover.length; i++) {
            for (int j = 0; j < offspringCrossover[0].length; j++) {
                if (random.nextDouble() < mutationRate) {
                    offspringCrossover[i][j] = 1 - offspringCrossover[i][j];
                }
            }
        }
    }

    // Hàm chạy giải thuật di truyền
    public static double geneticAlgorithm(double[] coefficients, int numGenerations, int populationSize, int individualSize, double mutationRate) {
        int[][] population = createPopulation(populationSize, individualSize);
        double[] fitness = new double[populationSize];

        for (int generation = 0; generation < numGenerations; generation++) {
            for (int i = 0; i < populationSize; i++) {
                double currentX = decodeIndividual(Arrays.copyOfRange(population[i], 0, individualSize));
                fitness[i] = fitnessFunction(coefficients, currentX);
            }

            int[][] parents = selection(population, fitness, 2);
            int[][] offspringCrossover = crossover(parents, populationSize - parents.length);
            mutation(offspringCrossover, mutationRate);

            population = new int[populationSize][individualSize];
            for (int i = 0; i < parents.length; i++) {
                population[i] = parents[i];
            }
            for (int i = parents.length; i < populationSize; i++) {
                population[i] = offspringCrossover[i - parents.length];
            }
        }

        int[] bestIndividual = new int[individualSize];
        double bestFitness = Double.MAX_VALUE;
        for (int i = 0; i < populationSize; i++) {
            double currentX = decodeIndividual(Arrays.copyOfRange(population[i], 0, individualSize));
            double currentFitness = fitnessFunction(coefficients, currentX);
            if (currentFitness < bestFitness) {
                bestIndividual = Arrays.copyOf(population[i], individualSize);
                bestFitness = currentFitness;
            }
        }

        return decodeIndividual(Arrays.copyOfRange(bestIndividual, 0, individualSize));
    }

    // Hàm giải mã cá thể (decode individual) thành giá trị x
    public static double decodeIndividual(int[] individual) {   //individual là dãy số nhị phân
        int value = 0;
        for (int i = 0; i < individual.length; i++) {
            value += individual[i] * Math.pow(2, individual.length - i - 1);
        }
        return value;
    }

}
