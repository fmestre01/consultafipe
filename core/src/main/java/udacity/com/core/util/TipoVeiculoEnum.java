package udacity.com.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum TipoVeiculoEnum {

    CARROS_UTILITARIOS(ConstantsUtils.TipoVeiculo.CODIGO_CARROS_UTILITARIOS_PEQUENOS,
            ConstantsUtils.TipoVeiculo.DESC_CARROS_UTILITARIOS_PEQUENOS),

    MOTOS(ConstantsUtils.TipoVeiculo.CODIGO_MOTOS,
            ConstantsUtils.TipoVeiculo.DESC_CODIGO_MOTOS),

    CAMINHOES_MICRO_ONIBUS(ConstantsUtils.TipoVeiculo.CODIGO_CAMINHOES_MICRO_ONIBUS,
            ConstantsUtils.TipoVeiculo.DESC_CODIGO_CAMINHOES_MICRO_ONIBUS);

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

    public static String valueOf(int position) {
        return IDS.get(position);
    }
}
