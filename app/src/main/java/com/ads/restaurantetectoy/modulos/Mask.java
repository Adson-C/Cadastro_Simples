package com.ads.restaurantetectoy.modulos;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mask {

    private static String replaceChars(String cpfFull) {
        return cpfFull.replace(".", "").replace("-", "")
                .replace("(", "").replace(")", "")
                .replace("/", "").replace(" ", "")
                .replace("*", "");
    }

    public static TextWatcher mask(String mask, EditText edtCpf) {
        TextWatcher textWatcher = new TextWatcher() {
            boolean isUpdating = false;
            String oldString = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = replaceChars(s.toString());
                String cpfWithMask = "";

                if (count == 0) //is deleting
                    isUpdating = true;

                if (isUpdating) {
                    oldString = str;
                    isUpdating = false;
                    return;
                }

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > oldString.length()) {
                        cpfWithMask += m;
                        continue;
                    }
                    try {
                        cpfWithMask += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }

                isUpdating = true;
                edtCpf.setText(cpfWithMask);
                edtCpf.setSelection(cpfWithMask.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        return textWatcher;
    }
}

