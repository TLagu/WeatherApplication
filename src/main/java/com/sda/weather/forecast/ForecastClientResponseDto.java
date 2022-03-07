package com.sda.weather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ForecastClientResponseDto {

    private List<ForecastDaily> daily;

    @Data
    public static class ForecastDaily {
        @JsonProperty("dt")
        private Long date;
        private Temperature temp;
        private Integer pressure;
        private Integer humidity;
        @JsonProperty("wind_speed")
        private Float windSpeed;
        @JsonProperty("wind_deg")
        private Integer windDeg;

        @Data
        public static class Temperature {
            private Float day;
        }
    }
}