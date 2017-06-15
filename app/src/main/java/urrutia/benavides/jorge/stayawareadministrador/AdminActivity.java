package urrutia.benavides.jorge.stayawareadministrador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class AdminActivity extends AppCompatActivity {

    Button btnregistraradulto, btnregistrarred, btnveradultos, btnasociar, btnbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnregistraradulto= (Button) (findViewById(R.id.btnregistraradultoAdmin));
        btnregistrarred = (Button) (findViewById(R.id.btnregistrarredAdmin));
        btnveradultos = (Button) (findViewById(R.id.btnveradultosAdmin));
        btnasociar = (Button) (findViewById(R.id.btnasociarAdmin));
        btnbuscar = (Button) (findViewById(R.id.btnbuscarAdmin));

        btnregistraradulto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AdminActivity.this, RegistrarAdultoActivity.class);
                //intentalo.putExtra("idred", idred);
                startActivity(intent1);
            }
        });
        btnregistrarred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminActivity.this, RegistrarRedActivity.class);
                //intentalo.putExtra("idred", idred);
                startActivity(intent2);
            }
        });
        btnveradultos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AdminActivity.this, VerAdultosActivity.class);
                //intentalo.putExtra("idred", idred);
                startActivity(intent3);
            }
        });
        btnasociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(AdminActivity.this, AsociarActivity.class);
                //intentalo.putExtra("idred", idred);
                startActivity(intent4);
            }
        });
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(AdminActivity.this, VerAdultosActivity.class);
                //intentalo.putExtra("idred", idred);
                startActivity(intent5);
            }
        });
    }
}
