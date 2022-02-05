package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter;

    public IngredientToIngredientCommand(
            UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter
    ) {
        this.uomCommandConverter = uomCommandConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        IngredientCommand command = new IngredientCommand();
        command.setId(source.getId());
        if (source.getRecipe() != null) {
            command.setRecipeId(source.getRecipe().getId());
        }
        command.setDescription(source.getDescription());
        command.setAmount(source.getAmount());

        if (source.getUnitOfMeasure() != null) {
            UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
            uomCommand.setId(source.getUnitOfMeasure().getId());
            uomCommand.setDescription(source.getUnitOfMeasure().getDescription());
            command.setUnitOfMeasure(uomCommand);
        }

        return command;
    }
}
