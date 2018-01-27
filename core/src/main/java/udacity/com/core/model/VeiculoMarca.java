package udacity.com.core.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "veiculo_marca")
public class VeiculoMarca implements Parcelable {

    @PrimaryKey
    private Long auto_id;

    @JsonProperty("value")
    private String id;

    @JsonProperty("label")
    private String name;

    @Ignore
    private List<CombustivelModeloAno> combustivelModeloAnos;

    public VeiculoMarca() {
    }


    protected VeiculoMarca(Parcel in) {
        if (in.readByte() == 0) {
            auto_id = null;
        } else {
            auto_id = in.readLong();
        }
        id = in.readString();
        name = in.readString();
        combustivelModeloAnos = in.createTypedArrayList(CombustivelModeloAno.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (auto_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(auto_id);
        }
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(combustivelModeloAnos);
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

    public List<CombustivelModeloAno> getCombustivelModeloAnos() {
        if (combustivelModeloAnos == null) {
            combustivelModeloAnos = new ArrayList<>();
        }
        return combustivelModeloAnos;
    }

    public void setCombustivelModeloAnos(List<CombustivelModeloAno> combustivelModeloAnos) {
        this.combustivelModeloAnos = combustivelModeloAnos;
    }
}
