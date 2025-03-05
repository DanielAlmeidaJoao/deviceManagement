package com.springLessons.deviceManagement.dtos;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.databaseEntities.State;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@JsonPOJOBuilder
public class UpdateDTO extends CreateDTO{
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device updateDTOToDeviceMapper(){
        Device device = super.createDTOToDevice();
        device.setId(this.id);
        return device;
    }
}
