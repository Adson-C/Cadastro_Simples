package com.ads.restaurantetectoy.custom;


import java.util.Calendar;
import java.util.InputMismatchException;

/**
 * Classe de apoio contendo métodos que podem
 * ser reutilizados em todo o projeto
 *
 * Criada por Adson Sá - 11/02/2024
 *
 * Versão v1
 */
public class AppUtil {

    public static final int TIME_SPLASH = 5 * 1000;
    public static final String PREF_APP = "app_cliente_vip_pref";
    public static final String LOG_APP = "ELEITOR_LOG";


    /**
     *
     * @return devolve a data atual
     */
    public static String getDataAtual(){

        String dia, mes, ano;

        String dataAtual = "00/00/0000";

        try {

            Calendar calendar = Calendar.getInstance();

            dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            mes = String.valueOf(calendar.get(Calendar.MONTH)+1);
            ano = String.valueOf(calendar.get(Calendar.YEAR));

           // p1 : p2 : p2

            dia = (Calendar.DAY_OF_MONTH<10) ? "0"+dia : dia;

            dia = (dia.length()>2) ? dia.substring(1,3) : dia;

            int mesAtual = (Calendar.MONTH)+1;

            mes = (mesAtual<=9) ? "0"+mes : mes;

            mes = (mes.length()>2) ? mes.substring(1,3) : mes;

            dataAtual = dia+"/"+mes+"/"+ano;

            return dataAtual;


        }catch (Exception e){



        }

        return dataAtual;

    }

    /**
     *
     * @return devolve a hora atual
     */
    public static String getHoraAtual(){

        String horaAtual = "00:00:00";

        String hora, minuto, segudo;

        try {

            Calendar calendar = Calendar.getInstance();

            int iHora = calendar.get(Calendar.HOUR_OF_DAY);
            int iMinuto = calendar.get(Calendar.MINUTE);
            int iSegundo = calendar.get(Calendar.SECOND);


            hora = (iHora <= 9) ? "0"+iHora : Integer.toString(iHora);
            minuto = (iMinuto <= 9) ? "0"+iMinuto : Integer.toString(iMinuto);
            segudo = (iSegundo <= 9) ? "0"+iSegundo : Integer.toString(iSegundo);

            horaAtual = hora+":"+minuto+":"+segudo;

            return horaAtual;


        }catch (Exception e){

        }

        return horaAtual;

    }
    public static boolean isCPF(String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {

            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {

                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48);


            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public static String mascaraCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }


}

