package ru.fso13.jmsmockandstubtools.impl.services;

import jakarta.jms.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.ObjectUtils;
import ru.fso13.jmsmockandstubtools.api.Queue;
import ru.fso13.jmsmockandstubtools.api.QueueSetting;
import ru.fso13.jmsmockandstubtools.api.QueueSettingName;

import java.time.Duration;


public class MockJmsListenerServiceImpl implements MockJmsListenerService {
    private final JmsTemplate jmsTemplate;
    private final Queue queue;
    private final Duration delay;
    private final String stubMessage;
    private final String replyTo;

    public MockJmsListenerServiceImpl(Queue queue, JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
        this.delay = queue.queueSettings().stream().filter(queueSetting -> queueSetting.name() == QueueSettingName.DELAY).map(queueSetting -> Duration.parse(queueSetting.value())).findFirst().orElse(Duration.ZERO);

        this.replyTo = queue.queueSettings().stream().filter(queueSetting -> queueSetting.name() == QueueSettingName.REPLY_TO).map(QueueSetting::value).findFirst().orElse(null);


        this.stubMessage = queue.queueSettings().stream().filter(queueSetting -> queueSetting.name() == QueueSettingName.STUB_MESSAGE).map(QueueSetting::value).findFirst().orElse(null);

        if (ObjectUtils.isEmpty(stubMessage) || ObjectUtils.isEmpty(replyTo)) {
            throw new IllegalArgumentException("If stub listener REPLY_TO and STUB_MESSAGE is mandatory!!!");
        }
    }

    @Override
    public void onMessage(Message message) {

        if (!delay.equals(Duration.ZERO)) {
            try {
                Thread.sleep(delay);
                jmsTemplate.send(replyTo, s -> s.createTextMessage(stubMessage));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
