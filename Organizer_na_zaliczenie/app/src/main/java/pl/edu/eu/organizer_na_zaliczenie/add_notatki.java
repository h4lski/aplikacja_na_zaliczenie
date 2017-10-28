package pl.edu.eu.organizer_na_zaliczenie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import pl.edu.eu.organizer_na_zaliczenie.widget.LinedEditText;

public class add_notatki extends AppCompatActivity {

    EditText et;
    Bundle bundle = new Bundle();
    private String path = Environment.getExternalStorageDirectory().toString() + "/Organizer/Notatki";
    private final int MEMORY_ACCESS = 5;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_notatki, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_note) {
            Log.d("save","została zapsiana");
            createDir();
            createFile();
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktywnosc_add_notatki);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorCzarny));
        }
       et = (EditText) findViewById(R.id.text_notatki);
        et.setText(bundle.getString("et"));
        bundle.putString("et", et.getText().toString());
        if(ActivityCompat.shouldShowRequestPermissionRationale(add_notatki.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {}
        else {
            ActivityCompat.requestPermissions(add_notatki.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MEMORY_ACCESS);
        }

    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case MEMORY_ACCESS:
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Jeśli nie zostanie wyrażona zgoda na dostęp do pamięci, nie będzie możliwości zapisania pliku!", Toast.LENGTH_LONG).show();
        }
    }
    }

    public void createDir() {
        File folder = new File(path);
        if (!folder.exists()) {
            try {
                folder.mkdirs();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void createFile()
    {
        File file = new File(path+"/"+System.currentTimeMillis()+".txt");
        FileOutputStream fOut;
        OutputStreamWriter myOutWriter;
        try {
            fOut = new FileOutputStream(file);
            myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(et.getText());
            myOutWriter.close();
            fOut.close();;
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onPause() {
        Log.d("aktywność", "onPause");
        bundle.putString("et", et.getText().toString());
        super.onPause();
    }
}





