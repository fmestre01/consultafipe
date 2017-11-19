package udacity.com.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Marca implements Parcelable {

    @JsonProperty("value")
    private String id;

    @JsonProperty("label")
    private String name;

    public Marca() {
    }

    protected Marca(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Marca> CREATOR = new Creator<Marca>() {
        @Override
        public Marca createFromParcel(Parcel in) {
            return new Marca(in);
        }

        @Override
        public Marca[] newArray(int size) {
            return new Marca[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Marca marca = (Marca) o;

        if (!name.equals(marca.name)) return false;
        return id.equals(marca.id);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
