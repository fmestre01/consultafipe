package udacity.com.core.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import udacity.com.core.data.dao.MarcaDao;
import udacity.com.core.data.dao.VeiculoDao;
import udacity.com.core.data.dao.VeiculoMarcaDao;
import udacity.com.core.data.dao.VeiculoModeloAnoDao;
import udacity.com.core.data.entity.MarcaEntity;
import udacity.com.core.data.entity.VeiculoEntity;
import udacity.com.core.data.entity.VeiculoMarcaEntity;
import udacity.com.core.data.entity.VeiculoModeloAnoEntity;

@Database(entities = {VeiculoEntity.class, MarcaEntity.class, VeiculoMarcaEntity.class, VeiculoModeloAnoEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract VeiculoDao veiculoDao();

    public abstract MarcaDao marcaDao();

    public abstract VeiculoMarcaDao veiculoMarcaDao();

    public abstract VeiculoModeloAnoDao veiculoModeloAnoDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "veiculos_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static AppDatabase getMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
