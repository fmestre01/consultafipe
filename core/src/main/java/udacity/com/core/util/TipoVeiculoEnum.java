package udacity.com.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TipoVeiculoEnum {

    CARROS_UTILITARIOS("1"),

    MOTOS("2"),

    CAMINHOES_MICRO_ONIBUS("3");

    private static final List<String> VALUES;

    private final String value;

    static {
        VALUES = new ArrayList<>();
        for (TipoVeiculoEnum someEnum : TipoVeiculoEnum.values()) {
            VALUES.add(someEnum.value);
        }
    }

    TipoVeiculoEnum(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Collections.unmodifiableList(VALUES);
    }
}
