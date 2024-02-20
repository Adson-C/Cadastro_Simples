package com.ads.restaurantetectoy.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ads.restaurantetectoy.custom.CustomToast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Restaurante.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_restaurante";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USUARIO = "usuarios";
    private static final String COLUMN_CPF = "cpf";
    private static final String COLUMN_SETOR = "setor";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO + " TEXT, " +
                COLUMN_CPF + " TEXT UNIQUE, " +
                COLUMN_SETOR + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
     public void addUsuario(String nome,String cpf, String setor) {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put(COLUMN_USUARIO, nome);
         cv.put(COLUMN_CPF, cpf);
         cv.put(COLUMN_SETOR, setor);

         try {
             long result = db.insertOrThrow(TABLE_NAME, null, cv);
             if (result != -1) {
                 CustomToast.showToastSucesso(this.context, "SUCESSO !\n OBRIGADO PELA INSCRIÇÃO !", Toast.LENGTH_LONG);
             } else {
                 CustomToast.showToastErro(this.context, "ERROR NA INSCRIÇÃO, TENTE NOVAMENTE! ", Toast.LENGTH_SHORT);
             }
         } catch (SQLiteConstraintException e) {

             CustomToast.showToastErro(this.context, "CPF JÀ CADASTRADO, VERIFIQUE POR FAVOR! ", Toast.LENGTH_LONG);
         } finally {
             db.close();
         }
     }
    // verificar se já existe Cpf no banco de dados
    public Boolean checkUserCpf(String cpf){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cs = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CPF + " = ? ", new String[]{cpf});
        return cs.getCount()> 0;
    }

//    public boolean cpfExists(String cpf) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = null;
//
//        try {
//            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CPF + " = ?";
//            cursor = db.rawQuery(query, new String[]{cpf});
//
//            // Se o cursor tiver algum resultado, significa que o CPF já existe
//            return cursor.getCount() > 0;
//        } catch (Exception e) {
//            // Lidar com exceções (log, relatar, etc.)
//            e.printStackTrace();
//            return false;  // ou lançar uma exceção, dependendo dos requisitos
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            db.close();
//        }
//    }

//
//    public Boolean checkUserCpf4(String cpf) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cs = null;
//
//        try {
//            cs = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CPF + " = ? ", new String[]{cpf});
//            return cs.getCount() > 0;
//        } catch (SQLiteConstraintException e) {
//            // Tratamento de erro específico para CPF já cadastrado
//            CustomToast.showToastErro(this.context, "CPF JÁ CADASTRADO, VERIFIQUE POR FAVOR! ", Toast.LENGTH_LONG);
//            return true; // Indica que o CPF já existe
//        } finally {
//            // Certifique-se de fechar o cursor e o banco de dados no bloco 'finally'
//            if (cs != null) {
//                cs.close();
//            }
//            db.close();
//        }
//    }

//    public Boolean checkUserCpf3(String cpf) throws CPFAlreadyExistsException {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cs = null;
//
//        try {
//            cs = db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_CPF + " = ? ", new String[]{cpf});
//            if (cs.getCount() > 0) {
//                throw new CPFAlreadyExistsException("CPF JÁ CADASTRADO, VERIFIQUE POR FAVOR!");
//            }
//            return false; // CPF não existe
//        } finally {
//            // Certifique-se de fechar o cursor e o banco de dados no bloco 'finally'
//            if (cs != null) {
//                cs.close();
//            }
//            db.close();
//        }
//    }
//
//    // Defina uma exceção personalizada para CPF já existente
//    public class CPFAlreadyExistsException extends Exception {
//        public CPFAlreadyExistsException(String message) {
//            super(message);
//        }
//    }


}

