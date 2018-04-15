package util;

import udacity.com.core.util.ConstantsUtils;

public class ConsultaFipeUtil {

    public static String selectTipoVeiculoName(String tipoVeiculoCod) {
        switch (tipoVeiculoCod) {
            case ConstantsUtils.TipoVeiculo.CODIGO_CARROS_UTILITARIOS_PEQUENOS:
                return ConstantsUtils.TipoVeiculo.DESC_CARROS_UTILITARIOS_PEQUENOS;
            case ConstantsUtils.TipoVeiculo.CODIGO_MOTOS:
                return ConstantsUtils.TipoVeiculo.DESC_CODIGO_MOTOS;
            case ConstantsUtils.TipoVeiculo.CODIGO_CAMINHOES_MICRO_ONIBUS:
                return ConstantsUtils.TipoVeiculo.DESC_CODIGO_CAMINHOES_MICRO_ONIBUS;
        }
        return "";
    }
}
