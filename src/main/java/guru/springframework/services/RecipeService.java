package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public interface RecipeService {
    Iterable<Recipe> getAll();
    Recipe getById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
