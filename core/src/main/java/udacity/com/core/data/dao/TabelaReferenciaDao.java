package udacity.com.core.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import udacity.com.core.model.TabelaReferencia;

@Dao
public interface TabelaReferenciaDao {

    @Query("SELECT * FROM tabela_referencia ORDER BY id DESC")
    List<TabelaReferencia> todasTabelasReferencia();

    @Query("SELECT * FROM tabela_referencia WHERE id=:id")
    TabelaReferencia tabelaReferenciaPorId(String id);

    @Insert
    long insertTabelaReferencia(TabelaReferencia tabelaReferencia);

    @Query("SELECT * FROM tabela_referencia")
    List<TabelaReferencia> allTabelaReferencia();
}
