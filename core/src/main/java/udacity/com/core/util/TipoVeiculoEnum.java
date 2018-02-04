package udacity.com.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TipoVeiculoEnum {

    CARROS_UTILITARIOS("1", "Carros e Utilitários Pequenos"),

    MOTOS("2", "Motos"),

    CAMINHOES_MICRO_ONIBUS("3", "Caminhões e micro ônibus");

    private static final List<String> IDS;
    private static final List<String> VALUES;

    private final String id;
    private final String value;

    static {
        IDS = new ArrayList<>();
        VALUES = new ArrayList<>();
        for (TipoVeiculoEnum someEnum : TipoVeiculoEnum.values()) {
            IDS.add(someEnum.id);
            VALUES.add(someEnum.value);
        }
    }

    TipoVeiculoEnum(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public static List<String> getValues() {
        return Collections.unmodifiableList(VALUES);
    }
}
