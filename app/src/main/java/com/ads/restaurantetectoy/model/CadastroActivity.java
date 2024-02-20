package com.ads.restaurantetectoy.model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ads.restaurantetectoy.R;
import com.ads.restaurantetectoy.activity.MainActivity;
import com.ads.restaurantetectoy.custom.AppUtil;
import com.ads.restaurantetectoy.custom.CpfJava;
import com.ads.restaurantetectoy.custom.CpfUtil;
import com.ads.restaurantetectoy.custom.CustomToast;
import com.ads.restaurantetectoy.helper.MyDatabaseHelper;
import com.ads.restaurantetectoy.modulos.PrinterTester;
import com.ads.restaurantetectoy.modulos.SysTester;
import com.pax.dal.IDAL;
import com.pax.gl.page.IPage;
import com.pax.gl.page.PaxGLPage;
import com.pax.neptunelite.api.NeptuneLiteUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CadastroActivity extends AppCompatActivity {

    EditText edtNome, edtCpf;

//    private MaskEditText edtCpf;
    Button btnCriar, btnVotar;

    private static IDAL dal;
    AutoCompleteTextView autoTex;

    MediaPlayer mediaPlayer;
    Dialog dialog;
    private TextView textoComprovante;

    private SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");

    private Date data = new Date();
    private String dataAtual = formatData.format(data);


    private PaxGLPage iPaxGLPage;
    private static Context appContext;

    MyDatabaseHelper mybd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        appContext = getApplicationContext();
        dal = getDal();
        iPaxGLPage = PaxGLPage.getInstance(this);

        iniciaComponente();
        voltariniciar();

        SysTester.getInstance().enableNavigationBar(false);

    }

    public void iniciaComponente() {
        edtNome = findViewById(R.id.edtNome);

        // metodo para quantidade de caracteres e somente texto
        int maxLength = 35;
        edtNome.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        edtNome.setFilters(new InputFilter[]{new TextOnlyFilter()});

        edtCpf = findViewById(R.id.edtCpf);
//        edtCpf.addTextChangedListener(Mask.mask("###.###.###-##", edtCpf));
//        new CPFTextWatcher(edtCpf);
        autoTex = findViewById(R.id.autoComp);
        btnVotar = findViewById(R.id.btnVotar);
        mybd = new MyDatabaseHelper(this);

        String[] item = getResources().getStringArray(R.array.itens);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, item);
        autoTex.setAdapter(arrayAdapter);

        btnCriar = findViewById(R.id.btnCriar_conta);
        mediaPlayer = MediaPlayer.create(this, R.raw.success);
        dialog = new Dialog(CadastroActivity.this);
        dialog.setContentView(R.layout.dialogo_personalizado);

        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bakground));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button sim = dialog.findViewById(R.id.btn_okay);
        Button nao = dialog.findViewById(R.id.btn_cancel);

        sim.setOnClickListener(view -> {

            mybd.addUsuario(
                    edtNome.getText().toString().trim(),
                    edtCpf.getText().toString().trim(),
                    autoTex.getText().toString().trim());



            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            mediaPlayer.start();

//            imprimirComprovante();
//            PrinterTester.getInstance().cutPaper(0);
            toastSucesso();
            dialog.dismiss(

            );

            finish();

        });
        nao.setOnClickListener(view -> {
            CustomToast.showToastErro(this, "INSCRIÇÃO CANCELADA!", Toast.LENGTH_LONG);
            dialog.dismiss();
            finish();
        });
        btnCriar.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                validarDados();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void validarDados() {

       /* String nome = edtNome.getText().toString();
        String cpf = edtCpf.getText().toString();
        String autCom = autoTex.getText().toString();*/

        String nome = edtNome.getText().toString().trim();
        String cpf = edtCpf.getText().toString().trim();
        String autCom = autoTex.getText().toString().trim();
        Boolean criar = !mybd.checkUserCpf(edtCpf.getText().toString());

        if (!nome.isEmpty()) {
            if (!cpf.isEmpty()) {
                if (!autCom.isEmpty()) {
                    if (AppUtil.isCPF(cpf)) {
                          if  (criar) {
                              dialog.show();
                          }else {
                              CustomToast.showToastErro(this, "CPF JÁ CADASTRADO!", Toast.LENGTH_LONG);
                              toastError();
                              finish();
                          }
                    } else {
                        edtCpf.requestFocus();
                        edtCpf.setError("Cpf invalído...");
                        edtCpf.setText("");


                        Toast.makeText(appContext, "Cpf inválido......", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    autoTex.requestFocus();
                    autoTex.setError("Escolha um setor.");
                }

            } else {
                edtCpf.requestFocus();
                edtCpf.setError("Infome seu CPF.");
            }

        } else {
            edtNome.requestFocus();
            edtNome.setError("Infome seu nome.");
        }


        /*if (!nome.isEmpty()) {
            if (!cpf.isEmpty()) {
                if (!autCom.isEmpty()) {
                    if (!AppUtil.isCPF(cpf)) {
                        if (nome.equals(nome)) {
                            Boolean check = mybd.checkUserCpf(cpf);
                            if (check == false) {
                                dialog.show();
                            } else {
                                CustomToast.showToastErro(this, "CPF JÁ CADASTRADO!", Toast.LENGTH_LONG);
                                toastError();
                                finish();
                            }
                        } else {
                            edtNome.requestFocus();
                            edtNome.setError("Cpf invalido");
                        }

                    } else {
                        autoTex.requestFocus();
                        autoTex.setError("Escolha um setor.");
                    }

                } else {
                    edtCpf.requestFocus();
                    edtCpf.setError("Informe seu cpf.");
                }

            } else {
                edtNome.requestFocus();
                edtNome.setError("Informe seu Nome.");
            }
        }
*/

      /*  if (nome.isEmpty() || cpf.isEmpty() || autCom.isEmpty() || !AppUtil.isCPF(cpf))  {
            edtNome.requestFocus();
            edtNome.setError("Infome o nome.");
            edtCpf.requestFocus();
            edtCpf.setError("Informe seu cpf.");
            autoTex.requestFocus();
            autoTex.setError("Escolha um setor.");

        } else {
            // validar se cpf já existe
            if (nome.equals(nome)) {
                Boolean check = mybd.checkUserCpf(cpf);
                if (check == false) {
                    dialog.show();
                } else {
                    CustomToast.showToastErro(this, "CPF JÁ CADASTRADO!", Toast.LENGTH_LONG);
                    toastError();
                    finish();
                }
            }
        }*/
    }

    private void voltariniciar(){

        btnVotar.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

    }


    public static IDAL getDal() {
        if (dal == null) {
            try {
                long start = System.currentTimeMillis();
                dal = NeptuneLiteUser.getInstance().getDal(appContext);
                Log.i("Test", "get dal cost:" + (System.currentTimeMillis() - start) + " ms");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(appContext, "error occurred,DAL is null.", Toast.LENGTH_LONG).show();
            }
        }
        return dal;
    }

    private void toastSucesso() {

        ViewGroup viewGroup = findViewById(R.id.containa_toast_sucesso);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_customizado_sucesso, viewGroup);

        TextView textView = layout.findViewById(R.id.textmessage_sucesso);
        ImageView imageView = layout.findViewById(R.id.imgCha);

        textView.setText("BOM APETITE !");
        imageView.setImageResource(R.drawable.ok);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 800, 800);
        toast.setView(layout);
        toast.show();
    }

    private void toastError() {

        ViewGroup viewGroup = findViewById(R.id.containa_toast_erro);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_customizado_error, viewGroup);

        TextView textView = layout.findViewById(R.id.text_messa_erro);
        ImageView imageView = layout.findViewById(R.id.erroimage);

        textView.setText("Esse CPF já foi cadastrado !");
        imageView.setImageResource(R.drawable.ic_erro);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.RIGHT, 800, 800);
        toast.setView(layout);
        toast.show();
    }

    // metodo para somente Texto no EditNome
    private class TextOnlyFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // Loop através dos caracteres de origem para garantir que são apenas caracteres de texto
            for (int i = start; i < end; i++) {
                if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                    return ""; // Remove o caractere não permitido
                }
            }
            return null; // Aceita o caractere
        }
    }

    private void imprimirComprovante(){
        PrinterTester.getInstance().init();
        final int random = new Random().nextInt(60000) + 1000;

        IPage page = iPaxGLPage.createPage();
        BitmapFactory.Options opitions = new BitmapFactory.Options();

        IPage.ILine.IUnit unit = page.createUnit();

        unit.setAlign(IPage.EAlign.CENTER);

        PrinterTester.getInstance().setGray(4);
        Bitmap bitmapImg = BitmapFactory.decodeResource(getResources(), R.drawable.restajohn, opitions);
        PrinterTester.getInstance().printBitmap(bitmapImg);

        PrinterTester.getInstance().printStr("   *  COMPROVANTE DE INSCRIÇÃO  *\n", null);
        PrinterTester.getInstance().printStr("  --- Data   - " + dataAtual + "\n", null);
        PrinterTester.getInstance().printStr("  --- Obrigado pela inscrição ! --  \n", null);
        PrinterTester.getInstance().printStr("  --- Número do comprovante - " + random + "\n\n\n", null);
        PrinterTester.getInstance().printStr("  --- Deus seja louvado ! --  \n", null);


        PrinterTester.getInstance().start();
        PrinterTester.getInstance().finish();
        Log.i("getDotLine", "" + PrinterTester.getInstance().getDotLine());
    }

}