package udacity.com.core.util;

public class ConsultaFipeUtils {

    public static boolean isVeiculoNovo(String anoVeiculo) {
        return anoVeiculo.substring(0, 4).equals(ConstantsUtils.TipoVeiculo.ZERO_KM);
    }
}
