package urrutia.benavides.jorge.stayawareadministrador;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VerAdultosActivity extends AppCompatActivity {

    String url="https://bancademia.net/StayAware/VerTodosAdultos.php";
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_adultos);
        userLogin();
    }
    public void userLogin() {
        class UserLoginClass extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            final ListView lv= (ListView) findViewById(R.id.Lv);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VerAdultosActivity.this, "Espere por favor", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                final ArrayList<JSONArray> arreglo = new ArrayList<>();
                final ArrayAdapter<JSONObject> AdultosMayores = new ArrayAdapter(VerAdultosActivity.this, android.R.layout.simple_expandable_list_item_1,arreglo);
                try {
                    JSONArray obj = new JSONArray(s);
                    for(int i=0;i < obj.length();i++){
                        JSONObject Adulto = obj.getJSONObject(i);
                        AdultosMayores.add(Adulto);
                        lv.setAdapter(AdultosMayores);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();

                HttpsQuerys ruc = new HttpsQuerys();

                String result = ruc.sendPostRequest(url, data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute();
    }
}
