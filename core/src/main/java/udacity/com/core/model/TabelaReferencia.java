package udacity.com.core.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TabelaReferencia implements Parcelable {

    @NonNull
    @JsonProperty("codigo")
    private String id;

    @JsonProperty("mes")
    private String mes;

    protected TabelaReferencia(Parcel in) {
        id = in.readString();
        mes = in.readString();
    }

    public static final Creator<TabelaReferencia> CREATOR = new Creator<TabelaReferencia>() {
        @Override
        public TabelaReferencia createFromParcel(Parcel in) {
            return new TabelaReferencia(in);
        }

        @Override
        public TabelaReferencia[] newArray(int size) {
            return new TabelaReferencia[size];
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

    public TabelaReferencia() {

    }
}
