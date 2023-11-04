package ru.fso13.jmsmockandstubtools.impl.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fso13.jmsmockandstubtools.api.QueueSetting;
import ru.fso13.jmsmockandstubtools.api.QueueSettingName;

@Entity
@Data
@NoArgsConstructor
public class QueueSettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private QueueSettingName name;
    @Column
    private String value;
    @ManyToOne
    @JoinColumn(name = "queue_id", nullable = false)
    private QueueEntity queue;

    public QueueSettingsEntity(QueueSettingName name, String value) {
        this.name = name;
        this.value = value;
    }

    public QueueSetting toQueueSetting(){
        return new QueueSetting(name, value);
    }
}
