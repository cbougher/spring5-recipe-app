package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {
    final static String DESCRIPTION = "description";
    final static Long LONG_VALUE = 1L;

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void nullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        CategoryCommand command = new CategoryCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        Category category = converter.convert(command);

        assertNotNull(category);
        assertEquals(command.getId(), category.getId());
        assertEquals(command.getDescription(), category.getDescription());
    }
}