package br.com.deliberation_api.application.service;

import br.com.deliberation_api.application.view.dto.component.ButtonScreenDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateRequestDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.domain.model.view.ViewTemplateEntity;
import br.com.deliberation_api.domain.repository.ViewTemplateRepository;
import br.com.deliberation_api.shared.exception.ViewNotFoundException;
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
class ViewTemplateServiceImplTest {

    @Mock
    private ViewTemplateRepository repository;

    @InjectMocks
    private ViewTemplateServiceImpl service;

    private ViewTemplateEntity entity;
    private ViewTemplateRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        entity = new ViewTemplateEntity();
        entity.setName("Test View");
        entity.setType("typeA");
        entity.setTitle("Title Test");
        entity.setText("Some text");
        entity.setButtonOk(new ButtonScreenDTO("OK"));
        entity.setButtonCancel(new ButtonScreenDTO("Cancel"));
        entity.setVisible(true);
        entity.setElements(List.of(new ButtonScreenDTO("element1"), new ButtonScreenDTO("element2")));

        requestDTO = new ViewTemplateRequestDTO();
        requestDTO.setName("Test View");
        requestDTO.setType("typeA");
        requestDTO.setTitle("Title Test");
        requestDTO.setText("Some text");
        requestDTO.setButtonOk(new ButtonScreenDTO("OK"));
        requestDTO.setButtonCancel(new ButtonScreenDTO("Cancel"));
        requestDTO.setVisible(true);
        requestDTO.setElements(List.of(new ButtonScreenDTO("element1"), new ButtonScreenDTO("element2")));

    }

    @Test
    void create_ShouldSaveAndReturnDTO_WhenNameNotExists() {
        when(repository.existsByName(requestDTO.getName())).thenReturn(false);
        when(repository.save(any(ViewTemplateEntity.class))).thenReturn(entity);

        ViewTemplateResponseDTO response = service.create(requestDTO);

        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
        assertEquals(requestDTO.getName(), response.getName());

        verify(repository).existsByName(requestDTO.getName());
        verify(repository).save(any(ViewTemplateEntity.class));
    }

    @Test
    void create_ShouldThrowException_WhenNameExists() {
        when(repository.existsByName(requestDTO.getName())).thenReturn(true);

        assertThrows(ViewNotFoundException.class, () -> service.create(requestDTO));

        verify(repository).existsByName(requestDTO.getName());
        verify(repository, never()).save(any());
    }

    @Test
    void findAll_ShouldReturnListOfDTOs() {
        when(repository.findAll()).thenReturn(List.of(entity));

        List<ViewTemplateResponseDTO> list = service.findAll();

        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(entity.getName(), list.get(0).getName());

        verify(repository).findAll();
    }

    @Test
    void findById_ShouldReturnDTO_WhenFound() {
        when(repository.findById("template_testview")).thenReturn(Optional.of(entity));

        ViewTemplateResponseDTO dto = service.findById("template_testview");

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());

        verify(repository).findById("template_testview");
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(repository.findById("template_testview")).thenReturn(Optional.empty());

        assertThrows(ViewNotFoundException.class, () -> service.findById("template_testview"));

        verify(repository).findById("template_testview");
    }

    @Test
    void update_ShouldModifyAndReturnDTO_WhenFound() {
        when(repository.findById("template_testview")).thenReturn(Optional.of(entity));
        when(repository.save(any(ViewTemplateEntity.class))).thenReturn(entity);

        requestDTO.setTitle("Updated Title");
        ViewTemplateResponseDTO dto = service.update("template_testview", requestDTO);

        assertNotNull(dto);
        assertEquals("Updated Title", dto.getTitle());

        verify(repository).findById("template_testview");
        verify(repository).save(entity);
    }

    @Test
    void update_ShouldThrowException_WhenNotFound() {
        when(repository.findById("template_testview")).thenReturn(Optional.empty());

        assertThrows(ViewNotFoundException.class, () -> service.update("template_testview", requestDTO));

        verify(repository).findById("template_testview");
        verify(repository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteEntity_WhenFound() {
        when(repository.findById("template_testview")).thenReturn(Optional.of(entity));

        service.delete("template_testview");

        verify(repository).findById("template_testview");
        verify(repository).delete(entity);
    }

    @Test
    void delete_ShouldThrowException_WhenNotFound() {
        when(repository.findById("template_testview")).thenReturn(Optional.empty());

        assertThrows(ViewNotFoundException.class, () -> service.delete("template_testview"));

        verify(repository).findById("template_testview");
        verify(repository, never()).delete(any());
    }
}
