package client;

import lib.element.*;
import lib.interaction.Input;

import java.util.Scanner;

public class ConsoleInput extends Input {


    public ConsoleInput(Scanner reader) {
        super(reader);
    }

    @Override
    public City readElement() {
        FieldReaderFromConsole reader = new FieldReaderFromConsole(getReader());
        System.out.println("Введите название города");
        String name = reader.nameReadEvent();
        System.out.println("Введите координаты в формате: X Y");
        Coordinates coordinates = reader.coordinatesReadEvent();
        System.out.println("Введите площадь города:");
        int area = reader.areaReadEvent();
        System.out.println("Введите население:");
        long population = reader.populationReadEvent();
        System.out.println("Введите уровень над водой:");
        Long metersAboveSeaLevel = reader.metersAboveSeaLevelReadEvent();
        System.out.println("Введите аггломерацию:");
        float agglomeration = reader.agglomerationReadEvent();
        System.out.println("Введите климат (TROPICAL_SAVANNA, STEPPE, SUBARCTIC, TUNDRA):");
        Climate climate = reader.climateReadEvent();
        System.out.println("Введите форму правления (IDEOCRACY, NOOCRACY, THALASSOCRACY):");
        Government government = reader.governmentReadEvent();
        System.out.println("Введите возраст правителя");
        Human governor = reader.governorReadEvent();
        return new City(name, coordinates, area, population, metersAboveSeaLevel, agglomeration, climate, government, governor);
    }



}
