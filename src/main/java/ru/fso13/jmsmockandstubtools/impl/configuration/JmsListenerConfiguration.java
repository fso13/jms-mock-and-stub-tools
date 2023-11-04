package ru.fso13.jmsmockandstubtools.impl.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fso13.jmsmockandstubtools.api.Filter;
import ru.fso13.jmsmockandstubtools.api.JmsType;
import ru.fso13.jmsmockandstubtools.api.Queue;
import ru.fso13.jmsmockandstubtools.api.QueueSettingService;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class JmsListenerConfiguration {

    private final QueueSettingService queueSettingService;


    @PostConstruct
    public void init() {

        queueSettingService.getQueues(Filter.builder().jmsType(JmsType.IN).build()).forEach(new Consumer<Queue>() {
            @Override
            public void accept(Queue queue) {
            }
        });
    }


}
