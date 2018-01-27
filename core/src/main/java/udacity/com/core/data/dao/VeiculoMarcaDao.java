package udacity.com.core.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import udacity.com.core.model.VeiculoMarca;

@Dao
public interface VeiculoMarcaDao {

    @Insert
    void insertVeiculosMarca(VeiculoMarca veiculoMarca);

    @Query("SELECT * FROM veiculo_marca")
    List<VeiculoMarca> todosVeiculosMarca();
}
