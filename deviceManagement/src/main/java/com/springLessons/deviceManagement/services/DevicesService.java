package com.springLessons.deviceManagement.services;

import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.databaseEntities.State;
import com.springLessons.deviceManagement.repositories.DevicesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DevicesService {

    private final DevicesRepository devicesRepository;

    public DevicesService(DevicesRepository devicesRepository) {
        this.devicesRepository = devicesRepository;
    }

    public Device create(Device device){
        return devicesRepository.save(device);
    }

    public Device updateDevice(Device device){
        return devicesRepository.save(device);
    }

    public Device getDeviceById(long id){
        return devicesRepository.findById(id).orElse(null);
    }

    public List<Device> getAll(){
        return devicesRepository.findAll();
    }

    public List<Device> getAllByBrand(String brand){
        return devicesRepository.findByBrand(brand);
    }

    public List<Device> getAllByState(String state){
        State stateEnum = State.valueOf(state);
        if(stateEnum == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid state "+state);
        }
        return devicesRepository.findByState(stateEnum);
    }

    public void deleteDevice(long deviceId){
        devicesRepository.deleteById(deviceId);
    }
}
