package com.springLessons.deviceManagement.services;

import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.databaseEntities.State;
import com.springLessons.deviceManagement.dtos.CreateDTO;
import com.springLessons.deviceManagement.dtos.UpdateDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(DevicesService.class)
@SpringBootTest
class DevicesServiceTest {

    @Autowired
    private DevicesService devicesService;

    private Device getTestDevice(State state){
        return new Device("nameTest","nameBrand", state);
    }

    private CreateDTO getTestCreateDTO(State state){
        CreateDTO createDTO = new CreateDTO();
        createDTO.setBrand("BRAND_2" + System.currentTimeMillis());
        createDTO.setName("NAME_2" + System.currentTimeMillis());
        createDTO.setState(state);
        return createDTO;
    }

    private UpdateDTO getTestUpdateDTO(State state, Device device){
        UpdateDTO updateDTO = new UpdateDTO();
        updateDTO.setBrand(device.getBrand());
        updateDTO.setName(device.getName());
        updateDTO.setState(state);
        updateDTO.setId(device.getId());
        return updateDTO;
    }

    private UpdateDTO getUnknownIdTestUpdateDTO(State state){
        UpdateDTO updateDTO = new UpdateDTO();
        updateDTO.setBrand("BRAND_3");
        updateDTO.setName("NAME_3");
        updateDTO.setState(state);
        updateDTO.setId(-1L);
        return updateDTO;
    }

    @Test
    void testCreate() {
        CreateDTO createDeviceDTO = getTestCreateDTO(State.IN_USE);
        Device createdDevice = devicesService.create(createDeviceDTO);

        assertTrue( createdDevice.getId() > 0);
        assertEquals( createDeviceDTO.getName(),createdDevice.getName());
        assertEquals( createDeviceDTO.getBrand(),createdDevice.getBrand());
        assertEquals( createDeviceDTO.getState(),createdDevice.getState());
    }


    @Test
    void testCreate_WithAvailableState() {
        CreateDTO createDeviceDTO = getTestCreateDTO(State.AVAILABLE);
        Device createdDevice = devicesService.create(createDeviceDTO);

        assertEquals( createDeviceDTO.getState(),createdDevice.getState());
    }

    @Test
    void testCreate_WithInUseState() {
        CreateDTO createDeviceDTO = getTestCreateDTO(State.IN_USE);

        Device createResponse = devicesService.create(createDeviceDTO);

        Device createdDevice = devicesService.getDeviceById(createResponse.getId());

        assertEquals( createDeviceDTO.getState(),createdDevice.getState());
    }

    @Test
    void testCreate_WithInactiveState() {
        CreateDTO createDeviceDTO = getTestCreateDTO(State.INACTIVE);

        Device createResponse = devicesService.create(createDeviceDTO);

        Device createdDevice = devicesService.getDeviceById(createResponse.getId());

        assertEquals( createDeviceDTO.getState(),createdDevice.getState());
    }

    @Test
    void update_name_brand_of_available_device() {
        //TODO use the library that allows to pass test parameters
        // for update in available
        CreateDTO createDTO = getTestCreateDTO(State.AVAILABLE);

        Device createdDevice = devicesService.create(createDTO);
        createdDevice.setName("DEVICE_2");
        createdDevice.setBrand("BRAND_2");
        createdDevice.setState(State.IN_USE);

        UpdateDTO updateDTO = getTestUpdateDTO(State.IN_USE,createdDevice);
        Device updatedDevice = devicesService.updateDevice(updateDTO);

        assertEquals(createdDevice.getId(),updatedDevice.getId());
        assertEquals(createdDevice.getName(),updatedDevice.getName());
        assertEquals(createdDevice.getBrand(),updatedDevice.getBrand());
        assertEquals(createdDevice.getState(),updatedDevice.getState());
    }

    @Test
    void fails_to_update_name_brand_of_inUse_device() {
        CreateDTO device = getTestCreateDTO(State.IN_USE);

        Device createdDevice = devicesService.create(device);

        UpdateDTO updateDTO = getTestUpdateDTO(State.IN_USE,createdDevice);
        updateDTO.setName("DEVICE__NEW");
        updateDTO.setBrand("BRAND__NEW");

        try{
            devicesService.updateDevice(updateDTO);
            assertTrue(false);
        }catch (ResponseStatusException e){
            assertEquals(e.getStatusCode(), HttpStatus.CONFLICT);
        }
    }

