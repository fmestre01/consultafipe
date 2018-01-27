package udacity.com.core.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import udacity.com.core.model.Veiculo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface VeiculoDao {

    @Query("SELECT * FROM veiculo ORDER BY modelo ASC")
    LiveData<List<Veiculo>> veiculosByNomeAsc();

    @Query("SELECT * FROM veiculo")
    List<Veiculo> todosVeiculos();

    @Query("SELECT * FROM veiculo WHERE auto_id=:id")
    Veiculo veiculoPorId(String id);

    @Insert(onConflict = IGNORE)
    long insertVeiculo(Veiculo Veiculo);

    @Update
    int updateVeiculo(Veiculo Veiculo);

    @Update
    void updateVeiculo(List<Veiculo> veiculos);

    @Delete
    void deleteVeiculo(Veiculo Veiculo);

    @Query("DELETE FROM veiculo")
    void deleteTodosVeiculos();

    @Query("SELECT * FROM veiculo WHERE marca=:marca")
    public List<Veiculo> veiculosPorMarca(String marca);
}
