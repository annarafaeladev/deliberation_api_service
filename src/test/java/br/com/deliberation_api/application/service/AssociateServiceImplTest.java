package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
import br.com.deliberation_api.domain.repository.AssociateRepository;
import br.com.deliberation_api.shared.exception.AssociateException;
import br.com.deliberation_api.shared.exception.AssociateNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociateServiceImplTest {

    @Mock
    private AssociateRepository associateRepository;

    @InjectMocks
    private AssociateServiceImpl associateService;

    private AssociateEntity associateEntity;

    @BeforeEach
    void setup() {
        associateEntity = new AssociateEntity("Anna", "12345678900");
        associateEntity.setId("123");
    }

    @Test
    void create_ShouldSaveAndReturnAssociate() {
        var dto = new AssociateCreateDTO("Anna", "263.781.667-81");

        when(associateRepository.existsByDocument("26378166781")).thenReturn(false);
        when(associateRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        AssociateEntity created = associateService.create(dto);

        assertEquals("Anna", created.getName());
        assertEquals("26378166781", created.getDocument());
        verify(associateRepository).save(any());
    }

    @Test
    void create_ShouldThrowException_WhenDocumentAlreadyExists() {
        var dto = new AssociateCreateDTO("Anna", "263.781.667-81");

        when(associateRepository.existsByDocument("26378166781")).thenReturn(true);

        AssociateException ex = assertThrows(AssociateException.class, () -> {
            associateService.create(dto);
        });

        assertEquals("Document already registered", ex.getMessage());
        verify(associateRepository, never()).save(any());
    }

    @Test
    void create_ShouldThrowException_WhenDocumentInvalid() {
        var dto = new AssociateCreateDTO("Anna", "123"); // documento inválido

        when(associateRepository.existsByDocument("123")).thenReturn(false);

        AssociateException ex = assertThrows(AssociateException.class, () -> {
            associateService.create(dto);
        });

        assertEquals("Associate document invalid", ex.getMessage());
        verify(associateRepository, never()).save(any());
    }

    @Test
    void list_ShouldReturnAllAssociates() {
        List<AssociateEntity> associates = List.of(
                new AssociateEntity("John", "11111111111"),
                new AssociateEntity("Jane", "22222222222")
        );

        associates.get(0).setId("1");
        associates.get(1).setId("2");
        when(associateRepository.findAll()).thenReturn(associates);

        List<AssociateEntity> result = associateService.list();

        assertEquals(2, result.size());
        verify(associateRepository).findAll();
    }

    @Test
    void getById_ShouldReturnAssociate_WhenExists() {
        when(associateRepository.findById("123")).thenReturn(Optional.of(associateEntity));

        AssociateEntity found = associateService.getById("123");

        assertEquals("Anna", found.getName());
        verify(associateRepository).findById("123");
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenNotExists() {
        when(associateRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(AssociateNotFoundException.class, () -> associateService.getById("123"));

        verify(associateRepository).findById("123");
    }

    @Test
    void update_ShouldUpdateNameAndDocument() {
        var updateDTO = new AssociateUpdateDTO("Anna Updated", "987.654.321-00");
        when(associateRepository.findById("123")).thenReturn(Optional.of(associateEntity));
        when(associateRepository.existsByDocument("98765432100")).thenReturn(false);
        when(associateRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        AssociateEntity updated = associateService.update("123", updateDTO);

        assertEquals("Anna Updated", updated.getName());
        assertEquals("98765432100", updated.getDocument());
        verify(associateRepository).save(any());
    }

    @Test
    void update_ShouldThrowException_WhenDocumentAlreadyExists() {
        var updateDTO = new AssociateUpdateDTO(null, "987.654.321-00");
        when(associateRepository.findById("123")).thenReturn(Optional.of(associateEntity));
        when(associateRepository.existsByDocument("98765432100")).thenReturn(true);

        AssociateException ex = assertThrows(AssociateException.class, () -> {
            associateService.update("123", updateDTO);
        });

        assertEquals("Document already registered", ex.getMessage());
        verify(associateRepository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteAssociate_WhenExists() {
        when(associateRepository.findById("123")).thenReturn(Optional.of(associateEntity));

        associateService.delete("123");

        verify(associateRepository).delete(associateEntity);
    }

    @Test
    void delete_ShouldThrowNotFoundException_WhenNotExists() {
        when(associateRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(AssociateNotFoundException.class, () -> associateService.delete("123"));

        verify(associateRepository, never()).delete(any());
    }
}

