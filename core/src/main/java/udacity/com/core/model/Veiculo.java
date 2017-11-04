package udacity.com.core.model;

import com.google.gson.annotations.SerializedName;

public class Veiculo {

    @SerializedName("id")
    private String id;

    @SerializedName("ano_modelo")
    private String ano_modelo;

    @SerializedName("marca")
    private String marca;

    @SerializedName("name")
    private String name;

    @SerializedName("veiculo")
    private String veiculo;

    @SerializedName("preco")
    private String preco;

    @SerializedName("combustivel")
    private String combustivel;

    @SerializedName("referencia")
    private String referencia;

    @SerializedName("fipe_codigo")
    private String fipe_codigo;

    @SerializedName("key")
    private String key;

    @SerializedName("time")
    private String time;

    public Veiculo() {
    }

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
}
