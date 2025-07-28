package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.dto.associate.AssociateCreateDTO;
import br.com.deliberation_api.application.dto.associate.AssociateUpdateDTO;
import br.com.deliberation_api.domain.model.associate.AssociateEntity;
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

        AssociateEntity expected = new AssociateEntity();
        expected.setId("1");
        expected.setName("João");
        expected.setDocument("12345678900");

        when(associateService.create(dto)).thenReturn(expected);

        ResponseEntity<AssociateEntity> response = associateController.create(dto);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(associateService).create(dto);
    }

    @Test
    void testList() {
        AssociateEntity a1 = new AssociateEntity();
        a1.setId("1");

        AssociateEntity a2 = new AssociateEntity();
        a2.setId("2");

        when(associateService.list()).thenReturn(List.of(a1, a2));

        ResponseEntity<List<AssociateEntity>> response = associateController.list();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(associateService).list();
    }

    @Test
    void testGetById() {
        String id = "1";
        AssociateEntity expected = new AssociateEntity();
        expected.setId(id);

        when(associateService.getById(id)).thenReturn(expected);

        ResponseEntity<AssociateEntity> response = associateController.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(associateService).getById(id);
    }

    @Test
    void testUpdate() {
        String id = "1";
        AssociateUpdateDTO dto = new AssociateUpdateDTO("Novo nome" ,"97186681522");

        AssociateEntity updated = new AssociateEntity();
        updated.setId(id);
        updated.setName("Novo Nome");

        when(associateService.update(id, dto)).thenReturn(updated);

        ResponseEntity<AssociateEntity> response = associateController.update(id, dto);

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
