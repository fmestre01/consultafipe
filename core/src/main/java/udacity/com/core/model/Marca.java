package udacity.com.core.model;

import com.google.gson.annotations.SerializedName;

public class Marca {
    @SerializedName("name")
    private String name;

    @SerializedName("fipe_name")
    private String fipe_name;

    @SerializedName("order")
    private String order;

    @SerializedName("key")
    private String key;

    @SerializedName("id")
    private String id;

    public Marca(){

    }

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
}
