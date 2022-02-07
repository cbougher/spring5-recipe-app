package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryCommandConnverter;
    private final IngredientToIngredientCommand ingredientCommandConnverter;
    private final NotesToNotesCommand notesCommandConnverter;

    public RecipeToRecipeCommand(
            CategoryToCategoryCommand categoryCommandConnverter,
            IngredientToIngredientCommand ingredientCommandConnverter,
            NotesToNotesCommand notesCommandConnverter
    ) {
        this.categoryCommandConnverter = categoryCommandConnverter;
        this.ingredientCommandConnverter = ingredientCommandConnverter;
        this.notesCommandConnverter = notesCommandConnverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setImage(source.getImage());
        if (source.getNotes() != null) {
            recipeCommand.setNotes(notesCommandConnverter.convert(source.getNotes()));
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().forEach(category ->
                    recipeCommand.getCategories().add(categoryCommandConnverter.convert(category))
            );
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients().forEach(ingredient ->
                    recipeCommand.getIngredients().add(ingredientCommandConnverter.convert(ingredient))
            );
        }

        return recipeCommand;
    }
}
