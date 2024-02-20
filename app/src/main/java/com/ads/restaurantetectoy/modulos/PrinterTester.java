package com.ads.restaurantetectoy.modulos;

import android.graphics.Bitmap;
import android.util.Log;

import com.ads.restaurantetectoy.model.CadastroActivity;
import com.pax.dal.IPrinter;
import com.pax.dal.entity.EFontTypeAscii;
import com.pax.dal.entity.EFontTypeExtCode;
import com.pax.dal.exceptions.PrinterDevException;

public class PrinterTester extends CadastroActivity {
    private static PrinterTester printerTester;
    private IPrinter iPrinter;
    private String log = "PrinterTester";

    private PrinterTester(){
        iPrinter = CadastroActivity.getDal().getPrinter();
    }

    public static PrinterTester getInstance(){
        if (printerTester == null){
            printerTester = new PrinterTester();
        }
        return printerTester;
    }

    public int getDotLine() {
        try {
            int dotLine = iPrinter.getDotLine();
            Log.i("getDotLine", "getDotLine: OK");
            return dotLine;
        } catch (PrinterDevException e) {
            e.printStackTrace();
            Log.e("getDotLine", e.toString());
            return -2;
        }
    }

    public void init(){
        try{
            iPrinter.init();
        }catch (PrinterDevException e){
            e.printStackTrace();
        }
    }


    public void printStr(String texto, String charset){
        try{
            iPrinter.printStr(texto, charset);
        }catch (PrinterDevException e){
            e.printStackTrace();
        }
    }

    public String start(){
        try{
            int res = iPrinter.start();
            return statusCode2Str(res);
        }catch (PrinterDevException e){
            e.printStackTrace();
            return "";
        }
    }

    public void cutPaper(int mode) {

        try {
            iPrinter.cutPaper(mode);
        }catch (PrinterDevException e){
            e.printStackTrace();
        }
    }

    public String statusCode2Str(int status) {
        String res = "";
        switch (status) {
            case 0:
                res = "Success ";
                break;
            case 1:
                res = "Printer is busy ";
                break;
            case 2:
                res = "Out of paper ";
                break;
            case 3:
                res = "The format of print data packet error ";
                break;
            case 4:
                res = "Printer malfunctions ";
                break;
            case 8:
                res = "Printer over heats ";
                break;
            case 9:
                res = "Printer voltage is too low";
                break;
            case 240:
                res = "Printing is unfinished ";
                break;
            case 252:
                res = " The printer has not installed font library ";
                break;
            case 254:
                res = "Data package is too long ";
                break;
            default:
                break;
        }
        return res;
    }

    public void fontSet(EFontTypeAscii AsciiFontType, EFontTypeExtCode cFontType){
        try{
            iPrinter.fontSet(AsciiFontType, cFontType);
        }catch(PrinterDevException e){
            e.printStackTrace();
        }
    }

    public void setGray(int level){
        try{
            iPrinter.setGray(level);
        }catch(PrinterDevException e){
            e.printStackTrace();
        }
    }


    public void printBitmap(Bitmap bitmap) {
        try {
           iPrinter.printBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
