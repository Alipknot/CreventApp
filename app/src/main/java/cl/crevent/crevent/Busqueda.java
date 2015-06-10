package cl.crevent.crevent;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Busqueda extends AppCompatActivity {
    private ProgressDialog pDialog;
    private DrawerLayout mDrawerLayout; //declaracion de Layout para contener el menu
    private ActionBarDrawerToggle drawerToggle; //accion q permite esconder el menu
    private Toolbar toolbar;//declaracion de toolbar barra superior
    private static final String TAG_result = "Resultados";
    private static final String TAG_NAME = "empresa_nombre";
    private static final String TAG_IMG = "empresa_img";
    private static final String TAG_LOC= "empresa_loc";
    private static final String TAG_PHONE = "empresa_cel";
    private static final String TAG_DIR = "empresa_dir";
    private static final String TAG_CLI = "cliente_name";

    ListView listView;

    // contacts JSONArray
    JSONArray contacts = null;

    // Hashmap for ListView
    // ArrayList<HashMap<String, String>> contactList;

    List<String> imga = new ArrayList<String>();
    List<String> namae = new ArrayList<String>();
    List<String> dire = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.ls_result);
        new GetResults().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.home){// permite volver a la actividad padre
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetResults extends AsyncTask<Void, Void, Void> {

        ListViewCustomAdapter Adp;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Busqueda.this);
            pDialog.setMessage("Por favor espere");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            httpHandler sh = new httpHandler(getApplicationContext());

            // Making a request to url and getting response
            String url="http://192.168.2.2/CreApp/CargarPerfil.php?comuna=";
            String query;
            try {
                query = URLEncoder.encode("Concepcion", "UTF-8");
            }catch (UnsupportedEncodingException e) {

                query = "*";
            };
            url= url+query;
            Log.d("Url: >", url);
            String jsonStr = sh.get(url);

            Log.d("Response: ", "> " + jsonStr);
            switch (jsonStr){
                case "ERROR":
                    runOnUiThread(new Runnable(){

                        public void run() {
                            Toast.makeText(getApplicationContext(), "No se pudo cargar la pagina, intente denuevo",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
                    break;
                case "null":
                    runOnUiThread(new Runnable(){

                        public void run() {
                            Toast.makeText(getApplicationContext(), "Por favor compruebe su conexi?n a internet",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
                    break;
                default:
                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);

                            // Getting JSON Array node
                            contacts = jsonObj.getJSONArray(TAG_result);


                            // looping through All Contacts
                            for (int i = 0; i < contacts.length(); i++) {

                                JSONObject c = contacts.getJSONObject(i);
                                String name = c.getString(TAG_NAME);
                                String img = c.getString(TAG_IMG);
                                String address = c.getString(TAG_DIR);
                                String loc = c.getString(TAG_LOC);
                                String phone = c.getString(TAG_PHONE);
                                String cli = c.getString(TAG_CLI);
                                Log.d("datos", img+name+address);
                                imga.add(img);
                                namae.add(name);
                                dire.add(address);
/**
 // tmp hashmap for single contact
 HashMap<String, String> contact = new HashMap<String, String>();

 // adding each child node to HashMap key => value

 contact.put(TAG_NAME, name);
 contact.put(TAG_IMG,img);
 contact.put(TAG_DIR, address);

 // adding contact to contact list
 contactList.add(contact);
 */
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("ServiceHandler", "Couldn't get any data from the url");
                    }
                    break;
            }
            Adp = new ListViewCustomAdapter(Busqueda.this, namae.toArray(new String[namae.size()]),dire.toArray(new String[dire.size()]),imga.toArray(new String[imga.size()]) );
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            Log.d("Array img", "arr: " + imga.toString());
            Log.d("Array dire", "arr: " + dire.toString());
            Log.d("Array nombres", "arr: " + namae.toString());


/**
 ListAdapter adapter = new SimpleAdapter(
 MainHub.this, contactList,
 R.layout.list_item, new String[] { TAG_NAME, TAG_DIR,
 TAG_IMG }, new int[] { R.id.name,
 R.id.email, R.id.mobile });

 listView.setAdapter(adapter);

 */

            listView.setAdapter(Adp);
        }

    }

}
