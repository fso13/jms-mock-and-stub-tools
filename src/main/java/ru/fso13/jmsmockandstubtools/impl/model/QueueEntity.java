package ru.fso13.jmsmockandstubtools.impl.model;


import jakarta.persistence.*;
import lombok.Data;
import ru.fso13.jmsmockandstubtools.api.JmsType;
import ru.fso13.jmsmockandstubtools.api.Queue;

import java.util.Set;

@Entity
@Data
public class QueueEntity {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private JmsType type;
    @Column
    private String comment;
    @OneToMany(mappedBy = "queue")
    private Set<QueueSettingsEntity> queueSettings;


    public Queue toQueue() {
        return new Queue();
    }
}
