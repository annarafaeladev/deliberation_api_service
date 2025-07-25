package br.com.deliberation_api.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDateTime;

public enum TimeTypeEnum {
    HOUR,
    MINUTE,
    DAY;

    @JsonCreator
    public static TimeTypeEnum fromString(String value) {
        return TimeTypeEnum.valueOf(value.toUpperCase());
    }

    public LocalDateTime addTo(LocalDateTime dateTime, long amount) {
        return switch (this) {
            case HOUR -> dateTime.plusHours(amount);
            case MINUTE -> dateTime.plusMinutes(amount);
            case DAY -> dateTime.plusDays(amount);
        };
    }
}

