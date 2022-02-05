package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    public static final String DESCRIPTION = "description";
    public static final long LONG_VALUE = 1L;
    public static final BigDecimal FLOAT_VALUE = new BigDecimal("9.99");

    IngredientToIngredientCommand converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(
                new UnitOfMeasureToUnitOfMeasureCommand()
        );
    }

    @Test
    void nullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void convertWithNullUOM() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(LONG_VALUE);
        ingredient.setAmount(FLOAT_VALUE);
        ingredient.setDescription(DESCRIPTION);

        //when
        IngredientCommand command = converter.convert(ingredient);

        //then
        assertNotNull(command);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(LONG_VALUE, command.getId());
        assertEquals(FLOAT_VALUE, command.getAmount());
        assertEquals(DESCRIPTION, command.getDescription());

    }

    @Test
    void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(LONG_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(FLOAT_VALUE);
        ingredient.setUnitOfMeasure(uom);

        IngredientCommand command = converter.convert(ingredient);

        //test ingredient conversion
        assertNotNull(command);
        assertEquals(ingredient.getId(), command.getId());
        assertEquals(ingredient.getDescription(), command.getDescription());
        assertEquals(ingredient.getAmount(), command.getAmount());

        //test embedded UOM conversion
        UnitOfMeasureCommand uomCommand = command.getUnitOfMeasure();
        assertNotNull(uomCommand);
        assertEquals(uom.getId(), uomCommand.getId());
        assertEquals(uom.getDescription(), uomCommand.getDescription());
    }
}