package com.ads.restaurantetectoy.custom;
/**
 * Classe de apoio contendo métodos que podem
 * ser reutilizados em todo o projeto
 *
 * Criada por Adson Sá - 11/02/2024
 *
 * Versão v1
 */

public class CpfUtil {

    public static boolean isCPFValido(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) {
            primeiroDigito = 0;
        }

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito > 9) {
            segundoDigito = 0;
        }

        // Verifica se os dígitos calculados correspondem aos dígitos reais
        return Character.getNumericValue(cpf.charAt(9)) == primeiroDigito
                && Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
    }

    // mascara CPF para gravar no banco de dados
    public static String mascaraCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

//    public static void main(String[] args) {
//        // Exemplo de uso
//        String cpf = "123.456.789-09";
//        if (isCPFValido(cpf)) {
//            System.out.println("CPF válido!");
//        } else {
//            System.out.println("CPF inválido!");
//        }
//    }
}

