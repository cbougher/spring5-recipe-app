package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
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

        Set<Recipe> foundRecipes = new HashSet<>();
        Recipe foundRecipe = recipeService.getById(recipe.getId());

        assertNotNull(foundRecipe);
        assertEquals(recipe.getId(), foundRecipe.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(anyLong());
    }
}