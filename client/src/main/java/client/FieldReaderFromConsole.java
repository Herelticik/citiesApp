package client;

import lib.exceptions.IncorrectArgumentException;
import lib.interaction.FieldReader;
import lib.element.Climate;
import lib.element.Coordinates;
import lib.element.Government;
import lib.element.Human;

import java.util.Scanner;

public class FieldReaderFromConsole extends FieldReader {
    /**
     * Задание места, откуда читать
     *
     * @param reader Поток ввода
     */
    public FieldReaderFromConsole(Scanner reader) {
        super(reader);
    }


    public String nameReadEvent() {
        String name = null;
        while (true) {
            try {
                name = nameValidation();
                break;
            } catch (IncorrectArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return name;
    }

    public Coordinates coordinatesReadEvent() {
        Coordinates coordinates = null;
        while (true) {
            try {
                coordinates = coordinatesValidation();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введены не числа!");
            } catch (IncorrectArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
        return coordinates;
    }

    public int areaReadEvent() {
        int area = 0;
        while (true) {
            try {
                area = areaValidation();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введено не число!");
            } catch (IncorrectArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return area;
    }


    public long populationReadEvent() {
        long population = 0;

        while (true) {

            try {
                population = populationValidation();
                break;
            } catch (IncorrectArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Введено не число!");
            }
        }
        return population;
    }


    public Long metersAboveSeaLevelReadEvent() {
        long metersAboveSeaLevel = 0;
        while (true) {
            try {
                metersAboveSeaLevel = metersAboveSeaLevelValidation();
                break;
            } catch (IncorrectArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Введено не число!");
            }
        }
        return metersAboveSeaLevel;
    }

    public float agglomerationReadEvent() {
        float agglomeration = 0;
        while (true) {
            try {
                agglomeration = agglomerationValidation();
                break;
            } catch (IncorrectArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Введено не число!");
            }
        }
        return agglomeration;
    }


    public Climate climateReadEvent() {
        Climate climate = null;

        while (true) {
            try {
                climate = climateValidation();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Введено некорректное значение!");
            }
        }
        return climate;
    }

    public Government governmentReadEvent() {
        Government government = null;

        while (true) {
            try {
                government = governmentValidation();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Введено некорректное значение!");
            }
        }
        return government;
    }


    public Human governorReadEvent() {
        Human governor = null;
        while (true) {
            try {
                governor = governorValidation();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введено не число!");
            } catch (IncorrectArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
        return governor;
    }
}
