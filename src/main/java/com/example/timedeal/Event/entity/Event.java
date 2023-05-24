package com.example.timedeal.Event.entity;

import com.example.timedeal.common.entity.baseEntity;
import com.example.timedeal.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Event extends baseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-id-generator")
    @GenericGenerator(
            name = "message-id-generator",
            strategy = "sequence",
            parameters = {@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "hibernate_sequence"),
                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1000"),
                    @org.hibernate.annotations.Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lotl")}
    )
    @Column(name = "event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private User createdBy;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<PublishEvent> publishEvents = new ArrayList<>();

    private String eventType;

    @Builder
    public Event(Long id, User createdBy, List<PublishEvent> publishEvents, String eventType) {
        this.id = id;
        this.createdBy = createdBy;
        this.publishEvents = publishEvents;
        this.eventType = eventType;
    }

    // TODO 로직 수정해야 함.
    public void publish(PublishEvent publishEvent) {
        publishEvent.setEvent(this);
        this.publishEvents.add(publishEvent);
    }
}
