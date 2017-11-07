package udacity.com.core.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Marca implements Parcelable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("fipe_name")
    private String fipe_name;

    @JsonProperty("order")
    private String order;

    @JsonProperty("key")
    private String key;

    @JsonProperty("id")
    private String id;

    public Marca(){
    }

    protected Marca(Parcel in) {
        name = in.readString();
        fipe_name = in.readString();
        order = in.readString();
        key = in.readString();
        id = in.readString();
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

    public String getFipe_name() {
        return fipe_name;
    }

    public void setFipe_name(String fipe_name) {
        this.fipe_name = fipe_name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        parcel.writeString(name);
        parcel.writeString(fipe_name);
        parcel.writeString(order);
        parcel.writeString(key);
        parcel.writeString(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Marca marca = (Marca) o;

        if (!name.equals(marca.name)) return false;
        if (!fipe_name.equals(marca.fipe_name)) return false;
        if (!order.equals(marca.order)) return false;
        if (!key.equals(marca.key)) return false;
        return id.equals(marca.id);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + fipe_name.hashCode();
        result = 31 * result + order.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
