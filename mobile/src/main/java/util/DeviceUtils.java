package util;

import android.os.Build;

import com.google.firebase.database.DatabaseReference;

import firebase.UsuariosFirebase;
import udacity.com.core.util.ConstantsUtils;

public class DeviceUtils {

    public static void insertNewUsuarioFirebase(DatabaseReference firebaseDatabase) {
        UsuariosFirebase usuarioFirebase = new UsuariosFirebase(
                Build.BOARD,
                Build.BRAND,
                Build.CPU_ABI,
                Build.DEVICE,
                Build.DISPLAY,
                Build.HOST,
                Build.ID,
                Build.MANUFACTURER,
                Build.MODEL,
                Build.PRODUCT,
                Build.TAGS,
                Build.TYPE,
                Build.USER,
                Build.FINGERPRINT,
                Build.VERSION.CODENAME);
        firebaseDatabase.child(ConstantsUtils.Firebase.USUARIOS_FIREBASE).setValue(usuarioFirebase);
    }
}
