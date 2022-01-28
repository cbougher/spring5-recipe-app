package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {
    final static String DESCRIPTION = "description";
    final static Long LONG_VALUE = 1L;

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void nullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(LONG_VALUE);
        category.setDescription(DESCRIPTION);

        CategoryCommand command = converter.convert(category);

        assertNotNull(command);
        assertEquals(category.getId(), command.getId());
        assertEquals(category.getDescription(), command.getDescription());
    }
}