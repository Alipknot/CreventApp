package cl.crevent.crevent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    Button btLogin, btRegistro; //declaracion botones
    EditText etUser,etPassword;//declaracion Caja de texto



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLogin = (Button) findViewById(R.id.BtGps);//instanciar boton
        btRegistro = (Button) findViewById(R.id.btRegistro);//instanciar boton
        etUser = (EditText) findViewById(R.id.etUser);//instanciar caja de texto
        etPassword = (EditText) findViewById(R.id.etPassword);//instanciar caja de texto



        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               new Asyncs().execute();
            }
        });

        consultaSesion();
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
    public void consultaSesion(){//metodo ve si la sesion esta iniciada

        SharedPreferences preferences = getSharedPreferences("Preferencia_usuario", Context.MODE_PRIVATE);

        if (preferences.contains("sesion")){
            startActivity(new Intent(this, MainHub.class));
        }
    }
 private class Asyncs extends AsyncTask<Void,Void,Void> {


     @Override
     protected Void doInBackground(Void... params) {
         String usuario = etUser.getText().toString().trim();
         String password = etPassword.getText().toString().trim();


         if( usuario.equals("")|| password.equals("")){
             runOnUiThread(new Runnable() {

                 public void run() {
                     Toast.makeText(getApplicationContext(), "Ingrese sus datos",
                             Toast.LENGTH_SHORT).show();

                 }
             });
         }else{
             try {
                 httpHandler sh = new httpHandler(getApplicationContext());
                 String login= sh.get("http://192.168.2.2/CreApp/Login.php?user=" + usuario + "&password=" + password);



                 switch (login) {
                     case "Usuario encontrado":
                         runOnUiThread(new Runnable() {

                             public void run() {
                                 Toast.makeText(getApplicationContext(), "Usuario encontrado",
                                         Toast.LENGTH_SHORT).show();

                             }
                         });

                         SharedPreferences preferences = getSharedPreferences("Preferencia_usuario", Context.MODE_PRIVATE);//guardar datos de user en el celular
                         SharedPreferences.Editor editor = preferences.edit();
                         editor.putString("usuario", usuario);

                         final CheckBox chkSesion = (CheckBox) findViewById(R.id.cbNocerrar);//instanciar checkbox

                         if (chkSesion.isChecked()) {
                             editor.putBoolean("sesion", true);
                         }
                         editor.apply();
                         startActivity(new Intent(MainActivity.this, MainHub.class));//ir a perfil luego del login
                         break;

                     case "Usuario no encontrado":
                         runOnUiThread(new Runnable() {

                             public void run() {
                                 Toast.makeText(getApplicationContext(), "Usuario no encontrado",
                                         Toast.LENGTH_SHORT).show();

                             }
                         });
                         break;
                     case "ERROR":
                         runOnUiThread(new Runnable() {

                             public void run() {
                                 Toast.makeText(getApplicationContext(), "No se pudo conectar al servidor",
                                         Toast.LENGTH_SHORT).show();

                             }
                         });
                         break;
                     default:
                         //

                 }
             }catch (Exception e)
             { runOnUiThread(new Runnable() {

                 public void run() {
                     Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

                 }
             });
             }
         }
         return null;
     }
 }

}
