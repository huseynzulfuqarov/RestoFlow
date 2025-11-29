package com.restoflow.repository;

import com.restoflow.domain.settings.OperationalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationalSettingsRepository extends JpaRepository<OperationalSettings, Long> {
}
