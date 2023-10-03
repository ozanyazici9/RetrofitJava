package com.ozanyazici.retrofitjava.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    //CryptoModel sınıfı, API'den alınacak JSON verilerinin Java nesnelerine dönüştürüldüğü sınıftır.
    //@SerializedName anotasyonu, JSON verilerinin hangi alanlara eşleşeceğini belirtir.

    //SerializedName, JSON veya XML gibi veri formatlarıyla etkileşimde bulunurken,
    //verilerin hangi adla (veya isimle) eşleşeceğini belirtmek için kullanılır.
    @SerializedName("currency")
    public String currency;

    @SerializedName("price")
    public String price;
}
