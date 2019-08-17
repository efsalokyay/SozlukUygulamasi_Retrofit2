package com.efsalokyay.sozlukuygulamasiretrofit;

public class ApiUtils {

    public static final String BASE_URL = "http://kasimadalan.pe.hu/";

    public static KelimelerDaoInterface getKelimelerDaoInterface() {
        return RetrofitClient.getClient(BASE_URL).create(KelimelerDaoInterface.class);
    }
}