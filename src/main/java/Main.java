import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {

        String filename = "src/main/resources/input.txt";
        System.out.println(runDay12Code(filename));

    }

    public static long runDay12Code(String filename) {

        int rollingSum = 0;

        File inputFile = new File(filename);

        try {
            Scanner myReader = new Scanner(inputFile);
            int rowCount = 0;

            while (myReader.hasNextLine()) {
                String charString = myReader.nextLine();
                SpringRow springRow = new SpringRow(charString);
                long rowSum = springRow.analyseLine();
                System.out.println(rowSum);
                rollingSum += rowSum;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return rollingSum;
    }
}
