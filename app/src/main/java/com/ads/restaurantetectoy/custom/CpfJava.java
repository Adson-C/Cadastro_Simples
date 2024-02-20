package com.ads.restaurantetectoy.custom;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

public class CpfJava {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean myValidateCPF(String cpf) {
        String cpfClean = cpf.replace(".", "").replace("-", "");

        // check if size is eleven
        if (cpfClean.length() != 11)
            return false;

        // check if is number
        try {
            Long.parseLong(cpfClean);
        } catch (NumberFormatException e) {
            return false;
        }

        // continue
        int dvCurrent10 = Integer.parseInt(cpfClean.substring(9, 10));
        int dvCurrent11 = Integer.parseInt(cpfClean.substring(10, 11));

        // the sum of the nine first digits determines the tenth digit
        int[] cpfNineFirst = new int[9];
        int i = 9;
        while (i > 0) {
            cpfNineFirst[i - 1] = Integer.parseInt(cpfClean.substring(i - 1, i));
            i--;
        }

        // multiple the nine digits for your weights: 10,9..2
        int[] sumProductNine = new int[9];
        int weight = 10;
        int position = 0;
        while (weight >= 2) {
            sumProductNine[position] = weight * cpfNineFirst[position];
            weight--;
            position++;
        }

        // Verify the nineth digit
        int dvForTenthDigit = Arrays.stream(sumProductNine).sum() % 11;
        dvForTenthDigit = 11 - dvForTenthDigit; // rule for tenth digit
        if (dvForTenthDigit > 9)
            dvForTenthDigit = 0;
        if (dvForTenthDigit != dvCurrent10)
            return false;

        // verify tenth digit
        int[] cpfTenFirst = Arrays.copyOf(cpfNineFirst, 10);
        cpfTenFirst[9] = dvCurrent10;

        // multiple the nine digits for your weights: 10,9..2
        int[] sumProductTen = new int[10];
        int w = 11;
        int p = 0;
        while (w >= 2) {
            sumProductTen[p] = w * cpfTenFirst[p];
            w--;
            p++;
        }

        // Verify the eleventh digit
        int dvForEleventhDigit = Arrays.stream(sumProductTen).sum() % 11;
        dvForEleventhDigit = 11 - dvForEleventhDigit; // rule for eleventh digit
        if (dvForEleventhDigit > 9)
            dvForEleventhDigit = 0;
        return dvForEleventhDigit == dvCurrent11;
    }
}
