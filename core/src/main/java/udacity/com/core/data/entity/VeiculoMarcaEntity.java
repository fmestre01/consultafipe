package udacity.com.core.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "veiculo_marca")
public class VeiculoMarcaEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String fipe_marca;
    private String marca;
    private String key;
    private String fipe_name;

    @Ignore
    public VeiculoMarcaEntity() {
        this.name = "";
        this.fipe_marca = "";
        this.marca = "";
        this.key = "";
        this.fipe_name = "";
    }

    public VeiculoMarcaEntity(String name, String fipe_marca, String marca, String key, String fipe_name) {
        this.name = name;
        this.fipe_marca = fipe_marca;
        this.marca = marca;
        this.key = key;
        this.fipe_name = fipe_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFipe_marca() {
        return fipe_marca;
    }

    public void setFipe_marca(String fipe_marca) {
        this.fipe_marca = fipe_marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFipe_name() {
        return fipe_name;
    }

    public void setFipe_name(String fipe_name) {
        this.fipe_name = fipe_name;
    }
}
