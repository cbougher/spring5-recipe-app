package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientServiceImpl ingredientService;

    IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(
                ingredientRepository,
                ingredientCommandToIngredient,
                ingredientToIngredientCommand,
                recipeRepository,
                unitOfMeasureRepository);
    }

    @Test
    void getById() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        Ingredient foundIngredient = ingredientService.getById(1L);

        assertNotNull(foundIngredient);
        assertEquals(ingredient, foundIngredient);
        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void saveIngredientCommand() {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngrediant(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void deleteById() {
        ingredientService.deleteById(1L);

        verify(ingredientRepository).deleteById(anyLong());
    }

    @Test
    void buildCommandFromId() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        IngredientCommand command = ingredientService.buildCommandFromId(1L);

        assertNotNull(command);
        assertEquals(ingredient.getId(), command.getId());
    }
}