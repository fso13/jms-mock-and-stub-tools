package ru.fso13.jmsmockandstubtools.api;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Filter {
    private JmsType jmsType;
    private String queueName;
}
