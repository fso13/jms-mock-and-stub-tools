package ru.fso13.jmsmockandstubtools.front;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.fso13.jmsmockandstubtools.api.QueueSettingService;

@RestController
@RequiredArgsConstructor
public class QueueSettingController {

    private final QueueSettingService queueSettingService;

}
