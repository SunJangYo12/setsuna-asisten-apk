package com.setsunajin.asisten;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ndk_satu.MainActivity;
import com.example.ndk_satu.R;
import com.setsunajin.asisten.memori.MainMemori;

import org.w3c.dom.Text;

public class MainCatatan extends AppCompatActivity {
    private EditText edit_alert;
    private LinearLayout layout;
    private ListView list;
    private MainMemori memori;
    private int position = 0;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan);

        layout = (LinearLayout)findViewById(R.id.catatan_layout);

        memori = new MainMemori();
        int lenNote = memori.getStringCatatan(this).size();

        if (lenNote == 0) {
            TextView txtEmp = (TextView)findViewById(R.id.catatan_txt);
            txtEmp.setVisibility(View.VISIBLE);
        } else {
            for (int i=0; i<lenNote; i++) {
                createButton(this, i, memori.getStringCatatan(this).get(i));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.FIRST, 1, 1, "New").setIcon(R.drawable.icon_css);
        menu.add(Menu.FIRST, 2, 1, "Exit").setIcon(R.drawable.icon_css);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("ini", "note");
            startActivity(intent);
        }
        if (item.getItemId() == 2) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void createButton(Context context, int letak, String title) {
        LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (letak % 2 == 0) {
            bparams.gravity = Gravity.LEFT;
            bparams.leftMargin = 20;
        } else {
            bparams.gravity = Gravity.RIGHT;
            bparams.rightMargin = 20;
        }
        bparams.topMargin = 50;

        Button b1 = new Button(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert(letak);
            }
        });
        b1.setText(title);
        b1.setLayoutParams(bparams);
        layout.addView(b1);
    }

    private void alert(int posisi) {
        final CharSequence[] dialogitem = {"Open...", "Edit", "Hapus"};
        position = posisi;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainCatatan.this);
        builder.setTitle(memori.isCatatan(position, 2, MainCatatan.this));
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    alertOpen();
                }
                else if (item == 1) {
                    String[] rinci = {"Nama: \n"+memori.isCatatan(position, 0, MainCatatan.this),
                            "Content: \n"+memori.isCatatan(position, 1, MainCatatan.this),
                            "Date: \n"+memori.isCatatan(position, 2, MainCatatan.this) };
                    AlertDialog.Builder builderIndex1 = new AlertDialog.Builder(MainCatatan.this);
                    builderIndex1.setTitle("Rincian: "+position);
                    builderIndex1.setItems(rinci, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item)
                        {
                            String[] rinci = {"Nama: \n"+memori.isCatatan(position, 0, MainCatatan.this),
                                    "Content: \n"+memori.isCatatan(position, 1, MainCatatan.this),
                                    "Date: \n"+memori.isCatatan(position, 2, MainCatatan.this) };
                            if (item == 0) {
                                alertEdit(rinci[0], 0);
                            }
                            else if (item == 1) {
                                alertEdit(rinci[1], 1);
                            }
                            else if (item == 2) {
                                alertEdit(rinci[2], 2);
                            }
                        }
                    });
                    builderIndex1.create().show();
                }
                else if (item == 2) {
                    memori.position = position;
                    memori.setCatatan("rm", "", "", MainCatatan.this);
                    Toast.makeText(MainCatatan.this, "Saved", Toast.LENGTH_SHORT).show();
                    MainCatatan.this.finish();
                }
            }
        });
        builder.create().show();
    }

    private void alertEdit(String inEdit, int xindex) {
        index = xindex;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainCatatan.this);
        builder1.setTitle("Edit: "+inEdit);
        builder1.setCancelable(true);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View layout = inflater.inflate(R.layout.alert_catatan, null);

        edit_alert = (EditText) layout.findViewById(R.id.alert_catatan_amEdit);
        edit_alert.setText(memori.isCatatan(position, index, MainCatatan.this));

        Button bt = (Button) layout.findViewById(R.id.alert_catatan_amButton);
        bt.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String edit = edit_alert.getText().toString();
                if (index == 0) {
                    memori.position = position;
                    memori.setCatatan("edit", edit, memori.isCatatan(position, 1, MainCatatan.this), MainCatatan.this);
                }
                if (index == 1) {
                    memori.position = position;
                    memori.setCatatan("edit", memori.isCatatan(position, 0, MainCatatan.this), edit, MainCatatan.this);
                }
                if (index == 2) {
                    Toast.makeText(MainCatatan.this, "Auto edit", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(MainCatatan.this, "Saved", Toast.LENGTH_SHORT).show();
                MainCatatan.this.finish();
            }
        });
        builder1.setView(layout);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void alertOpen() {
        final CharSequence[] dialogitem = {"Google.com", "bing.com"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainCatatan.this);
        builder.setTitle("Pilihan");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String data = memori.titles.get(position);

                if (item == 0) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/m?hl=in&q="+data+"&source=android-browser-type")));

                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?safe=strict&client=firefox-b-ab&ei=BRpWXKm1F9DprQGN6p3oCA&q=zzz&oq=zzz&gs_l=psy-ab.3..0l2j0i131l2j0l6.7384.8817..9380...0.0..0.213.723.0j2j2......0....1..gws-wiz.....0..0i131i67.65XSM2uBm9c")));
                }
                else if (item == 1) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://http-www-bing-com.0.freebasics.com/search?iorg_service_id_internal=803478443041409%3BAfrEX0ng8fF-69Ni&iorgbsid=AZwOf5p9ZGHdo4ma-_4xLROJiPP57wR4JxMMMfZYMk2RHTXt0k_suZhZX4ELlv0Xo8d0A99ibKz2Zk2OYsINpLd4&q="+data+"&go=Search&qs=ds&form=QBRE&pc=FBIO")));
                }
            }
        });
        builder.create().show();
    }
}
