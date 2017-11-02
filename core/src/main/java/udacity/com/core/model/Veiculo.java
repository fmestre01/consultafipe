package udacity.com.core.model;

import com.google.gson.annotations.SerializedName;

public class Veiculo {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("fipe_marca")
    private String fipeMarca;

    @SerializedName("marca")
    private String marca;

    @SerializedName("key")
    private String key;

    @SerializedName("fipe_name")
    private String fipeName;

    public Veiculo(String id, String name, String fipeMarca, String marca, String key, String fipeName) {
        this.id = id;
        this.name = name;
        this.fipeMarca = fipeMarca;
        this.marca = marca;
        this.key = key;
        this.fipeName = fipeName;
    }

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

    public String getFipeMarca() {
        return fipeMarca;
    }

    public void setFipeMarca(String fipeMarca) {
        this.fipeMarca = fipeMarca;
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

    public String getFipeName() {
        return fipeName;
    }

    public void setFipeName(String fipeName) {
        this.fipeName = fipeName;
    }
}
