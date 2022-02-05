package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureService = new UnitOfMeasureServiceImpl(
                unitOfMeasureRepository,
                new UnitOfMeasureToUnitOfMeasureCommand()
        );
    }

    @Test
    void getAll() {
        Set<UnitOfMeasure> allUoms = new HashSet<>();
        allUoms.add(UnitOfMeasure.builder().id(1L).build());
        allUoms.add(UnitOfMeasure.builder().id(2L).build());

        when(unitOfMeasureRepository.findAll()).thenReturn(allUoms);

        Set<UnitOfMeasureCommand> commands = unitOfMeasureService.getAll();

        assertEquals(allUoms.size(), commands.size());
        verify(unitOfMeasureRepository).findAll();
    }
}