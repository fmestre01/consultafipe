package udacity.com.core.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "marca")
public class MarcaEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String fipe_name;
    private String order;
    private String key;

    @Ignore
    public MarcaEntity() {
        this.name = "";
        this.fipe_name = "";
        this.order = "";
        this.key = "";
    }

    public MarcaEntity(String name, String fipe_name, String order, String key) {
        this.name = name;
        this.fipe_name = fipe_name;
        this.order = order;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFipe_name() {
        return fipe_name;
    }

    public void setFipe_name(String fipe_name) {
        this.fipe_name = fipe_name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
