package ru.fso13.jmsmockandstubtools.impl.services;

import jakarta.jms.Message;

public interface MockJmsListenerService {
    void onMessage(Message message);
}
