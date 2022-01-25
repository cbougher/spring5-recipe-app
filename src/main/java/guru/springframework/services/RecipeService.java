package guru.springframework.services;

import guru.springframework.domain.Recipe;

public interface RecipeService {
    Iterable<Recipe> getAll();
}
