package udacity.com.core.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "combustivel_modelo_ano")
public class CombustivelModeloAno implements Parcelable {

    @PrimaryKey
    private Long auto_id;

    @JsonProperty("value")
    private String id;

    @Override
    public String toString() {
        return "CombustivelModeloAno{" +
                "auto_id=" + auto_id +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @JsonProperty("label")
    private String name;

    public CombustivelModeloAno() {
    }

    protected CombustivelModeloAno(Parcel in) {
        if (in.readByte() == 0) {
            auto_id = null;
        } else {
            auto_id = in.readLong();
        }
        id = in.readString();
        name = in.readString();
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
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CombustivelModeloAno> CREATOR = new Creator<CombustivelModeloAno>() {
        @Override
        public CombustivelModeloAno createFromParcel(Parcel in) {
            return new CombustivelModeloAno(in);
        }

        @Override
        public CombustivelModeloAno[] newArray(int size) {
            return new CombustivelModeloAno[size];
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

    public CombustivelModeloAno(Long auto_id, String id, String name) {
        this.auto_id = auto_id;
        this.id = id;
        this.name = name;
    }
}
