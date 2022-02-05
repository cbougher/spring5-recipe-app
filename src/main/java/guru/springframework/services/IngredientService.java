package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

public interface IngredientService {
    Ingredient getById(Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long id);

    IngredientCommand buildCommandFromId(Long id);
}
