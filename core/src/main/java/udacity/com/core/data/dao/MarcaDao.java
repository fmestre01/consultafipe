package udacity.com.core.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import udacity.com.core.data.entity.MarcaEntity;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MarcaDao {

    @Query("SELECT * FROM marca ORDER BY name ASC")
    LiveData<List<MarcaEntity>> marcasByNomeAsc();

    @Query("SELECT * FROM marca")
    List<MarcaEntity> todasMarcas();

    @Query("SELECT * FROM marca WHERE id=:id")
    MarcaEntity marcaPorId(String id);

    @Insert(onConflict = IGNORE)
    long insertMarca(MarcaEntity marcaEntity);

    @Update
    int updateMarca(MarcaEntity marcaEntity);

    @Update
    void updateMarca(List<MarcaEntity> marcasEntity);

    @Delete
    void deleteMarca(MarcaEntity marcaEntity);

    @Query("DELETE FROM marca")
    void deleteTodasMarcas();

    @Query("SELECT COUNT(*) FROM marca")
    int count();
}
