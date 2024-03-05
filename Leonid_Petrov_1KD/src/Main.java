import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<AngryCat> catList = new ArrayList<>();
        readData("src/data.txt", catList);
        Collections.sort(catList);
        for (AngryCat cat : catList) {
            System.out.println(cat);
        }
        writeToFile(catList);
    }

    public static void writeToFile(ArrayList<AngryCat> catList) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name:");
        String fName = sc.nextLine();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fName))) {
            for (AngryCat cat : catList) {
                oos.writeObject(cat);
            }
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void readData(String fileName, ArrayList<AngryCat> catList) throws IOException {
        try (Scanner s = new Scanner(new File(fileName))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("<yyyy-MM-dd>");
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] data = line.split(" ",4);
                String name = data[0].replaceAll("\\(.*\\)", "");// Remove breed from name
                AngryCat.Breed breed = Cotoconverter(data[1].replaceAll("[\\(\\)]", ""));
                LocalDate birthDate = LocalDate.parse(data[2], formatter);
                int assault = countAssaults(data[3]);
                catList.add(new AngryCat(name, breed, birthDate, assault));
            }
        }
    }

    private static AngryCat.Breed Cotoconverter(String breed) {
        return switch (breed) {
            case "Abyssinian" -> AngryCat.Breed.Abyssinian_Cat;
            case "American" -> AngryCat.Breed.American_Bobtail;
            case "Lithuanian" -> AngryCat.Breed.Lithuanian_vicious;
            case "Bengal" -> AngryCat.Breed.Bengal_Cat;
            default -> throw new IllegalArgumentException("Invalid breed: " + breed);
        };
    }

    private static int countAssaults(String assaults) {
        String[] assaultData = assaults.split(", ");
        int count = 0;
        for (String assault : assaultData) {
            if (!assault.startsWith("*Playfully")&&!assault.startsWith("playfully")) { // Ignore playful assaults
                count++;
            }
        }
        return count;
    }
}
