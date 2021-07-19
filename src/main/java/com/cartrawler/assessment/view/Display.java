package com.cartrawler.assessment.view;

import com.cartrawler.assessment.car.CarResult;

import java.util.List;

public class Display {
    public void render(List<CarResult> cars) {
        for (CarResult car : cars) {
            System.out.println (car);
        }
    }
}
