package ru.fso13.jmsmockandstubtools.front;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fso13.jmsmockandstubtools.api.Filter;
import ru.fso13.jmsmockandstubtools.api.Queue;
import ru.fso13.jmsmockandstubtools.api.QueueSettingService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue-settings")
public class QueueSettingController {

    private final QueueSettingService queueSettingService;

    @GetMapping
    public Set<Queue> findSettings() {
        return queueSettingService.getQueues(Filter.builder().build());
    }

    @PostMapping
    public Queue create(Queue queue){
        return queueSettingService.addSetting(queue);
    }
}
