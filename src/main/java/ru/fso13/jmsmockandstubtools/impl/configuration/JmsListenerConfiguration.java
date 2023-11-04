package ru.fso13.jmsmockandstubtools.impl.configuration;

import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsTemplate;
import ru.fso13.jmsmockandstubtools.api.Filter;
import ru.fso13.jmsmockandstubtools.api.JmsType;
import ru.fso13.jmsmockandstubtools.api.QueueSettingService;
import ru.fso13.jmsmockandstubtools.impl.services.MockJmsListenerService;
import ru.fso13.jmsmockandstubtools.impl.services.MockJmsListenerServiceImpl;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JmsListenerConfiguration implements JmsListenerConfigurer {
    private final QueueSettingService queueSettingService;
    private final JmsTemplate jmsTemplate;
    private final JmsListenerContainerFactory<?> jmsListenerContainerFactory;

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        registrar.setContainerFactory(jmsListenerContainerFactory);
        registrationAsyncListenersForTibco(registrar);
    }

    private void registrationAsyncListenersForTibco(JmsListenerEndpointRegistrar registrar) {
        queueSettingService.getQueues(Filter.builder().jmsType(JmsType.IN).build()).forEach(queue -> {
            log.info("Create listener for Queue name: {}", queue.name());

            SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
            endpoint.setDestination(queue.name());
            endpoint.setId(UUID.randomUUID().toString());

            endpoint.setMessageListener(new MessageListener() {
                private final MockJmsListenerService service = new MockJmsListenerServiceImpl(queue, jmsTemplate);

                @Override
                public void onMessage(Message message) {
                    service.onMessage(message);
                }
            });

            registrar.registerEndpoint(endpoint);
        });
    }
}
