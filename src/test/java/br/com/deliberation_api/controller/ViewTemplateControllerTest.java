package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.view.dto.structure.ViewTemplateRequestDTO;
import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.interfaces.service.ViewTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "mongock.enabled=false"
})
@AutoConfigureMockMvc
class ViewTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViewTemplateService viewTemplateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturnCreatedView() throws Exception {
        ViewTemplateRequestDTO requestDTO = new ViewTemplateRequestDTO();
        requestDTO.setName("Page");
        requestDTO.setType("FORMULARIO");

        ViewTemplateResponseDTO responseDTO = new ViewTemplateResponseDTO();
        responseDTO.setId("123");

        Mockito.when(viewTemplateService.create(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/v1/views")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void findAll_shouldReturnListOfViews() throws Exception {
        ViewTemplateResponseDTO dto1 = new ViewTemplateResponseDTO();
        dto1.setId("1");

        ViewTemplateResponseDTO dto2 = new ViewTemplateResponseDTO();
        dto2.setId("2");

        Mockito.when(viewTemplateService.findAll()).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/v1/views"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    void findById_shouldReturnView() throws Exception {
        ViewTemplateResponseDTO dto = new ViewTemplateResponseDTO();
        dto.setId("abc123");

        Mockito.when(viewTemplateService.findById("abc123")).thenReturn(dto);

        mockMvc.perform(get("/v1/views/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"));
    }

    @Test
    void update_shouldReturnUpdatedView() throws Exception {
        ViewTemplateRequestDTO requestDTO = new ViewTemplateRequestDTO();
        requestDTO.setName("Page");
        requestDTO.setType("FORMULARIO");
        ViewTemplateResponseDTO responseDTO = new ViewTemplateResponseDTO();
        responseDTO.setId("updatedId");

        Mockito.when(viewTemplateService.update(any(), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/v1/views/updatedId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("updatedId"));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(viewTemplateService).delete("deleteId");

        mockMvc.perform(delete("/v1/views/deleteId"))
                .andExpect(status().isNoContent());
    }
}
