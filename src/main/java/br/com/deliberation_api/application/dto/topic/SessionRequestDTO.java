package br.com.deliberation_api.application.dto.topic;

import br.com.deliberation_api.domain.enums.TimeTypeEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SessionRequestDTO {
        @NotNull(message = "Time type is require: minute, hour or day")
        private final TimeTypeEnum timeType;

        @Min(value = 1, message = "minimum value 1")
        @Max(value = 60, message = "maximum value 60")
        private final Integer duration;

        public SessionRequestDTO(
                @NotNull(message = "Time type is require: minute, hour or day") TimeTypeEnum timeType,
                @Min(value = 1, message = "minimum value 1") @Max(value = 60, message = "maximum value 60") Integer duration) {
                this.timeType = timeType;
                this.duration = duration;
        }

        public TimeTypeEnum getTimeTypeOrDefault() {
                return timeType != null ? timeType : TimeTypeEnum.MINUTE;
        }

        public int getDurationOrDefault() {
                return duration != null ? duration : 1;
        }
}
