package guru.springframework.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"categories", "notes", "ingredients"})
@ToString(exclude = {"categories", "notes", "ingredients"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;

    private String url;
    @Lob
    private String directions;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    @ManyToMany
    @JoinTable(
        name = "categories_recipes",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    public void setNotes(Notes notes) {
        notes.setRecipe(this);

        this.notes = notes;
    }

    public Set<Ingredient> getIngredients() {
        if(ingredients == null) {
            ingredients = new HashSet<>();
        }
        return ingredients;
    }

    public Recipe addIngrediant(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.getIngredients().add(ingredient);

        return this;
    }

    public Set<Category> getCategories() {
        if(categories ==  null) {
            categories = new HashSet<>();
        }
        return categories;
    }

    public Recipe addCategory(Category category) {
        this.getCategories().add(category);

        return this;
    }
}
