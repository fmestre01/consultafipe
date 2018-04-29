package firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UsuariosFirebase {

    public String board;
    public String brand;
    public String cpuAbi;
    public String device;
    public String display;
    public String host;
    public String id;
    public String manufacturer;
    public String model;
    public String product;
    public String tags;
    public String type;
    public String user;

    public UsuariosFirebase() {
    }

    public UsuariosFirebase(String board, String brand, String cpuAbi, String device,
                            String display, String host, String id, String manufacturer,
                            String model, String product, String tags, String type, String user) {
        this.board = board;
        this.brand = brand;
        this.cpuAbi = cpuAbi;
        this.device = device;
        this.display = display;
        this.host = host;
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.product = product;
        this.tags = tags;
        this.type = type;
        this.user = user;
    }
}
