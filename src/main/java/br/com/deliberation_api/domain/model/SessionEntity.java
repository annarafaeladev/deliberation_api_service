package br.com.deliberation_api.domain.model;

import br.com.deliberation_api.domain.enums.TimeTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class SessionEntity {

    private LocalDateTime openAt;

    private LocalDateTime closeAt;

    @Setter(AccessLevel.PUBLIC)
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
        this.start(timeType, duration);
    }

    public void start(TimeTypeEnum timeType, int duration) {
        this.timeType = timeType;
        this.duration = duration;
        this.openAt = LocalDateTime.now();
        this.closeAt = this.timeType.addTo(this.openAt, duration);
    }


    public boolean isAvailable() {
        if (closedManually) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(openAt) && now.isBefore(closeAt);
    }
}

