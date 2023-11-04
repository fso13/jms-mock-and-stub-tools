package ru.fso13.jmsmockandstubtools.api;

import java.util.Set;

public interface QueueSettingService {

    Set<Queue> getQueues(Filter filter);
}
