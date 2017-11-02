package udacity.com.core.model;

import com.google.gson.annotations.SerializedName;

public class Marca {
    @SerializedName("name")
    private String name;

    @SerializedName("fipe_name")
    private String fipeName;

    @SerializedName("order")
    private String order;

    @SerializedName("key")
    private String key;

    @SerializedName("id")
    private String id;

    public Marca(String name, String fipeName, String order, String key, String id) {
        this.name = name;
        this.fipeName = fipeName;
        this.order = order;
        this.key = key;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFipeName() {
        return fipeName;
    }

    public void setFipeName(String fipeName) {
        this.fipeName = fipeName;
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
