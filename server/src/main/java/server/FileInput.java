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
        CityBuilder cityBuilder = new CityBuilder();
        return cityBuilder.name(reader.nameReadEvent())
                .coordinates(reader.coordinatesReadEvent())
                .area(reader.areaReadEvent())
                .population(reader.populationReadEvent())
                .metersAboveSeaLevel(reader.metersAboveSeaLevelReadEvent())
                .agglomeration(reader.agglomerationReadEvent())
                .climate(reader.climateReadEvent())
                .government(reader.governmentReadEvent())
                .governor(reader.governorReadEvent())
                .build();
    }
}