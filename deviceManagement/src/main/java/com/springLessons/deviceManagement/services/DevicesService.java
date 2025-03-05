package com.springLessons.deviceManagement.services;

import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.databaseEntities.State;
import com.springLessons.deviceManagement.dtos.CreateDTO;
import com.springLessons.deviceManagement.dtos.UpdateDTO;
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

    public Device create(CreateDTO device){
        return devicesRepository.save(device.createDTOToDevice());
    }

    private boolean updatesNameOrBrand(UpdateDTO update, Device originalDevice){
        return !(originalDevice.getName().equals(update.getName()) && originalDevice.getBrand().equals(update.getBrand()));
    }
    public Device updateDevice(UpdateDTO device) {
        Device originalDevice = devicesRepository.findById(device.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown device: " + device.getId()));

        if (State.IN_USE.compareTo(originalDevice.getState()) == 0 && updatesNameOrBrand(device,originalDevice)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name and brand can not be updated if the device is 'IN_USE' state");
        }

        return devicesRepository.save(device.updateDTOToDeviceMapper());
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
        Device originalDevice = devicesRepository.findById(deviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown device: " + deviceId));

        if (State.IN_USE.compareTo(originalDevice.getState()) == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "IN_USE devices cannot be deleted");
        }
        devicesRepository.deleteById(deviceId);
    }
}
