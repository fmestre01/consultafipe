package udacity.com.core.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import udacity.com.core.model.Marca;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MarcaDao {

    @Query("SELECT * FROM marca ORDER BY name ASC")
    LiveData<List<Marca>> marcasByNomeAsc();

    @Query("SELECT * FROM marca")
    List<Marca> todasMarcas();

    @Query("SELECT * FROM marca WHERE id=:id")
    Marca marcaPorId(String id);

    @Insert(onConflict = IGNORE)
    long insertMarca(Marca Marca);

    @Update
    int updateMarca(Marca Marca);

    @Update
    void updateMarca(List<Marca> marcasEntity);

    @Delete
    void deleteMarca(Marca Marca);

    @Query("DELETE FROM marca")
    void deleteTodasMarcas();

    @Query("SELECT COUNT(*) FROM marca")
    int count();
}
