package ru.fso13.jmsmockandstubtools.impl.model;


import jakarta.persistence.*;
import lombok.Data;
import ru.fso13.jmsmockandstubtools.api.JmsType;
import ru.fso13.jmsmockandstubtools.api.Queue;
import ru.fso13.jmsmockandstubtools.api.QueueSettingName;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Entity
@Data
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private JmsType type;
    @Column
    private String comment;
    @OneToMany(mappedBy = "queue")
    private Set<QueueSettingsEntity> queueSettings = new HashSet<>();


    public Queue toQueue() {
        return new Queue(name, type, comment, Duration.parse(getByName(QueueSettingName.DELAY).getValue()), getByName(QueueSettingName.STUB_MESSAGE).getValue(), getByName(QueueSettingName.REPLY_TO).getValue());
    }

    private QueueSettingsEntity getByName(QueueSettingName name) {
        return queueSettings.stream().filter(queueSettingsEntity -> queueSettingsEntity.getName() == name).findFirst().orElse(null);
    }
}
