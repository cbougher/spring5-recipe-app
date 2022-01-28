package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    public static final String DESCRIPTION = "description";
    public static final long LONG_VALUE = 1L;
    public static final BigDecimal FLOAT_VALUE = new BigDecimal("9.99");

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        UnitOfMeasureCommandToUnitOfMeasure uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
        converter = new IngredientCommandToIngredient(uomConverter);
    }

    @Test
    void nullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convertWithNullUOM() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(LONG_VALUE);
        command.setAmount(FLOAT_VALUE);
        command.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();


        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(LONG_VALUE, ingredient.getId());
        assertEquals(FLOAT_VALUE, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());

    }

    @Test
    void convert() {
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(LONG_VALUE);
        uomCommand.setDescription(DESCRIPTION);

        IngredientCommand command = new IngredientCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);
        command.setAmount(FLOAT_VALUE);
        command.setUnitOfMeasure(uomCommand);

        Ingredient ingredient = converter.convert(command);

        //test ingredient conversion
        assertNotNull(ingredient);
        assertEquals(command.getId(), ingredient.getId());
        assertEquals(command.getDescription(), ingredient.getDescription());
        assertEquals(command.getAmount(), ingredient.getAmount());

        //test embedded UOM conversion
        UnitOfMeasure uom = ingredient.getUnitOfMeasure();
        assertNotNull(uom);
        assertEquals(uomCommand.getId(), uom.getId());
        assertEquals(uomCommand.getDescription(), uom.getDescription());
    }
}