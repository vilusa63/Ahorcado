package edu.fje.ahorcado_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_GET_ACCOUNTS = 100;
    public TextView palabra,intentosView,tiempo,puntuacionView;
    public List<String> palabras= new ArrayList<>();
    public List<String> contactes=new ArrayList(),contactesNum=new ArrayList();;
    public CountDownTimer timer;
    public int tiempoAhora=30;
    public int pistasUsadas=0;
    public String elegida;
    public ArrayList charac=new ArrayList();
    public int intentos=10;
    public Button retry,playAgain,iniciar;
    public ImageView imagen,gif;
    public List<String> username = new LinkedList<>();
    public int[] imagenes ={R.drawable.ahorcado0,R.drawable.ahorcado1,R.drawable.ahorcado2,R.drawable.ahorcado3,R.drawable.ahorcado4,
            R.drawable.ahorcado5,R.drawable.ahorcado6,R.drawable.ahorcado7,R.drawable.ahorcado8,
            R.drawable.ahorcado9,R.drawable.ahorcado10,};
    public ConstraintLayout v;
    WebView navegador;
    private GridView gridView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.GET_ACCOUNTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.GET_ACCOUNTS},
                        PERMISSIONS_REQUEST_GET_ACCOUNTS);
            }
        }



        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        if(accounts.length==0){
            username.add("anonymous");
        }
        else{
            for (Account account : accounts) {
            username.add(account.name.split("@")[0]);
            }
        }

        setContentView(R.layout.portada);
        v=(ConstraintLayout) findViewById(R.id.constraintPortada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setTitle("Portada");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch(item.getItemId())
        {
            case R.id.infobut:
                Log.i(getClass().getName(),"hhhhhhhhhhh");
                mostrarInformacion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if(parent.getId()==R.id.spinner) {
            if (pos == 0) {
                v.setBackgroundColor(Color.WHITE);
            }
            if (pos == 1) {
                v.setBackgroundColor(Color.BLUE);
            }
            if (pos == 2) {
                v.setBackgroundColor(Color.GREEN);
            }
            if (pos == 3) {
                v.setBackgroundColor(Color.RED);
            }
        }else{
            pistasUsadas++;
            if(pos==0) pistasUsadas--;
            if (pos == 1) {
                int i=0;
                while(true) {

                    if(charac.get(i)=="_ ") break;
                    i++;
                }
                charac.set(i, elegida.charAt(i));

            }
            if (pos == 2) {
                int randomNumber=0;
                while(true) {
                    Random r = new Random();
                    randomNumber = r.nextInt(elegida.length());
                    if(charac.get(randomNumber)=="_ ") break;
                }
                charac.set(randomNumber, elegida.charAt(randomNumber));
            }
            if (pos == 3) {
                int i=elegida.length()-1;
                while(true) {

                    if(charac.get(i)=="_ ") break;
                    i--;
                }
                charac.set(i, elegida.charAt(i));
            }
            Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
            spinner2.setSelection(0);
            writeWord();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }



    public void start(View button) {
        palabras= Arrays.asList(getResources().getStringArray(R.array.paraules));
        setContentView(R.layout.activity_main);
        v=(ConstraintLayout) findViewById(R.id.constraintMain);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_pistas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setTitle("Ahorcado");

        tiempo=(TextView) findViewById(R.id.tiempo);
        timer=new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tiempo.setText("" + millisUntilFinished / 1000);
                tiempoAhora--;
            }

            public void onFinish() {
                palabra.setText("Has perdido, la palabra era "+elegida.toLowerCase());
                calcularPuntuacion(0);
                disableButtons();
            }
        };
        timer.start();

        iniciar=(Button) findViewById(R.id.iniciar);
        palabra= (TextView) findViewById(R.id.palabra);
        intentosView= (TextView) findViewById(R.id.intentosView);
        imagen= (ImageView) findViewById(R.id.imagen);
        gif=(ImageView) findViewById(R.id.gif);
        retry=(Button)  findViewById(R.id.retry);
        playAgain=(Button)  findViewById(R.id.playagain);
        puntuacionView=(TextView) findViewById(R.id.puntuacionView);
        writeIntentos();
        obtenirContactes();
    }
    public void actualitzaResultats(int puntuacio){
        AhorcadoUtilidad utilitatDB = new AhorcadoUtilidad(getBaseContext());
        SQLiteDatabase db = utilitatDB.getWritableDatabase();

        // creem un mapa de valors on les columnes són les claus
        ContentValues valors = new ContentValues();
        valors.put(AhorcadoContract.TablaAhorcado.COLUMNA_NOMBRE, username.get(0));
        valors.put(AhorcadoContract.TablaAhorcado.COLUMNA_PUNTUACION, puntuacio);
        valors.put(AhorcadoContract.TablaAhorcado.COLUMNA_FECHA, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        db.insert(AhorcadoContract.TablaAhorcado.NOM_TAULA,
                AhorcadoContract.TablaAhorcado.COLUMNA_NULL, valors);

    }
    public void readDatabase(){
        AhorcadoUtilidad utilitatDB = new AhorcadoUtilidad(getBaseContext());
        SQLiteDatabase db = utilitatDB.getReadableDatabase();

        String[] projeccio = {
                AhorcadoContract.TablaAhorcado.COLUMNA_NOMBRE,
                AhorcadoContract.TablaAhorcado.COLUMNA_PUNTUACION,
                AhorcadoContract.TablaAhorcado.COLUMNA_FECHA
        };


        String ordre =
                AhorcadoContract.TablaAhorcado.COLUMNA_PUNTUACION + " DESC";


        Cursor c = db.query(
                AhorcadoContract.TablaAhorcado.NOM_TAULA,  // taula
                projeccio,                               // columnes
                null,                                // columnes WHERE
                null,                            // valors WHERE
                null,                                     // GROUP
                null,                                     // HAVING
                ordre                                 // ordre
        );

        c.moveToFirst();

    }
    private void obtenirContactes() {
        Cursor managedCursor = getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[] {Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER}, null, null,  Phone.DISPLAY_NAME + " ASC");
        try {
            while (managedCursor.moveToNext()) {
                int index;

                index = managedCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
                contactes.add(managedCursor.getString(index));

                index = managedCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
                contactesNum.add(managedCursor.getString(index));
            }
        } finally {
            managedCursor.close();
        }
        chooseWord();

    }

    public void writeIntentos(){
        intentosView.setText(String.valueOf(intentos));
    }
    public void chooseWord(){
        Random r=new Random();
        if(contactes.size()!=0){
            palabras=contactes;
        }
        int randomNumber=r.nextInt(palabras.size());
        elegida=palabras.get(randomNumber).toUpperCase();

        for (int i=0; i<elegida.length(); i++)
        {
            if(elegida.charAt(i)==' '){
                charac.add("  ");
            }
            else charac.add("_ ");
        }
        writeWord();
    }
    public void writeWord(){
        palabra.setText("");
        for(int i=0; i<charac.size(); i++){
            palabra.setText(palabra.getText()+charac.get(i).toString());
        }
        if(!charac.contains("_ ")){
            gif.setVisibility(View.VISIBLE);
            calcularPuntuacion(1);
            disableButtons();
            playAgain.setVisibility(View.VISIBLE);
        }
    }
    public void setImage(){
        int index=10-intentos;
        imagen.setImageResource(imagenes[index]);
    }
    public void onKeyboardClick(View button) {
        Button b=(Button) button;
        if(elegida.toUpperCase().contains(b.getText()) && !charac.contains(b.getText())){
            int index=elegida.indexOf(b.getText().charAt(0));
            while (index >= 0) {
                charac.set(index,b.getText());
                index = elegida.indexOf(b.getText().charAt(0), index + 1);
            }
            writeWord();

        }else intentos--;writeIntentos();setImage();
        b.setEnabled(false);                                            //inhabilitar la letra cuando le das
        if(intentos==0){
            palabra.setText("Has perdido, la palabra era "+elegida.toLowerCase());
            calcularPuntuacion(0);
            disableButtons();
        }


    }
    public void calcularPuntuacion(int v){
        int puntuacion=100;
        puntuacion=puntuacion-(10-intentos)*5;
        puntuacion=puntuacion-(30-tiempoAhora)*3;
        puntuacion=puntuacion-pistasUsadas*5;
        puntuacion=puntuacion*v;
        if (puntuacion<0) puntuacion=0;
        puntuacionView.setText("Puntuación: "+puntuacion+ "\n"+username.get(0));
        actualitzaResultats(puntuacion);
    }
    public void disableButtons() {
        timer.cancel();
        for (char i='A';i<'A'+27;i++) {
            int id;
            if(i=='A'+26){
                id = getResources().getIdentifier("Ñ", "id", getPackageName());
            }
            else id = getResources().getIdentifier(String.valueOf(i), "id", getPackageName());
            Button button = (Button) findViewById(id);
            button.setEnabled(false);
        }
        retry.setVisibility(View.VISIBLE);
    }
    public void enableButtons() {
        for (char i='A';i<'A'+27;i++) {
            int id;
            if(i=='A'+26){
                id = getResources().getIdentifier("Ñ", "id", getPackageName());
            }
            else id = getResources().getIdentifier(String.valueOf(i), "id", getPackageName());
            Button button = (Button) findViewById(id);
            button.setEnabled(true);
        }
        retry.setVisibility(View.INVISIBLE);
    }
    public void restart(View button){
        imagen.setImageResource(0);
        intentos=10;
        charac.clear();
        enableButtons();
        writeIntentos();
        chooseWord();
        gif.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        timer.start();
        tiempoAhora=30;
    }
    public void mostrarInformacion() {
        setContentView(R.layout.info);
        navegador = (WebView) findViewById(R.id.informacion);

        navegador.getSettings().setJavaScriptEnabled(true);
        navegador.getSettings().setAllowContentAccess(true);
        navegador.getSettings().setAllowFileAccess(true);
        //per geolocalització de HTML5
        navegador.getSettings().setAppCacheEnabled(true);
        navegador.getSettings().setDatabaseEnabled(true);
        navegador.getSettings().setDomStorageEnabled(true);
        navegador.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        //per defecte ens obrirà Chrome, cal canviar-ho
        navegador.setWebViewClient(new WebViewClient());
        //navegador.loadUrl("http://www.fje.edu/");http://127.0.0.1:5500/view.html
        //navegador.loadData("<html><body>hola, mon!</body></html>", "text/html", "UTF-8");
        navegador.loadUrl("file:///android_asset/view.html");

    }

    public void goRanking(View view) {
        setContentView(R.layout.ranking);
        RelativeLayout v=(RelativeLayout) findViewById(R.id.rankingRelative);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setTitle("Ranking");
        writeRankingTable();
    }
    public void writeRankingTable(){
        gridView=(GridView) findViewById(R.id.gridView1);
        //ArrayList
        list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);
        String nombre, puntuacion2,fecha;
        int posicion=1;
        nombre="";
        puntuacion2="";
        fecha="";

        AhorcadoUtilidad utilitatDB = new AhorcadoUtilidad(getBaseContext());
        SQLiteDatabase db = utilitatDB.getReadableDatabase();

        try
        {
            //for holding retrieve data from query and store in the form of rows
            Cursor c=utilitatDB.DisplayData(db);
            //Move the cursor to the first row.
            if(c.moveToFirst())
            {
                do
                {

                    nombre=c.getString(c.getColumnIndex("nombre"));
                    puntuacion2=c.getString(c.getColumnIndex("puntuación"));
                    fecha=c.getString(c.getColumnIndex("fecha"));
                    //add in to array list
                    list.add(Integer.toString(posicion));
                    list.add(nombre);
                    list.add(puntuacion2);
                    list.add(fecha);
                    gridView.setAdapter(adapter);
                    posicion++;
                }while(c.moveToNext());//Move the cursor to the next row.
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "No data found"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        db.close();
        //Toast.makeText(getBaseContext(),"Name: "+name+"Roll No: "+roll+"Course: "+course , Toast.LENGTH_LONG).show();
    }




    class WebAppInterface {
        Context mContext;
        /** instancia la interfície i l'associa el context */
        WebAppInterface(Context c) {
            mContext = c;
        }
        /** mostra una torrada */
        @JavascriptInterface
        public void mostrarToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

}