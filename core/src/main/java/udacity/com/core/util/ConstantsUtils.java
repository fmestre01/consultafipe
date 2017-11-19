package udacity.com.core.util;

public class ConstantsUtils {

    public interface InfoLog {
        String ERROR = "Erro - favor tentar novamente mais tarde";
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

        String CODIGO_TIPO_VEICULO = "1";
    }

    public interface RequestParameters {
        //Carros e utilitários pequenos
        String CODIGO_TABELA_REFERENCIA = "codigoTabelaReferencia";
        String CODIGO_TIPO_VEICULO = "codigoTipoVeiculo";
        String VALOR_TABELA_REFERENCIA = "219";
        String VALOR_TIPO_VEICULO = "1";
    }
}