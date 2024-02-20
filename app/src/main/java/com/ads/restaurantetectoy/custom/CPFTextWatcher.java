package com.ads.restaurantetectoy.custom;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CPFTextWatcher implements TextWatcher {

    private EditText editText;
    private String current = "";

    public CPFTextWatcher(EditText editText) {
        this.editText = editText;
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
        // Não é necessário implementar neste caso
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        // Não é necessário implementar neste caso
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String cleanString = editable.toString().replaceAll("[^\\d]", "");

        if (!cleanString.equals(current)) {
            current = cleanString;

            String formattedCPF = formatCPF(current);
            editText.removeTextChangedListener(this);
            editText.setText(formattedCPF);
            editText.setSelection(formattedCPF.length());
            editText.addTextChangedListener(this);
        }
    }

    public String formatCPF(String cpf) {
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < cpf.length(); i++) {
            if (i == 3 || i == 6) {
                formatted.append('.');
            } else if (i == 9) {
                formatted.append('-');
            }
            formatted.append(cpf.charAt(i));
        }

        return formatted.toString();
    }
}
