package udacity.com.core.util;

public class ConsultaFipeUtil {

    public static boolean isVeiculoNovo(String anoVeiculo) {
        if (anoVeiculo.substring(0, 4).equals(ConstantsUtils.TipoVeiculo.ZERO_KM)) {
            return true;
        }
        return false;
    }
}
