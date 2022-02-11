package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeController(RecipeService recipeService, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeService = recipeService;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @GetMapping(value = "/{id}/show")
    public String show(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.getById(id);

        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }

    @GetMapping(value = "/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute("recipe", recipeService.buildCommandFromId(id));

        return "recipe/form";
    }

    @PostMapping(value = "createOrSave")
    public String createOrSave(
            @ModelAttribute("recipe") RecipeCommand recipeCommand,
            RedirectAttributes redirectAttributes
    ) {
        System.out.println(recipeCommand.getId());
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        redirectAttributes.addFlashAttribute("success", "Recipe was saved");

        return String.format("redirect:/recipe/%d/show", savedCommand.getId());
    }

    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeService.deleteById(id);

        return "redirect:/index";
    }
}
