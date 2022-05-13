package lib.interaction;

import lib.exceptions.IncorrectArgumentException;
import lib.element.Climate;
import lib.element.Coordinates;
import lib.element.Government;
import lib.element.Human;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Класс чтения полей ввода
 */
public abstract class FieldReader {
    Scanner reader;

    public FieldReader(Scanner reader) {
        this.reader = reader;
    }

    public String nameValidation() {
        String name = getInputValue();
        killIfValueIsNull(name);
        return name;
    }

    public Coordinates coordinatesValidation() {
        ArrayList<String> coordinatesArr = new ArrayList<>(Arrays.asList(getInputValue().split(" ")));
        coordinatesArr.removeIf(element->element.equals(""));
        Coordinates coordinates;
        if (coordinatesArr.size() == 2) {
            float x = Float.parseFloat(coordinatesArr.get(0));
            double y = Double.parseDouble(coordinatesArr.get(1));
            coordinates = new Coordinates(x, y);
            if (!coordinates.isValid()) {
                throw new IncorrectArgumentException("Координаты не попадают в область допустимых значений!");
            }
        } else {
            throw new IncorrectArgumentException("Координаты введены неверно!");
        }
        return coordinates;
    }

    public int areaValidation() {
        String areaLine = getInputValue();
        killIfValueIsNull(areaLine);
        int area = Integer.parseInt(areaLine);
        if (area <= 0) {
            throw new IncorrectArgumentException("Площадь не попадает в область допустимых значений!");
        }
        return area;
    }

    public Long populationValidation() {
        String populationLine = getInputValue();
        killIfValueIsNull(populationLine);
        long population = Long.parseLong(populationLine);
        if (population <= 0) {
            throw new IncorrectArgumentException("Численность населения не попадает в область допустимых значений!");
        }
        return population;

    }

    public long metersAboveSeaLevelValidation() {
        String metersAboveSeaLevelLine = getInputValue();
        killIfValueIsNull(metersAboveSeaLevelLine);
        return Long.parseLong(metersAboveSeaLevelLine);
    }


    public float agglomerationValidation() {
        String agglomerationLine = getInputValue();
        killIfValueIsNull(agglomerationLine);
        return Float.parseFloat(agglomerationLine);
    }


    public Climate climateValidation() {
        String climateLine = getInputValue().toUpperCase();
        Climate climate = null;
        if (!climateLine.isEmpty()) {
            climate = Climate.valueOf(climateLine);
        }
        return climate;
    }


    public Government governmentValidation() {
        Government government = null;
        String governmentLine = getInputValue().toUpperCase();
        if (!governmentLine.isEmpty()) {
            government = Government.valueOf(governmentLine);
        }
        return government;
    }


    public Human governorValidation() {
        String governorLine = getInputValue();
        Human governor = null;
        if (!governorLine.isEmpty()) {
            long governorAge = Long.parseLong(governorLine);
            if (governorAge <= 0) {
                throw new IncorrectArgumentException("Возраст правителя не попадает в область допустимых значений!");
            }
            governor = new Human(governorAge);
        }
        return governor;
    }

    private void killIfValueIsNull(String line) {
        if (line.trim().isEmpty()) {
            throw new IncorrectArgumentException("Значение не может быть пустым!");
        }
    }

    private String getInputValue() {
        return reader.nextLine().trim();
    }

}
