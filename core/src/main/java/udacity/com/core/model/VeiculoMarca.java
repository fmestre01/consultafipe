package udacity.com.core.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VeiculoMarca implements Parcelable {

    @NonNull
    @JsonProperty("value")
    private String id;

    @JsonProperty("label")
    private String name;

    public VeiculoMarca() {
    }

    private VeiculoMarca(Parcel in) {
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

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
