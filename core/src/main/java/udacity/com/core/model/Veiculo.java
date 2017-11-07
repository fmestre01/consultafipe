package udacity.com.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Veiculo implements Parcelable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("ano_modelo")
    private String ano_modelo;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("name")
    private String name;

    @JsonProperty("veiculo")
    private String veiculo;

    @JsonProperty("preco")
    private String preco;

    @JsonProperty("combustivel")
    private String combustivel;

    @JsonProperty("referencia")
    private String referencia;

    @JsonProperty("fipe_codigo")
    private String fipe_codigo;

    @JsonProperty("key")
    private String key;

    @JsonProperty("time")
    private String time;

    public Veiculo() {
    }

    protected Veiculo(Parcel in) {
        id = in.readString();
        ano_modelo = in.readString();
        marca = in.readString();
        name = in.readString();
        veiculo = in.readString();
        preco = in.readString();
        combustivel = in.readString();
        referencia = in.readString();
        fipe_codigo = in.readString();
        key = in.readString();
        time = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAno_modelo() {
        return ano_modelo;
    }

    public void setAno_modelo(String ano_modelo) {
        this.ano_modelo = ano_modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
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

    public String getFipe_codigo() {
        return fipe_codigo;
    }

    public void setFipe_codigo(String fipe_codigo) {
        this.fipe_codigo = fipe_codigo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(ano_modelo);
        parcel.writeString(marca);
        parcel.writeString(name);
        parcel.writeString(veiculo);
        parcel.writeString(preco);
        parcel.writeString(combustivel);
        parcel.writeString(referencia);
        parcel.writeString(fipe_codigo);
        parcel.writeString(key);
        parcel.writeString(time);
    }
}
