package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    public static final String RECIPE_NOTES = "Notes";
    public static final long LONG_VALUE = 1L;

    NotesCommandToNotes converter;

    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void nullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        NotesCommand command = new NotesCommand();
        command.setId(LONG_VALUE);
        command.setRecipeNotes(RECIPE_NOTES);

        Notes notes = converter.convert(command);

        assertNotNull(notes);
        assertEquals(command.getId(), notes.getId());
        assertEquals(command.getRecipeNotes(), notes.getRecipeNotes());
    }
}