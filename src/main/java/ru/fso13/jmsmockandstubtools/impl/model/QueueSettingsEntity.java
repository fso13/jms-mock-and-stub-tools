package ru.fso13.jmsmockandstubtools.impl.model;


import jakarta.persistence.*;
import lombok.Data;
import ru.fso13.jmsmockandstubtools.api.QueueSettingName;

@Entity
@Data
public class QueueSettingsEntity {
    @Id
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private QueueSettingName name;
    @Column
    private String value;
    @ManyToOne
    @JoinColumn(name = "queue_id", nullable = false)
    private QueueEntity queue;
}
