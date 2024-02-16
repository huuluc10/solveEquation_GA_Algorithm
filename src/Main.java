import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //số lượng thế hệ
        int numGenerations = 1000;
        // số lượng quần thể ban đầu để xét
        int populationSize = 1000;
        int individualSize = 10;
        double mutationRate = 0.01;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập bậc của phương trình: ");
        int degree = scanner.nextInt();
        double[] coefficients = new double[degree + 1];

        for (int i = 0; i <= degree; i++) {
            System.out.print("Nhập hệ số tại x^" + (degree - i) + ": ");
            coefficients[i] = scanner.nextDouble();
        }

//        double x = new Random().nextDouble() * 20 - 10; // Tạo giá trị ngẫu nhiên cho x trong khoảng [-10, 10]
        double bestX = GAAlgorithm.geneticAlgorithm(coefficients, numGenerations, populationSize, individualSize, mutationRate);

        System.out.println("Giá trị x mà tại đó hàm số đạt giá trị tối thiểu: x = " + bestX);
        if (GAAlgorithm.fitnessFunction(coefficients, bestX) != 0) {
            System.out.println("Nhưng kết quả " + bestX + " tìm được không phải là nghiệm của phương trình");
        } else {
            System.out.println("Kết quả " + bestX + " là nghiệm của phương trình");
        }
    }
}
