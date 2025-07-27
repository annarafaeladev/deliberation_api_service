package br.com.deliberation_api.domain.model.topic;

import br.com.deliberation_api.domain.enums.TimeTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class Session {

    private LocalDateTime openAt;

    private LocalDateTime closeAt;

    @Setter(AccessLevel.PUBLIC)
    private boolean closedManually;

    private TimeTypeEnum timeType;
    private int duration;

    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Session() {
        this.start(TimeTypeEnum.MINUTE, 1);
    }

    public Session(TimeTypeEnum timeType, int duration) {
        this.start(timeType, duration);
    }

    public void start(TimeTypeEnum timeType, int duration) {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.timeType = timeType;
        this.duration = duration;
        this.openAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

