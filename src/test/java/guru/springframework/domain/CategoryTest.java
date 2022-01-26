package guru.springframework.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4L;

        category.setId(idValue);

        Assertions.assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() {
        String description = "My Category";

        category.setDescription(description);

        Assertions.assertEquals(category.getDescription(), description);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);

        category.setRecipes(recipes);

        Assertions.assertEquals(recipes.size(), category.getRecipes().size());
    }
}