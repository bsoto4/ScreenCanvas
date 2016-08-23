package bs.screencanvas.activity;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import bs.fabricview.R;
import bs.screencanvas.widget.ScreenCanvas;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ScreenCanvas screenCanvas;
    private Button btnClean, btnSave;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenCanvas = (ScreenCanvas) findViewById(R.id.fv);
        btnClean = (Button) findViewById(R.id.btn_clean);
        btnSave = (Button) findViewById(R.id.btn_save);
        edtName = (EditText) findViewById(R.id.edt_name);

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenCanvas.cleanPage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = edtName.getText() + ".jpg";
                if (!fileName.equals(".jpg")) {
                    Bitmap bitmap = screenCanvas.getCanvasBitmap();
                    try {
                        saveImage(bitmap, fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Snackbar.make(getCurrentFocus(), "No olvides el nombre", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveImage(Bitmap finalBitmap, String filename) throws IOException {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/dibujitos");
        myDir.mkdirs();
        String fname = filename;
        File file = new File(myDir, fname);
        if (file.exists()) {
            file.delete();
        } else {
            file.createNewFile();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Snackbar.make(getCurrentFocus(), "Imagen guardada", Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
