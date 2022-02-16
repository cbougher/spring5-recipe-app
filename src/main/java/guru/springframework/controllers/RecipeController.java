package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.PrintWriter;
import java.io.StringWriter;

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
            @Valid
            @ModelAttribute("recipe") RecipeCommand recipeCommand,
//            RedirectAttributes redirectAttributes,
            BindingResult bindingResult
    ) {
        System.out.println(recipeCommand.getId());

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
//            redirectAttributes.addFlashAttribute("error", "Recipe has errors");

            return "recipe/form";
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

//        redirectAttributes.addFlashAttribute("success", "Recipe was saved");

        return String.format("redirect:/recipe/%d/show", savedCommand.getId());
    }

    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeService.deleteById(id);

        return "redirect:/index";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView recipeNotFound(NotFoundException exception) {
        // get the stacktrace into a string
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String stacktrace = sw.toString();

        // setup the view and model
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recipe/404error");
        modelAndView.getModel().put("exception", exception);
        modelAndView.getModel().put("stacktrace", stacktrace);

        log.debug("404 encountered");

        return modelAndView;
    }
}
