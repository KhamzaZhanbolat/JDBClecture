package lecturedependency.projectjdbc.service;

import lecturedependency.projectjdbc.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarService {

    private final Connection connection;

    public boolean addCar(Car c) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO t_car(id, brand, model, engine_volume, max_speed) VALUES (DEFAULT, ?, ?, ?, ?)");

            stmt.setString(1,c.getBrand());
            stmt.setString(2,c.getModel());
            stmt.setDouble(3,c.getEngineVolume());
            stmt.setInt(4,c.getMaxSpeed());

            stmt.executeUpdate();
            stmt.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
    public List<Car> getAllCarsByMinimalEngineVolume(double minimalEngineVolume){
        List<Car> cars = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM t_car WHERE engine_volum>=?");

            stmt.setDouble(1,minimalEngineVolume);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Long id = rs.getLong("id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                double engineVolume = rs.getDouble("engine_volume");
                int maxSpeed = rs.getInt("max_speed");

                Car car = Car
                        .builder()
                        .id(id)
                        .brand(brand)
                        .model(model)
                        .engineVolume(engineVolume)
                        .maxSpeed(maxSpeed)
                        .build();
                cars.add(car);
            }
            stmt.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getAllCars(){
        List<Car> cars = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM t_car;");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Car car = Car
                        .builder()
                        .id(rs.getLong("id"))
                        .brand(rs.getString("brand"))
                        .model(rs.getString("model"))
                        .engineVolume(rs.getDouble("engine_volume"))
                        .maxSpeed(rs.getInt("max_speed"))
                        .build();
                cars.add(car);
            }
            stmt.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return cars;
    }
    public Car getCarById(Long id){
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM t_car WHERE id=?");

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Car c = Car
                        .builder()
                        .id(rs.getLong("id"))
                        .brand(rs.getString("brand"))
                        .model(rs.getString("model"))
                        .engineVolume(rs.getDouble("engine_volume"))
                        .maxSpeed(rs.getInt("max_speed"))
                        .build();
                stmt.close();
                return c;
            }else {
                stmt.close();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
