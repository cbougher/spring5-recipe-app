package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConnverter;
    private final IngredientCommandToIngredient ingredientConnverter;
    private final NotesCommandToNotes notesConnverter;

    public RecipeCommandToRecipe(
            CategoryCommandToCategory categoryConnverter,
            IngredientCommandToIngredient ingredientConnverter,
            NotesCommandToNotes notesConnverter
    ) {
        this.categoryConnverter = categoryConnverter;
        this.ingredientConnverter = ingredientConnverter;
        this.notesConnverter = notesConnverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        if (source.getNotes() != null) {
            recipe.setNotes(notesConnverter.convert(source.getNotes()));
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().forEach(category ->
                    recipe.getCategories().add(categoryConnverter.convert(category))
            );
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients().forEach(ingredient ->
                    recipe.getIngredients().add(ingredientConnverter.convert(ingredient))
            );
        }

        return recipe;
    }
}
