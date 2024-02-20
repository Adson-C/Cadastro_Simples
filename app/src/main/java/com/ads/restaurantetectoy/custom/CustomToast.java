package com.ads.restaurantetectoy.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Classe de apoio contendo métodos que podem
 * ser reutilizados em todo o projeto
 *
 * Criada por Adson Sá - 11/02/2024
 *
 * Versão v1
 */

import com.ads.restaurantetectoy.R;

public class CustomToast {


    public static void showToastErro(Context contexte, String message, int duration) {
        LayoutInflater inflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_customizado_error, null);

        // Customize the layout elements
        ImageView imageViewIcon = layout.findViewById(R.id.erroimage);
        TextView textViewMessage = layout.findViewById(R.id.text_messa_erro);

        // Set your custom content
        textViewMessage.setText(message);

        Toast toast = new Toast(contexte);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

    public static void showToastSucesso(Context contexte, String message, int duration) {
        LayoutInflater inflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_customizado_sucesso, null);

        // Customize the layout elements
        ImageView imageViewIcon = layout.findViewById(R.id.imgCha);
        TextView textViewMessage = layout.findViewById(R.id.textmessage_sucesso);

        // Set your custom content
        textViewMessage.setText(message);

        Toast toast = new Toast(contexte);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

}
