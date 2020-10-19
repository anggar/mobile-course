package me.anggar.pbb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nrp, nama, email, telp;
    private SQLiteDatabase mDb;
    private SQLiteOpenHelper openDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nrp = findViewById(R.id.nrp);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        telp = findViewById(R.id.telp);

        int[] clickableViewIds = { R.id.save, R.id.getData };

        for (int viewId : clickableViewIds) {
            Button btn = findViewById(viewId);
            btn.setOnClickListener(this);
        }

        openDb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        mDb = openDb.getWritableDatabase();
        mDb.execSQL("CREATE TABLE IF NOT EXISTS mhs(nrp TEXT, nama TEXT, email TEXT, telp TEXT);");
    }

    @Override
    protected void onStop() {
        mDb.close();
        openDb.close();
        super.onStop();
    }

    @Override
    protected void onResume() {
        mDb = openDb.getWritableDatabase();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                dbSave();
                break;
            case R.id.getData:
                dbGet();
                break;
        }
    }

    private void clearInput() {
        int[] inputViewIds = { R.id.nrp, R.id.nama, R.id.email, R.id.telp };

        for (int viewId : inputViewIds) {
            EditText et = findViewById(viewId);
            et.setText("");
        }
    }

    private void notify(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void dbSave() {
        ContentValues data = new ContentValues();

        data.put("nrp", nrp != null ? nrp.getText().toString() : "");
        data.put("nama", nama != null ? nama.getText().toString() : "");
        data.put("email", email != null ? email.getText().toString() : "");
        data.put("telp", telp != null ? telp.getText().toString() : "");

        mDb.insert("mhs", null, data);

        notify("Data saved");
        clearInput();
    }

    private void dbGet() {
        String nrpText = nrp.getText().toString();
        Cursor curr = mDb.rawQuery("SELECT * FROM mhs where nrp = '" + nrpText + "'", null);

        if (curr.getCount() > 0) {
            notify("Found the data");
            curr.moveToFirst();

            nama.setText(curr.getString(curr.getColumnIndex("nama")));
            email.setText(curr.getString(curr.getColumnIndex("email")));
            telp.setText(curr.getString(curr.getColumnIndex("telp")));
        } else {
            notify("Data not found");
            clearInput();
        }
    }
}