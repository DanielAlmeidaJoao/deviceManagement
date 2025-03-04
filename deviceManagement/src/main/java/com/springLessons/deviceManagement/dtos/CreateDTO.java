package com.springLessons.deviceManagement.dtos;

import com.springLessons.deviceManagement.databaseEntities.Device;
import com.springLessons.deviceManagement.databaseEntities.State;

public class CreateDTO {

    private String name;
    private String brand;
    private State state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Device createDTOToDevice(){
        Device device = new Device();
        device.setName(this.getName());
        device.setBrand(this.getBrand());
        device.setState(this.getState());
        return device;
    }
}
