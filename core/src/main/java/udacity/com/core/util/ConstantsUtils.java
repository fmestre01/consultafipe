package udacity.com.core.util;

public class ConstantsUtils {

    public interface InfoLog {
        String ERROR = "Erro - favor tentar novamente mais tarde";
        String SEL_ANO = "Selecione o ano";
        String INFO = "info";
        String UNAUTHORIZED = "Sem autorização para a transação";
    }

    public interface Application {
        String INITAPPLICATION = "initApplication";
        String BASE_URL = "http://fipeapi.appspot.com/api/1/carros/";
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

        String CODIGO_TIPO_VEICULO = "1";
    }

    public interface RequestParameters {
        //Carros e utilitários pequenos
        String CODIGO_TABELA_REFERENCIA = "codigoTabelaReferencia";
        String CODIGO_TIPO_VEICULO = "codigoTipoVeiculo";
        String VALOR_TABELA_REFERENCIA = "221";
        String VALOR_TIPO_VEICULO = "1";
    }

    public interface Data{
        String SAVE_DATA_ANO_REFERENCIA = "ano_referencia_local";
    }
}