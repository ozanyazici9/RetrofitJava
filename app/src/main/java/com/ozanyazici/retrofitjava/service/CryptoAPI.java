package com.ozanyazici.retrofitjava.service;

import com.ozanyazici.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {

    //CryptoAPI arayüzü, API'nin hangi URL'ye ve hangi türde istekler yapılacağını belirten metodları içerir.
    // Bu örnekte sadece getData adında bir metod bulunur.

    //GET, POST, UPDATE, DELETE

    //URL BASE -> www.website.com
    //GET -> price?key=xxx

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //Burada url sini sonradan vereceğim şu adrese bir get isteği yolla
    //sonunda da call yap ve bana bir liste içerisinde kripto modelleri gelicek diyorum ve bütün bu metodun adına getData diyorum.

    Observable<List<CryptoModel>> getData();
    //Observable yazarak rxJava yı kullanıyoruz. RxJava yı kullanmamızın sebebi daha karmaşık apilerde yani çok call isteği olan vb. apilerde
    //bu işi call ile yapmak sıkıntılar çıkarabiliyor bunu engellemek ve kodun daha düzgün gözükmesi için RxJava yı kullanıyoruz.
    //Gözlemlenebilir bir nesne oluşturuyoruz veri setinde bir değişiklik olduğunda bunu gözlemleyen objelere yayınlıyor bildiriyor

    //Call<List<CryptoModel>> getData();



    /*
    İşte Call sınıfının temel görevleri:

    1. Bir API isteği oluşturmak: Call sınıfı, bir API isteğinin nasıl yapılandırılacağını ve hangi URL'ye gönderileceğini belirtir.

    2. API isteğini göndermek: Call nesnesi, .enqueue() veya .execute() gibi yöntemler kullanılarak API isteğini gönderir.
     .enqueue() yöntemi isteği asenkron olarak gönderirken, .execute() yöntemi isteği senkron bir şekilde gönderir.

    3. API yanıtını almak: API yanıtı, isteğin sonucunda döner. Yanıt, HTTP durum kodu, başlık bilgileri ve yanıt verisi gibi bilgileri içerir.
     */




}
