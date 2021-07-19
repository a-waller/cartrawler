package assessment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cartrawler.assessment.app.CarService;
import com.cartrawler.assessment.car.CarResult;

public class CarServiceTest {

	public CarService carService = CarService.getInstance();
	
	@Test
	public void whenCarsContainCorporates_shouldFilterOutCorporateCars() {
		List<CarResult> CARS = new ArrayList<>();
		CARS.add(new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));
		CARS.add(new CarResult("Volkswagen Polo", "AVIS", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));
		
		List<CarResult> corporates = carService.getCorporateCars(CARS, true);
		
		assertTrue(corporates.stream().allMatch(c -> c.getSupplierName().equals("AVIS")));
		
	}
	
	@Test
	public void whenCarsUnsorted_shouldSortByPrice() {
		List<CarResult> CARS = new ArrayList<>();
		CARS.add(new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));
		CARS.add(new CarResult("Volkswagen Polo", "AVIS", "EDMR", 44.81d, CarResult.FuelPolicy.FULLEMPTY));
		CARS.add(new CarResult("Volkswagen Polo", "HERTZ", "EDMR", 6.81d, CarResult.FuelPolicy.FULLEMPTY));
		
		List<CarResult> expectedCARS = new ArrayList<>();
		expectedCARS.add(new CarResult("Volkswagen Polo", "HERTZ", "EDMR", 6.81d, CarResult.FuelPolicy.FULLEMPTY));
		expectedCARS.add(new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));
		expectedCARS.add(new CarResult("Volkswagen Polo", "AVIS", "EDMR", 44.81d, CarResult.FuelPolicy.FULLEMPTY));
		
		carService.sortPrice(CARS);
		
		assertEquals(CARS, expectedCARS);
	}
}
