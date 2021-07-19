package com.cartrawler.assessment.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cartrawler.assessment.car.CarResult;

public class CarService {
	
	private static final Logger log = LoggerFactory.getLogger(CarService.class);
	
	private static CarService instance;

	public static CarService getInstance() {
		if (instance == null) {
			instance = new CarService();
		}
		return instance;
	}
	
	private String[] corporates = {"AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY"};
	
	private ArrayList<Character> sippRanking;
	
	private CarService() {
		sippRanking = new ArrayList<>();
		sippRanking.add('M');
		sippRanking.add('E');
		sippRanking.add('C');
	}
	
	public List<Character> getSippRanking() {
		return sippRanking;
	}

	public List<CarResult> removeDuplicates(Set<CarResult> cars) {
		return cars.stream().distinct().collect(Collectors.toList());
	}
	
	public List<CarResult> getCorporateCars(List<CarResult> cars, boolean corporate) {
		log.info("Get corporate cars {}", corporate);
		return cars.stream()
				.filter(c -> Arrays.asList(corporates).contains(c.getSupplierName()) == corporate)
				.collect(Collectors.toList());
	}
	
	public List<CarResult> getSipp(List<CarResult> cars, char sipp) {
		log.info("Getting cars with sipp {}", sipp);
		if (sippRanking.contains(sipp)) {
			return cars.stream()
					.filter(c -> c.getSippCode().charAt(0) == sipp)
					.collect(Collectors.toList());
		} else {
			return cars.stream()
					.filter(c -> !sippRanking.contains(c.getSippCode().charAt(0)))
					.collect(Collectors.toList());
		}
	}
	
	public void sortPrice(List<CarResult> cars) {
		log.info("Sorting cars by price");
		Collections.sort(cars, Comparator.comparingDouble(CarResult::getRentalCost));
	}
	
	public void removeAboveMedian(List<CarResult> cars) {
		double median = (cars.size() + 1) / 2d;
		double medianPrice;
		if (median % 1 == 0) {
			medianPrice = cars.get((int) median).getRentalCost();
		} else {
			medianPrice = (cars.get((int) (median-0.5)).getRentalCost() + cars.get((int) (median+0.5)).getRentalCost()) / 2;
		}
		
		cars.removeIf(c -> c.getRentalCost() > medianPrice);
	}
}
