package org.java.courses.lesson1.hibernate.dao;

import org.java.courses.lesson1.hibernate.model.Car;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarDaoTest {

    private final CarDao carDao = CarDao.create("hibernate.annotation.internal.cfg.xml");

    @Test
    public void verifyCrudOperations() {
        //CREATE
        Car car = carDao.createRecord(new Car("нива"));
        assertNotNull(car.getId());

        //FIND ALL
        List<Car> cars = carDao.findAll();
        assertEquals(1, cars.size());
        assertEquals("нива", cars.get(0).getModel());

        //UPDATE
        car.setModel("жигули");
        car = carDao.updateRecord(car);
        assertEquals("жигули", car.getModel());

        //FIND BY ID
        car = carDao.findRecordById(car.getId());
        assertEquals("жигули", car.getModel());

        //DELETE
        carDao.deleteRecord(car.getId());
        car = carDao.findRecordById(car.getId());
        assertNull(car);
    }
}