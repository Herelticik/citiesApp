package server;

import lib.element.*;
import lib.interaction.Input;
import server.converter.FieldReaderFromFile;

import java.util.Scanner;

public class FileInput extends Input {

    public FileInput(Scanner reader) {
        super(reader);
    }

    @Override
    public City readElement() {
        FieldReaderFromFile reader = new FieldReaderFromFile(getReader());
        String name = reader.nameReadEvent();
        Coordinates coordinates = reader.coordinatesReadEvent();
        int area = reader.areaReadEvent();
        long population = reader.populationReadEvent();
        Long metersAboveSeaLevel = reader.metersAboveSeaLevelReadEvent();
        float agglomeration = reader.agglomerationReadEvent();
        Climate climate = reader.climateReadEvent();
        Government government = reader.governmentReadEvent();
        Human governor = reader.governorReadEvent();
        return new City(name, coordinates, area, population, metersAboveSeaLevel, agglomeration, climate, government, governor);
    }
}
