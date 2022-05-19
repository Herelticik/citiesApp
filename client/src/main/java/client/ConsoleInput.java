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
        CityBuilder cityBuilder=new CityBuilder();
        FieldReaderFromConsole reader = new FieldReaderFromConsole(getReader());
        System.out.println("Введите название города");
        cityBuilder.name(reader.nameReadEvent());
        System.out.println("Введите координаты в формате: X Y");
        cityBuilder.coordinates(reader.coordinatesReadEvent());
        System.out.println("Введите площадь города:");
        cityBuilder.area(reader.areaReadEvent());
        System.out.println("Введите население:");
        cityBuilder.population(reader.populationReadEvent());
        System.out.println("Введите уровень над водой:");
        cityBuilder.metersAboveSeaLevel(reader.metersAboveSeaLevelReadEvent());
        System.out.println("Введите аггломерацию:");
        cityBuilder.agglomeration(reader.agglomerationReadEvent());
        System.out.println("Введите климат (TROPICAL_SAVANNA, STEPPE, SUBARCTIC, TUNDRA):");
        cityBuilder.climate(reader.climateReadEvent());
        System.out.println("Введите форму правления (IDEOCRACY, NOOCRACY, THALASSOCRACY):");
        cityBuilder.government(reader.governmentReadEvent());
        System.out.println("Введите возраст правителя");
        cityBuilder.governor(reader.governorReadEvent());
        return cityBuilder.build();
    }



}
