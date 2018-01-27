package udacity.com.core.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import udacity.com.core.model.AnoReferencia;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AnoReferenciaDao {

    @Query("SELECT * FROM ano_referencia ORDER BY id DESC")
    List<AnoReferencia> todosAnos();

    @Query("SELECT * FROM ano_referencia WHERE id=:id")
    AnoReferencia anoReferenciaPorId(String id);

    @Insert(onConflict = IGNORE)
    long insertAnosReferencia(AnoReferencia anoReferenciaEntity);
}
