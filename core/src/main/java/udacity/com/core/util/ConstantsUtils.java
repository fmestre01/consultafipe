package udacity.com.core.util;

public class ConstantsUtils {

    public interface InfoLog {
        String ERROR = "Erro - favor tentar novamente mais tarde";
        String SEL_ANO = "Selecione o ano";
        String INFO = "info";
        String UNAUTHORIZED = "Sem autorização para a transação";
        String SOON = "Em breve...";
    }

    public interface Application {
        String INITAPPLICATION = "initApplication";
        String BASE_URL = "http://fipeapi.appspot.com/api/1/carros/";
        String CONSULTA_FIPE = "Consulta Fipe";
        String VEICULOS = "Veículos";
    }

    public interface ListLog {
        String ERROR = "Erro ao recuperar itens";
    }

    public interface Urls {
        String SITE_FIPE = "http://veiculos.fipe.org.br/api/veiculos/";
        String HEADER_REFERER = "Referer";
        String HEADER_REFERER_VALUE = "http://veiculos.fipe.org.br/";

        String OP_KEY_MARCAS = "ConsultarMarcas";
        String OP_KEY_VEICULOS_MARCA = "ConsultarModelos";
        String OP_KEY_VEICULO_DETALHE = "ConsultarValorComTodosParametros";
        String OP_KEY_ANO_MODELO = "ConsultarAnoModelo";
        String OP_KEY_TABELA_REFERENCIA = "ConsultarTabelaDeReferencia";
    }

    public interface RequestParameters {
        String CODIGO_TABELA_REFERENCIA = "codigoTabelaReferencia";
        String CODIGO_TIPO_VEICULO = "codigoTipoVeiculo";
    }

    public interface Data {
        String SAVED_ANO_REFERENCIA = "ano_referencia_local";
    }

    public interface TipoVeiculo {
        String CODIGO_CARROS_UTILITARIOS_PEQUENOS = "1";
        String CODIGO_MOTOS = "2";
        String CODIGO_CAMINHOES_MICRO_ONIBUS = "3";
        String DESC_CARROS_UTILITARIOS_PEQUENOS = "Carros e utilitários pequenos";
        String DESC_CODIGO_MOTOS = "Motos";
        String DESC_CODIGO_CAMINHOES_MICRO_ONIBUS = "Caminhões e micro-ônibus";
        String ZERO_KM = "3200";
        String VEICULO_ZERO_KM = "Zero km";
    }

    public interface TrackEvent {
        String TRACK_CONSULTA_FIPE = "CONSULTA_FIPE";
        String TRACK_SCREEN_ACTION = "ACTION";
        String SCREEN_MARCA = "MARCA";
        String SCREEN_VEICULOS_MARCA = "VEICULOS_MARCA";
        String SCREEN_VEICULOS_DETALHE = "VEICULOS_DETALHE";

    }

    public interface AdMob {
        String KEY = "ca-app-pub-7012787268546106~8197623604";
    }

    public interface Api {
        int HTTP_READ_TIMEOUT = 10;
        int HTTP_CONNECT_TIMEOUT = 6;
    }

    public interface TimeoutSystem {
        long CLICK_TIMEOUT_LAST_CLICK = 2000;
    }

    public interface Firebase {
        String USUARIOS_FIREBASE = "usuarios";
    }

}