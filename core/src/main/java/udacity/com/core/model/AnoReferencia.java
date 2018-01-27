package udacity.com.core.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "ano_referencia")
public class AnoReferencia implements Parcelable {

    @PrimaryKey
    private Long auto_id;

    @JsonProperty("codigo")
    private String id;

    @JsonProperty("mes")
    private String mes;

    protected AnoReferencia(Parcel in) {
        id = in.readString();
        mes = in.readString();
    }

    @Ignore
    public AnoReferencia() {
        this.id = "";
        this.mes = "";
    }

    public static final Creator<AnoReferencia> CREATOR = new Creator<AnoReferencia>() {
        @Override
        public AnoReferencia createFromParcel(Parcel in) {
            return new AnoReferencia(in);
        }

        @Override
        public AnoReferencia[] newArray(int size) {
            return new AnoReferencia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(mes);
    }

    public AnoReferencia(String id, String mes) {
        this.id = id;
        this.mes = mes;
    }

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

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
