import java.io.Serializable;
import java.time.LocalDate;
import java.util.EnumSet;

public class AngryCat implements Comparable<AngryCat>, Serializable {
    enum Breed {
        Abyssinian_Cat, American_Bobtail, Lithuanian_vicious, Bengal_Cat
    }

    String name;
    Breed breed;
    LocalDate birthDate;
    int assault;

    public AngryCat(String name, Breed breed, LocalDate birthDate, int assault) {
        this.name = name;
        this.breed = breed;
        this.birthDate = birthDate;
        this.assault = assault;
    }

    public boolean isDangerous() {
        return this.assault > 3;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.breed + ") - " + this.assault + " assault(s)";
    }

    @Override
    public int compareTo(AngryCat other) {
        int nameComparison = this.name.compareToIgnoreCase(other.name) * -1; // Reverse order for Z to A
        if (nameComparison != 0) {
            return nameComparison;
        }
        int birthDateComparison = this.birthDate.compareTo(other.birthDate);
        if (birthDateComparison != 0) {
            return birthDateComparison;
        }
        return Integer.compare(this.assault, other.assault);
    }
}
