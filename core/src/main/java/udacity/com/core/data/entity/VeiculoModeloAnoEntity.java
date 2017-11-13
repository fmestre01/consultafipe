package udacity.com.core.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "veiculo_modelo_ano")
public class VeiculoModeloAnoEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String fipe_codigo;
    private String name;
    private String key;
    private String veiculo;

    @Ignore
    public VeiculoModeloAnoEntity() {
        this.name = "";
        this.key = "";
        this.name = "";
        this.key = "";
        this.veiculo = "";
    }

    public VeiculoModeloAnoEntity(long id, String fipe_codigo, String name, String key, String veiculo) {
        this.id = id;
        this.fipe_codigo = fipe_codigo;
        this.name = name;
        this.key = key;
        this.veiculo = veiculo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFipe_codigo() {
        return fipe_codigo;
    }

    public void setFipe_codigo(String fipe_codigo) {
        this.fipe_codigo = fipe_codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }
}
