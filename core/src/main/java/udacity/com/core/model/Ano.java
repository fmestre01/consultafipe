package udacity.com.core.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "ano")
public class Ano implements Parcelable {

    @PrimaryKey
    private Long auto_id;

    @JsonProperty("value")
    private String id;

    @JsonProperty("label")
    private String name;

    public Ano() {
    }

    public Ano(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Ano(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VeiculoMarca> CREATOR = new Creator<VeiculoMarca>() {
        @Override
        public VeiculoMarca createFromParcel(Parcel in) {
            return new VeiculoMarca(in);
        }

        @Override
        public VeiculoMarca[] newArray(int size) {
            return new VeiculoMarca[size];
        }
    };

    public Long getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(Long auto_id) {
        this.auto_id = auto_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
