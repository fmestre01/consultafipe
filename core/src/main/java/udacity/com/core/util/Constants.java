package udacity.com.core.util;

public class Constants {

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
}