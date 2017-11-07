package udacity.com.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VeiculoModeloAno implements Parcelable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("fipe_marca")
    private String fipeMarca;

    @JsonProperty("fipe_codigo")
    private String fipeCodigo;

    @JsonProperty("name")
    private String name;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("key")
    private String key;

    @JsonProperty("veiculo")
    private String veiculo;

    public VeiculoModeloAno() {
    }

    public VeiculoModeloAno(String id, String fipeMarca, String fipeCodigo, String name, String marca, String key, String veiculo) {
        this.id = id;
        this.fipeMarca = fipeMarca;
        this.fipeCodigo = fipeCodigo;
        this.name = name;
        this.marca = marca;
        this.key = key;
        this.veiculo = veiculo;
    }

    protected VeiculoModeloAno(Parcel in) {
        id = in.readString();
        fipeMarca = in.readString();
        fipeCodigo = in.readString();
        name = in.readString();
        marca = in.readString();
        key = in.readString();
        veiculo = in.readString();
    }

    public static final Creator<VeiculoModeloAno> CREATOR = new Creator<VeiculoModeloAno>() {
        @Override
        public VeiculoModeloAno createFromParcel(Parcel in) {
            return new VeiculoModeloAno(in);
        }

        @Override
        public VeiculoModeloAno[] newArray(int size) {
            return new VeiculoModeloAno[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFipeMarca() {
        return fipeMarca;
    }

    public void setFipeMarca(String fipeMarca) {
        this.fipeMarca = fipeMarca;
    }

    public String getFipeCodigo() {
        return fipeCodigo;
    }

    public void setFipeCodigo(String fipeCodigo) {
        this.fipeCodigo = fipeCodigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(fipeMarca);
        parcel.writeString(fipeCodigo);
        parcel.writeString(name);
        parcel.writeString(marca);
        parcel.writeString(key);
        parcel.writeString(veiculo);
    }
}
