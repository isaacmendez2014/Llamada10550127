package com.example.geolocalizacion.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button geo;
        geo = (Button) findViewById(R.id.GeoButton);
        geo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String cordenada="28.6597766,-106.1140587,18z";
                String nombre="tel";
                public void localizar(View v){
                    String uri="geo:"+cordenada + "?q + cordenada + "("+nombre+")";
                    Intent intent = new Intent(Intent.Action.view);
                    intent.setData(uri.parse(uri));
                    if(intent.resolve.ACtivity(getPackageManager())!=null){
                        starActivity(intent);
                    }
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
