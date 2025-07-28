package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.interfaces.service.MobileViewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MobileViewController.class)
class MobileViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MobileViewService mobileViewService;

    @Test
    void shouldReturnPageById() throws Exception {
        ViewTemplateResponseDTO response = new ViewTemplateResponseDTO();
        Mockito.when(mobileViewService.getPage("1")).thenReturn(response);

        mockMvc.perform(get("/v1/mobile/pages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnTopicsByPageId() throws Exception {
        ViewTemplateResponseDTO response = new ViewTemplateResponseDTO();
        Mockito.when(mobileViewService.getTopics("1")).thenReturn(response);

        mockMvc.perform(get("/v1/mobile/pages/1/topics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnPageOptions() throws Exception {
        ViewTemplateResponseDTO response = new ViewTemplateResponseDTO();
        Mockito.when(mobileViewService.getPageOptions("1", "2")).thenReturn(response);

        mockMvc.perform(get("/v1/mobile/pages/1/topics/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnPageOptionByOptionId() throws Exception {
        ViewTemplateResponseDTO response = new ViewTemplateResponseDTO();
        Mockito.when(mobileViewService.getPageOptionByOptionId("1", "2", "3")).thenReturn(response);

        mockMvc.perform(get("/v1/mobile/pages/1/topics/2/options/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnProfilePage() throws Exception {
        ViewTemplateResponseDTO response = new ViewTemplateResponseDTO();
        Mockito.when(mobileViewService.getProfilePage("1", "99")).thenReturn(response);

        mockMvc.perform(get("/v1/mobile/pages/1/profile/99"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
