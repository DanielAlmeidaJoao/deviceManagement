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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@Import(DevicesService.class)
@SpringBootTest
class DevicesServiceTest {

    @Autowired
    private DevicesService devicesService;

    private Device getTestDevice(State state){
        return new Device("nameTest","nameBrand", state,System.currentTimeMillis());
    }
    @Test
    void testCreate() {
        Device device = getTestDevice(State.IN_USE);
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
    void update_name_brand_of_available_device() {
        //TODO use the library that allows to pass test parameters
        // for update in available
        Device device = getTestDevice(State.AVAILABLE);
        Device createdDevice = devicesService.create(device);
        createdDevice.setName("DEVICE_2");
        createdDevice.setBrand("BRAND_2");
        createdDevice.setState(State.IN_USE);

        Device updatedDevice = devicesService.updateDevice(createdDevice);

        assertEquals(createdDevice.getId(),updatedDevice.getId());
        assertEquals(createdDevice.getName(),updatedDevice.getName());
        assertEquals(createdDevice.getBrand(),updatedDevice.getBrand());
        assertEquals(createdDevice.getState(),updatedDevice.getState());
    }

    @Test
    void fails_to_update_name_brand_of_inUse_device() {
        Device device = getTestDevice(State.IN_USE);
        Device createdDevice = devicesService.create(device);
        createdDevice.setName("DEVICE_2");
        createdDevice.setBrand("BRAND_2");

        try{
            devicesService.updateDevice(createdDevice);
            assertTrue(false);
        }catch (ResponseStatusException e){
            assertEquals(e.getStatusCode(), HttpStatus.CONFLICT);
        }
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