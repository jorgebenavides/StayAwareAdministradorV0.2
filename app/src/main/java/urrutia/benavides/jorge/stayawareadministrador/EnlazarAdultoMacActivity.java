package urrutia.benavides.jorge.stayawareadministrador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EnlazarAdultoMacActivity extends AppCompatActivity{

    String ELIMINAR_INSERCION_URL = "https://www.bancademia.net/StayAware/EliminarRedAdmin.php";
    Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlazar_adulto_mac);
        Button enlazar = (Button) (findViewById(R.id.btnenlazar));
        cancelar = (Button) (findViewById(R.id.btncancelarenlazar));
        final String idAdulto = getIntent().getExtras().getString("idAdulto");


        enlazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EnlazarAdultoMacActivity.this, AsociarBeanActivity.class);
                intent1.putExtra("idAdultoMayor", idAdulto);
                //intentalo.putExtra("idred", idred);
                startActivity(intent1);
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(EnlazarAdultoMacActivity.this, AdminActivity.class);
                //intentalo.putExtra("idred", idred);
                //Eliminar al ADULTO recien agregado
                startActivity(intent2);
                eliminar(idAdulto);

            }
        });
    }

    private void eliminar(final String idAdultoMayor){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EnlazarAdultoMacActivity.this,"Espere por favor",null,true,true);
            }
            //ACA VA QUE EL PHP DEVUELVA ELIMINAADO PARA QUE LALAL
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("eliminado exitosamente")){
                    Toast.makeText(EnlazarAdultoMacActivity.this,"el Adulto ha sido eliminado de la base de datos",Toast.LENGTH_LONG).show();

                    finish();
                }else{
                    Toast.makeText(EnlazarAdultoMacActivity.this,"error la eliminacion",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("idAdulto",params[0]);
                HttpsQuerys ruc = new HttpsQuerys();
                String result = ruc.sendPostRequest(ELIMINAR_INSERCION_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(idAdultoMayor);
    }
}
