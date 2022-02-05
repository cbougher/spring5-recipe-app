package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
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

    IngredientServiceImplTest(IngredientToIngredientCommand ingredientToIngredientCommand) {
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
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);

        when(ingredientRepository.save(any())).thenReturn(ingredient);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
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