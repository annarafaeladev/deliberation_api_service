package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateResponseDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.interfaces.service.AssociateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssociateControllerTest {

    @Mock
    private AssociateService associateService;

    @InjectMocks
    private AssociateController associateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        AssociateCreateDTO dto = new AssociateCreateDTO("João", "75813484400");

        AssociateResponseDTO expected = new AssociateResponseDTO();
        expected.setId("1");
        expected.setName("João");
        expected.setDocument("12345678900");

        when(associateService.create(dto)).thenReturn(expected);

        ResponseEntity<AssociateResponseDTO> response = associateController.create(dto);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(associateService).create(dto);
    }

    @Test
    void testList() {
        AssociateResponseDTO a1 = new AssociateResponseDTO();
        a1.setId("1");

        AssociateResponseDTO a2 = new AssociateResponseDTO();
        a2.setId("2");

        when(associateService.list()).thenReturn(List.of(a1, a2));

        ResponseEntity<List<AssociateResponseDTO>> response = associateController.list();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(associateService).list();
    }


    @Test
    void testGetTopicById() {
        String id = "1";
        AssociateResponseDTO expected = new AssociateResponseDTO();
        expected.setId(id);

        when(associateService.getTopicById(id)).thenReturn(expected);

        ResponseEntity<AssociateResponseDTO> response = associateController.getTopicById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(associateService).getTopicById(id);
    }

    @Test
    void testUpdate() {
        String id = "1";
        AssociateUpdateDTO dto = new AssociateUpdateDTO("Novo nome" ,"97186681522");

        AssociateResponseDTO updated = new AssociateResponseDTO();
        updated.setId(id);
        updated.setName("Novo Nome");

        when(associateService.update(id, dto)).thenReturn(updated);

        ResponseEntity<AssociateResponseDTO> response = associateController.update(id, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Novo Nome", response.getBody().getName());
        verify(associateService).update(id, dto);
    }

    @Test
    void testDelete() {
        String id = "1";

        doNothing().when(associateService).delete(id);

        ResponseEntity<Void> response = associateController.delete(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(associateService).delete(id);
    }
}
