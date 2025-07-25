package br.com.deliberation_api.domain.model;

import br.com.deliberation_api.domain.enums.TimeTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionEntity {

    private LocalDateTime openAt;

    private LocalDateTime closeAt;

    private boolean closedManually;

    private TimeTypeEnum timeType;
    private int duration;

    public SessionEntity() {
        this.timeType = TimeTypeEnum.MINUTE;
        this.duration = 1;
        this.openAt = LocalDateTime.now();
        this.closeAt = this.timeType.addTo(this.openAt, 1);
    }

    public SessionEntity(TimeTypeEnum timeType, int duration) {
        this.timeType = timeType;
        this.duration = duration;
        this.openAt = LocalDateTime.now();
        this.closeAt = this.timeType.addTo(this.openAt, duration);
    }


    public boolean isAvailable() {
        if (closedManually || openAt == null || closeAt == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(openAt) && now.isBefore(closeAt);
    }
}

