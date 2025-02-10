package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.DisclaimerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DisclaimerController.class)
@AutoConfigureWebMvc
class DisclaimerControllerTest {

    private static final String BASE_PATH = "/api/disclaimer";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DisclaimerService disclaimerService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void postDisclaimer_Success() throws Exception {
        // Dado
        GetDisclaimerDto getDTO = new GetDisclaimerDto(1, 1, "Aca va un descargo del por qeu no deberia ir una multa a mi lote", LocalDateTime.now(), 1, LocalDateTime.now(), 1);
        PostDisclaimerDto postDTO = new PostDisclaimerDto(1, 1, "Aca va un descargo del por qeu no deberia ir una multa a mi lote");

        // Cuando
        when(disclaimerService.postDisclimer(any(PostDisclaimerDto.class))).thenReturn(getDTO);

        // Entonces
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH +"/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isOk()) // Espera un código 200 OK
                .andExpect(jsonPath("$.id").value(getDTO.getId()))
                .andExpect(jsonPath("$.fineId").value(getDTO.getFineId()))
                .andExpect(jsonPath("$.disclaimer").value(getDTO.getDisclaimer()));

        verify(disclaimerService, times(1)).postDisclimer(any(PostDisclaimerDto.class));
    }

    @Test
    void postDisclaimer_BadRequestIT() throws Exception {
        // Dado
        PostDisclaimerDto postDTO = new PostDisclaimerDto(999, 1, "descripcion incorrecta");

        // Cuando
        when(disclaimerService.postDisclimer(any(PostDisclaimerDto.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        // Entonces
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH+"/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDTO)))
                .andExpect(status().isBadRequest()); // Espera un código 400 Bad Request

        verify(disclaimerService, times(1)).postDisclimer(any(PostDisclaimerDto.class));
    }

}
