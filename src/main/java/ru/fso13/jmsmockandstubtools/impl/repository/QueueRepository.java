package ru.fso13.jmsmockandstubtools.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.fso13.jmsmockandstubtools.impl.model.QueueEntity;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long>, JpaSpecificationExecutor<QueueEntity> {
}
