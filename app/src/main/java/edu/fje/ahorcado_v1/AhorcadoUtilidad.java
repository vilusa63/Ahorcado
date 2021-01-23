package edu.fje.ahorcado_v1;

import android.os.Bundle;
import android.provider.BaseColumns;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
final class AhorcadoContract {

    public AhorcadoContract() {
    }

    /**
     * Classe Interna que declara una taula Alumne
     *
     * @author sergi grau
     * @version 1.0 26/11/2013
     *
     */
    public static abstract class TablaAhorcado implements BaseColumns {
        public static final String NOM_TAULA = "ranking";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_PUNTUACION = "puntuación";
        public static final String COLUMNA_FECHA= "fecha";
        public static final String COLUMNA_NULL = "null";
    }
}
public class AhorcadoUtilidad extends SQLiteOpenHelper {
    private static final String TIPUS_TEXT = " TEXT";
    private static final String TIPUS_ENTER = " INT";
    private static final String SEPARADOR_COMA = ",";
    private static final String SQL_CREACIO_TAULA = "CREATE TABLE "
            + AhorcadoContract.TablaAhorcado.NOM_TAULA + " ("
            + AhorcadoContract.TablaAhorcado._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + AhorcadoContract.TablaAhorcado.COLUMNA_NOMBRE + TIPUS_TEXT
            + SEPARADOR_COMA + AhorcadoContract.TablaAhorcado.COLUMNA_PUNTUACION
            + TIPUS_ENTER +  SEPARADOR_COMA +AhorcadoContract.TablaAhorcado.COLUMNA_FECHA
            + TIPUS_TEXT+" )";

    private static final String SQL_ESBORRAT_TAULA = "DROP TABLE IF EXISTS "
            + AhorcadoContract.TablaAhorcado.NOM_TAULA;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Ahorcado.db";
    public AhorcadoUtilidad(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public Cursor DisplayData(SQLiteDatabase db)
    {
        //Select query
        return db.rawQuery("SELECT * FROM ranking order by puntuación desc", null);
        //return db.query(TABLE_NAME, new String[]{NAME, ROLL,COURSE}, null, null, null, null, null);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREACIO_TAULA);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_ESBORRAT_TAULA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
