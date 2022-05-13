package server.converter;

import lib.interaction.FieldReader;
import lib.element.Climate;
import lib.element.Coordinates;
import lib.element.Government;
import lib.element.Human;

import java.util.Scanner;

public class FieldReaderFromFile extends FieldReader {
    public FieldReaderFromFile(Scanner reader) {
        super(reader);
    }

    public String nameReadEvent() {
        return nameValidation();
    }

    public Coordinates coordinatesReadEvent() {
        return coordinatesValidation();
    }


    public int areaReadEvent() {
        return areaValidation();
    }


    public long populationReadEvent() {
        return populationValidation();
    }


    public Long metersAboveSeaLevelReadEvent() {
        return metersAboveSeaLevelValidation();
    }


    public float agglomerationReadEvent() {
        return agglomerationValidation();
    }


    public Climate climateReadEvent() {
        return climateValidation();
    }


    public Government governmentReadEvent() {
        return governmentValidation();
    }


    public Human governorReadEvent() {
        return governorValidation();
    }
}
