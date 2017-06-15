package urrutia.benavides.jorge.stayawareadministrador;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class AsociarActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String LOGIN_URL = "https://bancademia.net/StayAware/Asociar3.php";

    Button btnok;
    TextView rutred, rutadulto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asociar);
        btnok = (Button) (findViewById(R.id.btnAsociar));
        rutred = (TextView) (findViewById(R.id.rutredAsociar));
        rutadulto = (TextView) (findViewById(R.id.rutadultoMayor));

        btnok.setOnClickListener(this);

    }
    private void registro(){
        String Rutred = rutred.getText().toString().trim();
        String Rutadulto = rutadulto.getText().toString().trim();
        registrar(Rutred,Rutadulto);
    }
    private void registrar(final String Rutred, final String Rutadulto){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AsociarActivity.this,"Espere por favor",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("asociado")){
                    Toast.makeText(AsociarActivity.this,"registrado exitosamente",Toast.LENGTH_LONG).show();
                    finish();

                }else{
                    Toast.makeText(AsociarActivity.this,"error en el registro",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("rutred",params[0]);
                data.put("rutadulto",params[1]);
                HttpsQuerys ruc = new HttpsQuerys();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(Rutred,Rutadulto);
    }
    @Override
    public void onClick(View v) {
        if(v == btnok){
            registro();
        }
    }
}
