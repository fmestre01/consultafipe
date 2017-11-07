package udacity.com.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VeiculoMarca implements Parcelable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fipe_marca")
    private String fipe_marca;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("key")
    private String key;

    @JsonProperty("fipe_name")
    private String fipe_name;

    public VeiculoMarca() {
    }

    public VeiculoMarca(String id, String name, String fipe_marca, String marca, String key, String fipe_name) {
        this.id = id;
        this.name = name;
        this.fipe_marca = fipe_marca;
        this.marca = marca;
        this.key = key;
        this.fipe_name = fipe_name;
    }

    protected VeiculoMarca(Parcel in) {
        id = in.readString();
        name = in.readString();
        fipe_marca = in.readString();
        marca = in.readString();
        key = in.readString();
        fipe_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(fipe_marca);
        dest.writeString(marca);
        dest.writeString(key);
        dest.writeString(fipe_name);
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

    public String getFipe_marca() {
        return fipe_marca;
    }

    public void setFipe_marca(String fipe_marca) {
        this.fipe_marca = fipe_marca;
    }

    public String getFipe_name() {
        return fipe_name;
    }

    public void setFipe_name(String fipe_name) {
        this.fipe_name = fipe_name;
    }
}
