package com.springLessons.deviceManagement.controllers;

import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.dtos.CreateDTO;
import com.springLessons.deviceManagement.dtos.UpdateDTO;
import com.springLessons.deviceManagement.services.DevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DevicesService devicesService;

    @PostMapping
    public Device createDevice(@RequestBody CreateDTO device) {
        return devicesService.create(device);
    }

    @PutMapping
    public Device updateDevice(@RequestBody UpdateDTO device) {
        return devicesService.updateDevice(device);
    }

    @GetMapping("/{id}")
    public Device getDevice(@RequestParam long id) {
        return devicesService.getDeviceById(id);
    }

    @GetMapping("/all")
    public List<Device> getDevice() {
        return devicesService.getAll();
    }

    @GetMapping("/all/brand/{brandName}")
    public List<Device> getDevice(String brandName) {
        return devicesService.getAllByBrand(brandName);
    }


    @GetMapping("/all/state/{stateValue}")
    public List<Device> getDevicesByState(String stateValue) {
        return devicesService.getAllByState(stateValue);
    }

}


