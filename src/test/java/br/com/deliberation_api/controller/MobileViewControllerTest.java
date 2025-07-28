package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.interfaces.service.MobileViewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = MobileViewController.class)
@ContextConfiguration(classes = {MobileViewController.class})
class MobileViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MobileViewService mobileViewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public MobileViewService mobileViewService() {
            return mock(MobileViewService.class);
        }
    }

    @Test
    void getPage_ShouldReturnOk() throws Exception {
        when(mobileViewService.getPage(anyString())).thenReturn(new ViewTemplateResponseDTO());

        mockMvc.perform(get("/v1/mobile/pages/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mobileViewService).getPage("123");
    }

    @Test
    void getTopics_ShouldReturnOk() throws Exception {
        when(mobileViewService.getTopics(anyString())).thenReturn(new ViewTemplateResponseDTO());

        mockMvc.perform(get("/v1/mobile/pages/123/topics")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mobileViewService).getTopics("123");
    }

    @Test
    void getPageOptions_ShouldReturnOk() throws Exception {
        when(mobileViewService.getPageOptions(anyString(), anyString())).thenReturn(new ViewTemplateResponseDTO());

        mockMvc.perform(get("/v1/mobile/pages/123/topics/456")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mobileViewService).getPageOptions("123", "456");
    }

    @Test
    void getPageOption_ShouldReturnOk() throws Exception {
        when(mobileViewService.getPageOptionByOptionId(anyString(), anyString(), anyString())).thenReturn(new ViewTemplateResponseDTO());

        mockMvc.perform(get("/v1/mobile/pages/123/topics/456/options/789")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mobileViewService).getPageOptionByOptionId("123", "456", "789");
    }

    @Test
    void getProfileDetails_ShouldReturnOk() throws Exception {
        when(mobileViewService.getProfilePage(anyString(), anyString())).thenReturn(new ViewTemplateResponseDTO());

        mockMvc.perform(get("/v1/mobile/pages/123/profile/555")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mobileViewService).getProfilePage("123", "555");
    }
}
