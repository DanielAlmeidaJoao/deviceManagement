package com.springLessons.deviceManagement.services;

import com.springLessons.deviceManagement.repositories.DevicesRepository;
import org.springframework.stereotype.Service;

@Service
public class DevicesService {

    private final DevicesRepository devicesRepository;

    public DevicesService(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }
}
