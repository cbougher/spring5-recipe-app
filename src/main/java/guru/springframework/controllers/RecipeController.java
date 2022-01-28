package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.RecipeNotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String show(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.getById(id);

        if(recipe == null) throw new RecipeNotFoundException("That recipe doesn't exist");

        model.addAttribute("recipe", recipe);

        return "recipe/show";
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest req, RecipeNotFoundException ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");

        return mav;
    }
}
