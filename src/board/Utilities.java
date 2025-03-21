package board;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Utilities {

    private Utilities() {
        throw new AssertionError("Not an instantiable class. Invoke its methods statically.");
    }

    public static int generateRandomNumber(int lowerBound, int upperBound) {
        Random random = new Random(System.nanoTime());
        return random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    @SuppressWarnings("unused")
    public static Coordinates createRandomCoordinates() {

        int row = generateRandomNumber(0, 9);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int col = generateRandomNumber(0, 9);
        return new Coordinates(row, col);
    }

    public static void main(String[] args) {
        Set<Integer> numbers = new TreeSet<>();
        while(numbers.size() < 10) {
            numbers.add(generateRandomNumber(0, 9));
        }
        System.out.println(numbers);
    }
}
