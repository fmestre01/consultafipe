package udacity.com.core.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "veiculo")
public class VeiculoEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;
    private String ano_modelo;
    private String marca;
    private String name;
    private String veiculo;
    private String preco;
    private String combustivel;
    private String referencia;
    private String fipe_codigo;
    private String key;
    private String time;

    @Ignore
    public VeiculoEntity() {
        this.ano_modelo = "";
        this.marca = "";
        this.name = "";
        this.veiculo = "";
        this.preco = "";
        this.combustivel = "";
        this.referencia = "";
        this.fipe_codigo = "";
        this.key = "";
        this.time = "";
    }

    public VeiculoEntity(long id, String ano_modelo, String marca, String name, String veiculo, String preco, String combustivel, String referencia, String fipe_codigo, String key, String time) {
        this.id = id;
        this.ano_modelo = ano_modelo;
        this.marca = marca;
        this.name = name;
        this.veiculo = veiculo;
        this.preco = preco;
        this.combustivel = combustivel;
        this.referencia = referencia;
        this.fipe_codigo = fipe_codigo;
        this.key = key;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
    public String toString() {
        return "VeiculoEntity{" +
                "id=" + id +
                ", ano_modelo='" + ano_modelo + '\'' +
                ", marca='" + marca + '\'' +
                ", name='" + name + '\'' +
                ", veiculo='" + veiculo + '\'' +
                ", preco='" + preco + '\'' +
                ", combustivel='" + combustivel + '\'' +
                ", referencia='" + referencia + '\'' +
                ", fipe_codigo='" + fipe_codigo + '\'' +
                ", key='" + key + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
