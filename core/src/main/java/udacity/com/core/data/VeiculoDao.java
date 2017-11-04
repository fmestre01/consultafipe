package udacity.com.core.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import udacity.com.core.data.local.VeiculoEntity;
import udacity.com.core.model.Veiculo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface VeiculoDao {

    @Query("SELECT * FROM veiculo ORDER BY name ASC")
    LiveData<List<VeiculoEntity>> veiculosByNomeAsc();

    @Query("SELECT * FROM veiculo")
    List<VeiculoEntity> todosVeiculos();

    @Query("SELECT * FROM veiculo WHERE id=:id")
    Veiculo veiculoPorId(String id);

    @Insert(onConflict = IGNORE)
    long insertVeiculo(VeiculoEntity veiculoEntity);

    @Update
    int updateVeiculo(VeiculoEntity veiculoEntity);

    @Update
    void updateVeiculo(List<VeiculoEntity> veiculos);

    @Delete
    void deleteVeiculo(VeiculoEntity veiculoEntity);

    @Query("DELETE FROM veiculo")
    void deleteTodosVeiculos();
}
