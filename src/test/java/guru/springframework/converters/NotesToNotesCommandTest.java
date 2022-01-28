package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    public static final String RECIPE_NOTES = "Notes";
    public static final long LONG_VALUE = 1L;

    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void nullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(LONG_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        NotesCommand command = converter.convert(notes);

        assertNotNull(command);
        assertEquals(notes.getId(), command.getId());
        assertEquals(notes.getRecipeNotes(), command.getRecipeNotes());
    }
}