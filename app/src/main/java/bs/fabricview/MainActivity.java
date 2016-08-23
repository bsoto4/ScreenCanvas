package bs.fabricview;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private FabricView fabricView;
    private Button btnClean, btnSave;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabricView = (FabricView) findViewById(R.id.fv);
        btnClean = (Button) findViewById(R.id.btn_clean);
        btnSave = (Button) findViewById(R.id.btn_save);

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabricView.cleanPage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = fabricView.getCanvasBitmap();
                try {
                    saveImage(bitmap, "imagen.jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveImage(Bitmap finalBitmap, String filename) throws IOException {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String fname = filename;
        File file = new File(myDir, fname);
        if (file.exists()){
            Log.e(TAG, "Imagen si existe, eliminando...");
            file.delete();
        }else{
            file.createNewFile();
            Log.e(TAG, "Imagen no existe, creandola");
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e(TAG, "imagen guardada");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "no se pudo guardar la imagen");
        }
    }
}
