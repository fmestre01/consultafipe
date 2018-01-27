package udacity.com.core.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = "veiculo")
public class Veiculo implements Parcelable {

    @PrimaryKey
    private Long auto_id;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("modelo")
    private String modelo;

    @JsonProperty("anoModelo")
    private String anoModelo;

    @JsonProperty("valor")
    private String valor;

    @JsonProperty("combustivel")
    private String combustivel;

    @JsonProperty("mesReferencia")
    private String referencia;

    public Veiculo() {
    }

    protected Veiculo(Parcel in) {
        if (in.readByte() == 0) {
            auto_id = null;
        } else {
            auto_id = in.readLong();
        }
        marca = in.readString();
        modelo = in.readString();
        anoModelo = in.readString();
        valor = in.readString();
        combustivel = in.readString();
        referencia = in.readString();
    }

    public static final Creator<Veiculo> CREATOR = new Creator<Veiculo>() {
        @Override
        public Veiculo createFromParcel(Parcel in) {
            return new Veiculo(in);
        }

        @Override
        public Veiculo[] newArray(int size) {
            return new Veiculo[size];
        }
    };

    public Long getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(Long auto_id) {
        this.auto_id = auto_id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(String anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (auto_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(auto_id);
        }
        parcel.writeString(marca);
        parcel.writeString(modelo);
        parcel.writeString(anoModelo);
        parcel.writeString(valor);
        parcel.writeString(combustivel);
        parcel.writeString(referencia);
    }
}
