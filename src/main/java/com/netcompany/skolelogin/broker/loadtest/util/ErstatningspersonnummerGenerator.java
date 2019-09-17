package com.netcompany.skolelogin.broker.loadtest.util;

public class ErstatningspersonnummerGenerator {

    public static String genererPersonnummer(int år, int måned, int dag, int løbenummer) {
        String fødselsdag = dag + 60 + "";

        String fødselsmåned = måned + "";
        if (fødselsmåned.length() == 1) {
            fødselsmåned = "0" + fødselsmåned;
        }

        String fødselsår = (år + "").substring(2, 4);

        String årstalsindikator = år >= 2000 ? "4" : "0";

        String løbenummerString="";
        if (løbenummer < 10) {
            løbenummerString = årstalsindikator + "00" + løbenummer;
        } else if (løbenummer < 100) {
            løbenummerString = årstalsindikator + "0" + løbenummer;
        } else if (løbenummer < 1000) {
            løbenummerString = årstalsindikator + løbenummer;
        }

        String erstatningspersonnummer = fødselsdag + fødselsmåned + fødselsår + løbenummerString;

        return erstatningspersonnummer;
    }
}
