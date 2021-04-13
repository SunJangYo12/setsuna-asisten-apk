package com.setsunajin.asisten;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ndk_satu.MainActivity;
import com.example.ndk_satu.R;
import com.setsunajin.asisten.memori.SharedMemori;
import com.setsunajin.asisten.task.ServiceStatus;

public class MainPengaturan extends AppCompatActivity {
    private SharedMemori shMemori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        shMemori = new SharedMemori(this);

        Switch swService = (Switch)findViewById(R.id.pengaturan_sw_service);
        swService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shMemori.setSharedMemori("services", swService.isChecked());
                if (!shMemori.getSharedMemori("services")) {
                    stopService(new Intent(MainPengaturan.this, ServiceStatus.class));
                    stopService(new Intent(MainPengaturan.this, ServicesBoot.class));
                } else {
                    startService(new Intent(MainPengaturan.this, ServiceStatus.class));
                    startService(new Intent(MainPengaturan.this, ServicesBoot.class));
                }
            }
        });
        swService.setChecked(shMemori.getSharedMemori("services"));

        Switch swTouch = (Switch)findViewById(R.id.pengaturan_sw_touch);
        swTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shMemori.setSharedMemori("touch", swTouch.isChecked());
            }
        });
        swTouch.setChecked(shMemori.getSharedMemori("touch"));

        Switch swNotifi = (Switch)findViewById(R.id.pengaturan_sw_notificatatan);
        swNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shMemori.setSharedMemori("notifi_catatan", swNotifi.isChecked());
            }
        });
        swNotifi.setChecked(shMemori.getSharedMemori("notifi_catatan"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.FIRST, 1, 1, "Exit").setIcon(R.drawable.icon_css);
        menu.add(Menu.FIRST, 2, 1, "Tes").setIcon(R.drawable.icon_css);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            finish();
        }
        if (item.getItemId() == 2) {
            Toast.makeText(MainPengaturan.this, ""+shMemori.getSharedMemori("services"), Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}