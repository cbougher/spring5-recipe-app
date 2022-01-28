package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @InjectMocks
    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @Test
    void testMockMvc() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    void getIndexPage() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(Recipe.builder().id(1L).build());
        recipes.add(Recipe.builder().id(2L).build());

        Mockito.when(recipeService.getAll()).thenReturn(recipes);

        ArgumentCaptor<Iterable<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Iterable.class);

        Assertions.assertEquals("index", indexController.getIndexPage(model));
        Mockito.verify(recipeService, Mockito.times(1)).getAll();
        Mockito.verify(
                model,
                Mockito.times(1)
        ).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());

        Set<Recipe> capturedRecipes = new HashSet<>();
        argumentCaptor.getValue().forEach(capturedRecipes::add);

        assertEquals(2, capturedRecipes.size());
    }
}