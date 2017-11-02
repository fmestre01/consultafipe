package udacity.com.core.model;

import com.google.gson.annotations.SerializedName;

public class VeiculoModeloAno {

    @SerializedName("id")
    private String id;

    @SerializedName("fipe_marca")
    private String fipeMarca;

    @SerializedName("fipe_codigo")
    private String fipeCodigo;

    @SerializedName("name")
    private String name;

    @SerializedName("marca")
    private String marca;

    @SerializedName("key")
    private String key;

    @SerializedName("veiculo")
    private String veiculo;

    public VeiculoModeloAno(String id, String fipeMarca, String fipeCodigo, String name, String marca, String key, String veiculo) {
        this.id = id;
        this.fipeMarca = fipeMarca;
        this.fipeCodigo = fipeCodigo;
        this.name = name;
        this.marca = marca;
        this.key = key;
        this.veiculo = veiculo;
    }

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
}
