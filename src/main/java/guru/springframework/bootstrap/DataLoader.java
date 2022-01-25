package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Loading recipes");

        recipeRepository.saveAll(createRecipes());
    }

    private List<Recipe> createRecipes() {
        log.debug("Creating recipes");

        List<Recipe> recipes = new ArrayList<>();

        Optional<Category> categoryOptionalMexican = categoryRepository.findByDescription("Mexican");
        Optional<Category> categoryOptionalAmerican = categoryRepository.findByDescription("American");

        Optional<UnitOfMeasure> unitOfMeasureOptionalEach = unitOfMeasureRepository.findByDescription("Each");
        Optional<UnitOfMeasure> unitOfMeasureOptionalTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        Optional<UnitOfMeasure> unitOfMeasureOptionalTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        Optional<UnitOfMeasure> unitOfMeasureOptionalPinch = unitOfMeasureRepository.findByDescription("Pinch");
        Optional<UnitOfMeasure> unitOfMeasureOptionalClove = unitOfMeasureRepository.findByDescription("Clove");
        Optional<UnitOfMeasure> unitOfMeasureOptionalCup = unitOfMeasureRepository.findByDescription("Cup");
        Optional<UnitOfMeasure> unitOfMeasureOptionalPint = unitOfMeasureRepository.findByDescription("Pint");

        Recipe perfectGuacamole = new Recipe();

        Notes pgNotes = new Notes();
        pgNotes.setRecipeNotes(
            "Be careful handling chilis! If using, it's best to wear food-safe gloves. " +
            "If no gloves are available, wash your hands thoroughly after handling, " +
            "and do not touch your eyes or the area near your eyes for several hours afterwards." +
            "Chilling tomatoes hurts their flavor. " +
            "So, if you want to add chopped tomato to your guacamole, add it just before serving."
        );

        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setNotes(pgNotes);
        perfectGuacamole.setServings(3);
        perfectGuacamole.setSource("www.simplyrecipes.com");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setDirections(
                "Cut the avocados in half. Remove the pit. Score the inside of " +
                "the avocado with a blunt knife and scoop out the flesh with a spoon. " +
                "(See How to Cut and Peel an Avocado.) Place in a bowl." +
                "\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! " +
                "The guacamole should be a little chunky.)" +
                "\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime " +
                "juice will provide some balance to the richness of the avocado and will " +
                "help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers " +
                "vary individually in their spiciness. So, start with a half of one chili " +
                "pepper and add more to the guacamole to your desired degree of heat." +
                "\n" +
                "Remember that much of this is done to taste because of the variability " +
                "in the fresh ingredients. Start with this recipe and adjust to your taste." +
                "\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the " +
                "guacamole and press down to cover it to prevent air reaching it. " +
                "(The oxygen in the air causes oxidation which will turn the guacamole brown.)" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your " +
                "choice of store-bought tortilla chips or make your own homemade tortilla chips." +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days."
        );
        perfectGuacamole.addCategory(categoryOptionalAmerican.get());
        perfectGuacamole.addCategory(categoryOptionalMexican.get());

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "Ripe avocado",
                new BigDecimal(2.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "Salt, plus more to taste",
                new BigDecimal(0.25),
                unitOfMeasureOptionalTeaspoon.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "fresh lime or lemon juice",
                new BigDecimal(1.0),
                unitOfMeasureOptionalTablespoon.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "minced red onion or thinly sliced green onion",
                new BigDecimal(3.0),
                unitOfMeasureOptionalTablespoon.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "serrano (or jalapeño) chilis, stems and seeds removed, minced",
                new BigDecimal(2.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "cilantro (leaves and tender stems), finely chopped",
                new BigDecimal(2.0),
                unitOfMeasureOptionalTablespoon.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "freshly ground black pepper",
                new BigDecimal(1.0),
                unitOfMeasureOptionalPinch.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "ripe tomato, chopped (optional)",
                new BigDecimal(0.5),
                unitOfMeasureOptionalEach.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "Red radish or jicama slices for garnish (optional)",
                new BigDecimal(1.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        perfectGuacamole.addIngrediant(
            new Ingredient(
                "Tortilla chips, to serve",
                new BigDecimal(1.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        log.debug("Created Perfect Guacamole");

        recipes.add(perfectGuacamole);

        Recipe spicyGrilledChickenTacos = new Recipe();

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes(
            "Look for ancho chile powder with the Mexican ingredients at your " +
            "grocery store, on buy it online. (If you can't find ancho chili " +
            "powder, you replace the ancho chili, the oregano, and the cumin " +
            "with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)"
        );

        spicyGrilledChickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        spicyGrilledChickenTacos.setDifficulty(Difficulty.MODERATE);
        spicyGrilledChickenTacos.setPrepTime(20);
        spicyGrilledChickenTacos.setCookTime(15);
        spicyGrilledChickenTacos.setNotes(tacoNotes);
        spicyGrilledChickenTacos.setServings(6);
        spicyGrilledChickenTacos.setSource("www.simplyrecipes.com");
        spicyGrilledChickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        spicyGrilledChickenTacos.setDirections(
            "Prepare a gas or charcoal grill for medium-high, direct heat" +
            "\n" +
            "In a large bowl, stir together the chili powder, oregano, cumin, " +
            "sugar, salt, garlic and orange zest. Stir in the orange juice and " +
            "olive oil to make a loose paste. Add the chicken to the bowl and " +
            "toss to coat all over." +
            "\n" +
            "Set aside to marinate while the grill heats and you prepare the rest of the toppings." +
            "\n" +
            "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into " +
            "the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes." +
            "\n" +
            "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. "+
            "As soon as you see pockets of the air start to puff up in the tortilla, turn it " +
            "with tongs and heat for a few seconds on the other side." +
            "\n" +
            "Wrap warmed tortillas in a tea towel to keep them warm until serving." +
            "Slice the chicken into strips. On each tortilla, place a small handful " +
            "of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, " +
            "and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges."
        );
        spicyGrilledChickenTacos.addCategory(categoryOptionalAmerican.get());
        spicyGrilledChickenTacos.addCategory(categoryOptionalMexican.get());

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "ancho chili powder",
                new BigDecimal(2.0),
                unitOfMeasureOptionalTablespoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "dried oregano",
                new BigDecimal(1.0),
                unitOfMeasureOptionalTeaspoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "dried oregano",
                new BigDecimal(1.0),
                unitOfMeasureOptionalTeaspoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "sugar",
                new BigDecimal(1.0),
                unitOfMeasureOptionalTeaspoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "salt" ,
                new BigDecimal(0.5),
                unitOfMeasureOptionalTeaspoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "garlic, finely chopped",
                new BigDecimal(1.0),
                unitOfMeasureOptionalClove.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "finely grated orange zest",
                new BigDecimal(0.5),
                unitOfMeasureOptionalTablespoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "fresh-squeezed orange juice",
                new BigDecimal(3.0),
                unitOfMeasureOptionalTablespoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "oilve oil",
                new BigDecimal(2.0),
                unitOfMeasureOptionalTablespoon.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "skinless, boneless chicken thighs (1 1/4 pounds)",
                new BigDecimal(6.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "small corn tortillas",
                new BigDecimal(8.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "packed baby arugula (3 ounces)",
                new BigDecimal(3.0),
                unitOfMeasureOptionalCup.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "medium ripe avocados, sliced",
                new BigDecimal(2.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "radishes, thinly sliced",
                new BigDecimal(4.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "cherry tomatoes, halved",
                new BigDecimal(0.5),
                unitOfMeasureOptionalPint.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "red onion, thinly sliced",
                new BigDecimal(0.25),
                unitOfMeasureOptionalEach.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "Roughly chopped cilantro",
                new BigDecimal(1.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "sour cream thinned with 1/4 cup milk",
                new BigDecimal(0.5),
                unitOfMeasureOptionalCup.get()
            )
        );

        spicyGrilledChickenTacos.addIngrediant(
            new Ingredient(
                "lime, cut into wedges",
                new BigDecimal(1.0),
                unitOfMeasureOptionalEach.get()
            )
        );

        log.debug("Created Spicy Grilled Chicken Tacos");

        recipes.add(spicyGrilledChickenTacos);

        return recipes;
    }
}
