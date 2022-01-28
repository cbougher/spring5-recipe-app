package guru.springframework.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @Transient
    private Integer wholeAmount;

    @Transient
    private String fractionAmount;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Integer getWholeAmount() {
        return amount.intValue();
    }

    public String getFractionAmount() {
        double decimalPart = amount.remainder(BigDecimal.ONE).doubleValue();

        return decimalToFraction(decimalPart);
    }

    // Recursive function to
    // return GCD of a and b
    static long gcd(long a, long b)
    {
        if (a == 0)
            return b;
        else if (b == 0)
            return a;
        if (a < b)
            return gcd(a, b % a);
        else
            return gcd(b, a % b);
    }

    // Function to convert decimal to fraction
    private String decimalToFraction(double number)
    {

        // Fetch integral value of the decimal
        double intVal = Math.floor(number);

        // Fetch fractional part of the decimal
        double fVal = number - intVal;

        // Consider precision value to
        // convert fractional part to
        // integral equivalent
        final long pVal = 1000000000;

        // Calculate GCD of integral
        // equivalent of fractional
        // part and precision value
        long gcdVal = gcd(Math.round(
                fVal * pVal), pVal);

        // Calculate num and deno
        long num = Math.round(fVal * pVal) / gcdVal;
        long deno = pVal / gcdVal;

        // Print the fraction
        if((long)(intVal * deno) + num > 0) {
            return (long) (intVal * deno) +
                    num + "/" + deno;
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient that = (Ingredient) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
