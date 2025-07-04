package com.bank.history.repository;

import com.bank.history.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления сущностью HistoryEntity.
 */
@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
}
