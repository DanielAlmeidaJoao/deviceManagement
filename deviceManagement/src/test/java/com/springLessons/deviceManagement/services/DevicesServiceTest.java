package com.springLessons.deviceManagement.services;

import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.databaseEntities.State;
import com.springLessons.deviceManagement.repositories.DevicesRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@Import(DevicesService.class)
@SpringBootTest
class DevicesServiceTest {

    @Autowired
    private DevicesService devicesService;

    @Test
    void testCreate() {
        Device device = new Device("nameTest","nameBrand", State.AVAILABLE,System.currentTimeMillis());
        Device createdDevice = devicesService.create(device);

        assertTrue( device.getId() > 0);
        assertEquals( device.getName(),createdDevice.getName());
        assertEquals( device.getBrand(),createdDevice.getBrand());
        assertEquals( device.getState(),createdDevice.getState());
    }


    @Test
    void testCreate_WithAvailableState() {
        Device device = new Device("nameTest","nameBrand", State.AVAILABLE,System.currentTimeMillis());
        Device createdDevice = devicesService.create(device);

        assertEquals( device.getState(),createdDevice.getState());
    }

    @Test
    void testCreate_WithInUseState() {
        Device device = new Device("nameTest","nameBrand", State.IN_USE,System.currentTimeMillis());
        Device createdDevice = devicesService.create(device);

        assertEquals( device.getState(),createdDevice.getState());
    }

    @Test
    void testCreate_WithInactiveState() {
        Device device = new Device("nameTest","nameBrand", State.INACTIVE,System.currentTimeMillis());
        Device createdDevice = devicesService.create(device);

        assertEquals( device.getState(),createdDevice.getState());
    }

    @Test
    void updateDevice() {
    }

    @Test
    void getDeviceById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllByBrand() {
    }

    @Test
    void getAllByState() {
    }

    @Test
    void deleteDevice() {
    }
}