package cl.crevent.crevent;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegistroActivity extends ActionBarActivity {


    EditText etUsuario, etPassword, etPassword2, etCorreo, etTelefono, etComuna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsuario = (EditText)findViewById(R.id.usuario);
        etPassword = (EditText)findViewById(R.id.password);
        etPassword2 = (EditText)findViewById(R.id.password2);
        etCorreo = (EditText)findViewById(R.id.correo);
        etTelefono = (EditText)findViewById(R.id.telefono);
        etComuna = (EditText)findViewById(R.id.comuna);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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
        }


        return super.onOptionsItemSelected(item);
    }

    public void Registrar(View view){//metodo registro de usuario

        String usuario = etUsuario.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String comuna = etComuna.getText().toString().trim();

        if(usuario.equals("") || password.equals("") || password2.equals("") || correo.equals("") || telefono.equals("") || comuna.equals("")){
            Toast.makeText(this, "Ingrese sus datos de usuario", Toast.LENGTH_LONG).show();
        }else{
            if(password.equals(password2)){
                try{
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    httpHandler sh = new httpHandler(getApplicationContext());
                    String registro =sh.get("http://192.168.2.2/CreApp/Registro.php?user="+usuario+"&password="+password+"&correo="+correo+"&telefono="+telefono+"&comuna="+comuna);

                    switch (registro) {
                        case "Usuario registrado":
                            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(this, MainActivity.class);
                            startActivity(i);
                            break;
                        case "Usuario no registrado":
                            Toast.makeText(this, "Usuario no registrado, por favor inttelo nuevamente", Toast.LENGTH_LONG).show();
                            break;
                        case "Usuario ya existe":
                            Toast.makeText(this, "El usuario que est? intentando de registrar ya existe.", Toast.LENGTH_LONG).show();
                            break;
                        case "Telefono ya existe":
                            Toast.makeText(this, "El teléfono que est? intentando de registrar ya existe.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
            }
        }
    }

}