    @Test
    void getDeviceById() {
        CreateDTO createDTO = getTestCreateDTO(State.AVAILABLE);

        Device createDeviceResponse = devicesService.create(createDTO);

        Device justCreatedDevice = devicesService.getDeviceById(createDeviceResponse.getId());

        assertEquals(createDeviceResponse.getId(),justCreatedDevice.getId());
        assertEquals(createDeviceResponse.getName(),justCreatedDevice.getName());
        assertEquals(createDeviceResponse.getBrand(),justCreatedDevice.getBrand());
        assertEquals(createDeviceResponse.getState(),justCreatedDevice.getState());
    }

    @Test
    void getAll() {
        int MAX_DEVICES = 10;
        Map<Long,Device> createdDevices = new HashMap<>();
        for (int i = 0; i < MAX_DEVICES; i++) {
            CreateDTO createDTO = getTestCreateDTO(State.AVAILABLE);
            Device createResponse = devicesService.create(createDTO);
            createdDevices.put(createResponse.getId(),createResponse);
        }

        List<Device> fetchedDevices = devicesService.getAll();

        assertTrue(MAX_DEVICES == createdDevices.size() && createdDevices.size() == fetchedDevices.size());
        fetchedDevices.forEach(device -> {
            Device chachedDevice = createdDevices.get(device.getId());
            assertEquals(chachedDevice.getId(),device.getId());
            assertEquals(chachedDevice.getName(),device.getName());
            assertEquals(chachedDevice.getBrand(),device.getBrand());
            assertEquals(chachedDevice.getState(),device.getState());
        });
    }

    @Test
    void getAllByBrand() {
        String best_brand = "BEST_BRAND";
        int countBestBrand = 0;
        int MAX_DEVICES = 10;
        Map<Long,Device> createdDevices = new HashMap<>();
        for (int i = 0; i < MAX_DEVICES; i++) {
            CreateDTO createDTO = getTestCreateDTO(State.AVAILABLE);
            if(i % 2 == 0) {
                createDTO.setBrand(best_brand);
                countBestBrand++;
            }
            Device createResponse = devicesService.create(createDTO);
            createdDevices.put(createResponse.getId(),createResponse);
        }

        List<Device> fetchedDevices = devicesService.getAllByBrand(best_brand);

        assertTrue(countBestBrand < createdDevices.size());
        assertTrue(countBestBrand == fetchedDevices.size());
        fetchedDevices.forEach(device -> {
            Device chachedDevice = createdDevices.get(device.getId());
            assertEquals(chachedDevice.getId(),device.getId());
            assertEquals(chachedDevice.getName(),device.getName());
            assertEquals(chachedDevice.getBrand(),device.getBrand());
            assertEquals(chachedDevice.getState(),device.getState());
        });
    }

    @Test
    void getAllByState() {
    }

    @Test
    void fails_deleteDevice() {
        CreateDTO device = getTestCreateDTO(State.IN_USE);

        Device createdDevice = devicesService.create(device);

        try{
            devicesService.deleteDevice(createdDevice.getId());
            assertTrue(false);
        }catch (ResponseStatusException e){
            assertEquals(e.getStatusCode(), HttpStatus.CONFLICT);
        }
    }

    @Test
    void success_deleteDevice() {
        CreateDTO device = getTestCreateDTO(State.AVAILABLE);

        Device availableDevice = devicesService.create(getTestCreateDTO(State.AVAILABLE));
        Device inactiveDevice = devicesService.create(getTestCreateDTO(State.INACTIVE));

        devicesService.deleteDevice(availableDevice.getId());
        devicesService.deleteDevice(inactiveDevice.getId());


        try{
            devicesService.getDeviceById(availableDevice.getId());
            assertTrue(false);
        }catch (ResponseStatusException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }

        try{
            devicesService.getDeviceById(inactiveDevice.getId());
            assertTrue(false);
        }catch (ResponseStatusException e){
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}