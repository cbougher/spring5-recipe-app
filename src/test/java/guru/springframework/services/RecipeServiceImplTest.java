package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.*;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    public RecipeServiceImplTest() {
        recipeCommandToRecipe = new RecipeCommandToRecipe(
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(
                        new UnitOfMeasureCommandToUnitOfMeasure()
                ),
                new NotesCommandToNotes()
        );

        recipeToRecipeCommand = new RecipeToRecipeCommand(
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(
                        new UnitOfMeasureToUnitOfMeasureCommand()
                ),
                new NotesToNotesCommand()
        );
    }

    @BeforeEach
    public void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getAll() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);

        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> foundRecipes = new HashSet<>();
        recipeService.getAll().forEach(foundRecipes::add);

        assertEquals(recipes.size(), foundRecipes.size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getById() {
        Recipe recipe = Recipe.builder().id(1L).build();

        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        Recipe foundRecipe = recipeService.getById(recipe.getId());

        assertNotNull(foundRecipe);
        assertEquals(recipe.getId(), foundRecipe.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void saveIngredientCommand() {
        RecipeCommand command = new RecipeCommand();
        command.setId(3L);
        Recipe recipe = new Recipe();
        recipe.setId(3L);

        when(recipeRepository.save(any())).thenReturn(recipe);

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
    }

    @Test
    void deleteById() {
        recipeService.deleteById(1L);

        verify(recipeRepository).deleteById(anyLong());
    }

    @Test
    void buildCommandFromId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        RecipeCommand command = recipeService.buildCommandFromId(1L);

        assertNotNull(command);
        assertEquals(recipe.getId(), command.getId());
    }
}