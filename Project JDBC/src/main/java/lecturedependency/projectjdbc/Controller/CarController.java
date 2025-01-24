package lecturedependency.projectjdbc.Controller;

import lecturedependency.projectjdbc.model.Car;
import lecturedependency.projectjdbc.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public String getAllCars(Model model,
                             @RequestParam(required = false, name = "minimal_engine_volume")Double minimalEngineVolume) {
        if (minimalEngineVolume == null){
            model.addAttribute("cars", carService.getAllCars());
        }else {
            model.addAttribute("cars", carService.getAllCarsByMinimalEngineVolume(minimalEngineVolume));
            model.addAttribute("minimalEngineVolume", minimalEngineVolume);
        }
        return "cars";
    }
    @GetMapping("/car")
    public String getCarById(Model model,
                             @RequestParam(name = "id")Long id){
        model.addAttribute("car", carService.getCarById(id));
        return "car";
    }
    @GetMapping("/add_car")
    public String addCarPage(){
        return "add-car";
    }
    @PostMapping("/save_car")
    public String addCar(@RequestParam(name = "car_brand")String carBrand,
                         @RequestParam(name = "car_model")String carModel,
                         @RequestParam(name = "car_engine_volume")double engineVolume,
                         @RequestParam(name = "car_max_speed")int maxSpeed){
        Car newCar = Car
                .builder()
                .id(null)
                .brand(carBrand)
                .model(carModel)
                .engineVolume(engineVolume)
                .maxSpeed(maxSpeed)
                .build();
        boolean status = carService.addCar(newCar);
        if (status){
            return "redirect:/cars";
        }else {
            return "redirect:/add_car?error";
        }
    }
}
