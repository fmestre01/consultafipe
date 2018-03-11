package udacity.com.core.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "combustivel_modelo_ano")
public class CombustivelModeloAno implements Parcelable {

    @PrimaryKey
    @NonNull
    @JsonProperty("value")
    private String id;

    @JsonProperty("label")
    private String name;

    public CombustivelModeloAno() {
    }

    @Override
    public String toString() {
        return "CombustivelModeloAno{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    protected CombustivelModeloAno(Parcel in) {
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

    @Ignore
    public CombustivelModeloAno(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
