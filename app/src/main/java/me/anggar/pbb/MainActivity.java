package me.anggar.pbb;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] viewIds = {
                R.id.btn_contact, R.id.btn_music, R.id.btn_pic,
                R.id.btn_camera, R.id.btn_youtube, R.id.btn_browser
        };

        for (int viewId : viewIds) {
            Button btn = findViewById(viewId);
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_contact:
                intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
                break;
            case R.id.btn_music:
                intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                break;
            case R.id.btn_pic:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("image/*");
                break;
            case R.id.btn_camera:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                break;
            case R.id.btn_youtube:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtu.be"));
                break;
            case R.id.btn_browser:
                intent = new Intent(this, Browser.class);
            default:
                break;
        }

        startActivity(intent);
    }
}