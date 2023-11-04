package ru.fso13.jmsmockandstubtools.impl.services;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.fso13.jmsmockandstubtools.api.*;
import ru.fso13.jmsmockandstubtools.impl.model.QueueEntity;
import ru.fso13.jmsmockandstubtools.impl.model.QueueSettingsEntity;
import ru.fso13.jmsmockandstubtools.impl.repository.QueueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueSettingServiceImpl implements QueueSettingService {

    private final QueueRepository queueRepository;

    private static Specification<QueueEntity> mapFromFilter(Filter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(filter.getQueueName())) {
                predicates.add(cb.equal(root.get("name"), filter.getQueueName()));
            }

            if (!ObjectUtils.isEmpty(filter.getJmsType())) {
                predicates.add(cb.equal(root.get("type"), filter.getJmsType()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

    }

    @Override
    public Set<Queue> getQueues(Filter filter) {
        if (filter == null) {
            return queueRepository.findAll().stream().map(QueueEntity::toQueue).collect(Collectors.toSet());
        } else {
            return queueRepository.findAll(mapFromFilter(filter)).stream().map(QueueEntity::toQueue).collect(Collectors.toSet());
        }
    }

    @Override
    public Queue addSetting(Queue queue) {
        QueueEntity queueEntity = new QueueEntity();
        queueEntity.setComment(queue.comment());
        queueEntity.setName(queue.name());
        queueEntity.setType(queue.type());

        queueEntity.getQueueSettings().add(new QueueSettingsEntity(QueueSettingName.DELAY, queue.delay().toString()));
        queueEntity.getQueueSettings().add(new QueueSettingsEntity(QueueSettingName.STUB_MESSAGE, queue.stubMessage()));
        queueEntity.getQueueSettings().add(new QueueSettingsEntity(QueueSettingName.REPLY_TO, queue.replyTo()));

        return queueRepository.saveAndFlush(queueEntity).toQueue();
    }
}
