package udacity.com.core.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import udacity.com.core.model.CombustivelModeloAno;

@Dao
public interface VeiculoModeloAnoDao {
    @Insert
    void insertVeiculosModeloAno(CombustivelModeloAno combustivelModeloAnoEntity);
}
