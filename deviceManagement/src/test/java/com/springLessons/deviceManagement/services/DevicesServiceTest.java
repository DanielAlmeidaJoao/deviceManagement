package com.springLessons.deviceManagement.services;

import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.databaseEntities.State;
import com.springLessons.deviceManagement.dtos.CreateDTO;
import com.springLessons.deviceManagement.dtos.UpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
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
        return new Device("nameTest","nameBrand", state);
    }

    private CreateDTO getTestCreateDTO(State state){
        CreateDTO createDTO = new CreateDTO();
        createDTO.setBrand("BRAND_2");
        createDTO.setName("NAME_2");
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