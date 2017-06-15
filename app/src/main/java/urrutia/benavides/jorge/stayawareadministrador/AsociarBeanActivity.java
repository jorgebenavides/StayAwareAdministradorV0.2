package urrutia.benavides.jorge.stayawareadministrador;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jbu_a on 19-05-2017.
 */

public class AsociarBeanActivity extends ListActivity implements Serializable {
    String ASOCIAR_MAC_URL = "https://www.bancademia.net/StayAware/guardarMacAdulto.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user asynchronously -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        final ArrayList<Bean> beans = new ArrayList<>();

        final ArrayAdapter<Bean> Dispositivos = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        BeanDiscoveryListener listener = new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi) {
                String addr = bean.getDevice().getAddress();
                for(Bean b: beans){
                    if(Dispositivos.getCount() > 0){


                        Bean ultimoRegistro = Dispositivos.getItem(Dispositivos.getCount()-1);
                        if(ultimoRegistro != b){
                            if(b.getDevice().getAddress().equals(addr)){
                                Dispositivos.add(b);
                            }
                        }
                    }else{
                        Dispositivos.add(b);
                    }
                }
                beans.add(bean);
                setListAdapter(Dispositivos);

            }


            @Override
            public void onDiscoveryComplete() {

            }
        };
        BeanManager.getInstance().setScanTimeout(10);  // Timeout in seconds, optional, default is 30 seconds
        BeanManager.getInstance().startDiscovery(listener);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Bean beanConnect = (Bean) l.getAdapter().getItem(position);
        String idAdulto = getIntent().getExtras().getString("idAdultoMayor");
        String mac = beanConnect.getDevice().getAddress();
        String macc = mac.replace(":", "");
        registrar(macc, idAdulto);
    }

    private void registrar(final String macc, final String idAdultoMayor){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if(obj.isNull("error")){
                        Toast.makeText(getApplicationContext(),"Mac enlazado exitosamente",Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(AsociarBeanActivity.this, AdminActivity.class);
                        startActivity(intent1);

                        /////finish();
                    }else{
                        Toast.makeText(getApplicationContext(),obj.getString("error"),Toast.LENGTH_LONG).show();
                        finish();
                    }
                }catch (JSONException e){
                    e.printStackTrace();

                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("macc",params[0]);
                data.put("idAdulto",params[1]);
                HttpsQuerys ruc = new HttpsQuerys();
                String result = ruc.sendPostRequest(ASOCIAR_MAC_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(macc,idAdultoMayor);
    }
}
