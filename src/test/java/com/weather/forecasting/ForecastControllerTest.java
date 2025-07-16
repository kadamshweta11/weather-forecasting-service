package com.weather.forecasting;

import com.weather.forecasting.controller.ForecastController;
import com.weather.forecasting.model.ForecastResponse;
import com.weather.forecasting.service.ForecastService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ForecastController.class)
public class ForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForecastService forecastService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetForecastByCoordinates() throws Exception {
        ForecastResponse mockResponse = new ForecastResponse(123456, 20.5, 19.0, 80);
        when(forecastService.getForecastByCoordinates(52.52, 13.405)).thenReturn(mockResponse);

        mockMvc.perform(get("/forecast?lat=52.52&lon=13.405"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temp").value(20.5));
    }
    @Test
    void testUnauthorizedRequest() throws Exception {
    mockMvc.perform(get("/forecast"))
           .andExpect(status().isUnauthorized());
    }
    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/forecast?lat=52.52&lon=13.405"))
            .andExpect(status().isUnauthorized());
    }

}
