package com.springLessons.deviceManagement.repositories;

import com.springLessons.deviceManagement.databaseEntities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevicesRepository extends JpaRepository<Device, Long> {
}
