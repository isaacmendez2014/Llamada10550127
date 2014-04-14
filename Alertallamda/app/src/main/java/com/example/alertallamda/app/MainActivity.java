package com.example.alertallamda.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends Activity
{
    EditText editNombre,editTelefono;
    private Button botonGuardar;
    private Button botonLlamar;


    private SQLiteDatabase baseDatos;
    private static final String TAG = "bdagenda";
    private static final String nombreBD = "agenda";
    private static final String tablaContacto = "contacto";

    private static final String crearTablaContacto = "create table if not exists "
            + " contacto (codigo integer primary key autoincrement, "
            + " nombre text not null, telefono text not null unique);";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNombre   = (EditText) findViewById(R.id.txtNombre);
        editTelefono = (EditText) findViewById(R.id.txtTelefono);
        botonGuardar = (Button) findViewById(R.id.btGuardar);
        botonLlamar = (Button) findViewById(R.id.btLlamar);

        botonGuardar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                abrirBasedatos();

                boolean resultado = insertarFila(editNombre.getText().toString(),
                        editTelefono.getText().toString());
                if(resultado)
                    Toast.makeText(getApplicationContext(),
                            "Contacto añadido correctamente", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),
                            "No se ha podido guardar el contacto" ,   Toast.LENGTH_LONG).show();
            }
        });

        botonLlamar.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage("¿Desea realizar la llamada al contacto?");
                alertDialog.setTitle("Llamar a contacto...");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        try
                        {
                            EditText num=(EditText)findViewById(R.id.txtTelefono);
                            String number = "tel:" + num.getText().toString().trim();
                            Toast.makeText(getApplicationContext(),
                                    "Llamando al " + num.getText().toString().trim(), Toast.LENGTH_LONG).show();
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                            startActivity(callIntent);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),
                                    "No se ha podido realizar la llamada", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Llamada cancelada", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.show();
            }
        });


    }


    private void abrirBasedatos()
    {
        try
        {
            baseDatos = openOrCreateDatabase(nombreBD, MODE_WORLD_WRITEABLE, null);
            baseDatos.execSQL(crearTablaContacto);
        }
        catch (Exception e)
        {
            Log.i(TAG, "Error al abrir o crear la base de datos" + e);
        }
    }

    private boolean insertarFila(String nombre, String telefono)
    {
        ContentValues values = new ContentValues();
        values.put("nombre",nombre );
        values.put("telefono", telefono);
        Toast.makeText(getApplicationContext(), "Nombre: " + nombre + ", " +
                "teléfono: " + telefono, Toast.LENGTH_LONG).show();
        return (baseDatos.insert(tablaContacto, null, values) > 0);
    }
}