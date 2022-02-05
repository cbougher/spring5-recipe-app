package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}/ingredient")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model, @PathVariable Long recipeId) {
        log.debug("Showing ingredients for recipe " + recipeId);

        model.addAttribute("recipe", recipeService.buildCommandFromId(recipeId));

        return "recipe/ingredient/index";
    }

    @GetMapping(value = "/{id}/show")
    public String show(Model model, @PathVariable Long id) {
        IngredientCommand command = ingredientService.buildCommandFromId(id);

        model.addAttribute("ingredient", command);

        return "recipe/ingredient/show";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long recipeId, @PathVariable Long id) {
        ingredientService.deleteById(id);

        return "redirect:/recipe/" + recipeId + "/ingredient/";
    }

    @GetMapping(value = "/{id}/update")
    public String update(Model model, @PathVariable Long id) {
        IngredientCommand command = ingredientService.buildCommandFromId(id);
        Set<UnitOfMeasureCommand> uomList = unitOfMeasureService.getAll();

        model.addAttribute("ingredient", command);
        model.addAttribute("uomList", uomList);

        return "recipe/ingredient/form";
    }

    @GetMapping("/new")
    public String newIngredient(Model model, @PathVariable Long recipeId) {
        Set<UnitOfMeasureCommand> uomList = unitOfMeasureService.getAll();
        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(recipeId);
        command.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", command);
        model.addAttribute("uomList", uomList);

        return "recipe/ingredient/form";
    }

    @PostMapping("")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + savedCommand.getRecipeId() +"/ingredient/" + savedCommand.getId() + "/show";
    }
}
