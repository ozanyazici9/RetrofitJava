package com.ozanyazici.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ozanyazici.retrofitjava.R;
import com.ozanyazici.retrofitjava.adapter.RecyclerViewAdapter;
import com.ozanyazici.retrofitjava.databinding.ActivityMainBinding;
import com.ozanyazici.retrofitjava.model.CryptoModel;
import com.ozanyazici.retrofitjava.service.CryptoAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://raw.githubusercontent.com/";
    Retrofit retrofit;
    private ActivityMainBinding binding;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

            //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        //Retrofit & JSON

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadData();


    }

    private void loadData() {

       final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);
        //Proxy nesnesi, bu arayüzün bir uygulaması değildir, ancak bu arayüzle çalışmak için gereken işlevselliği ekler.
        //Özellikle Retrofit gibi kütüphaneler, bu işlevselliği sağlamak için Java'nın dinamik proxy mekanizmasını kullanır.
        //Bu, API çağrılarını dinamik olarak oluşturmak ve yönlendirmek için kullanılır.
        // call bir proxy nesnesidir. Proxy -> vekil.

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io()) //Nerede gözlemleyeceğim (arka planda)
                .observeOn(AndroidSchedulers.mainThread()) //Nerede göstereceğim (Main threadde)
                .subscribe(this::handleResponse)); //Ele alma metodu





        /*

        Call<List<CryptoModel>> call = cryptoAPI.getData();

        //işlemi asenkron yapmak ve gelecek veriyi almak için bunu yazıyorum.
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if (response.isSuccessful()) {
                    List<CryptoModel> responseList = response.body(); //body bana kripto model listesini veriyor.
                    //API çağrısından gelen veriler cryptoModels listesine atıldığında,
                    // Gson ve Retrofit bu verileri otomatik olarak CryptoModel nesnelerine dönüştürürler.
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
                    binding.recyclerView.setAdapter(recyclerViewAdapter);


                }

            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();

            }
        });

         */

    }

    private  void  handleResponse(List<CryptoModel> cryptoModelList) {

        cryptoModels = new ArrayList<>(cryptoModelList);

        //RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new RecyclerViewAdapter(cryptoModels);
        binding.recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
        //compositeDisposable.clear(); satırı, bu aktivitenin öldürüldüğü veya sonlandırıldığı durumda,
        //içindeki tüm abonelikleri iptal ederek kaynakların düzgün bir şekilde serbest bırakılmasını sağlar.
        //Bu, hafıza sızıntılarını önlemeye yardımcı olur ve uygulamanın performansını artırır.
    }
}

/*
1. İlk olarak, cryptoAPI.getData() çağrısı ile bir API isteği oluşturursunuz
 ve bu isteği Call<List<CryptoModel>> türündeki call değişkenine atarsınız.
 Bu, isteğin hazırlandığını ancak henüz gönderilmediğini gösterir.

 2. Ardından, call.enqueue(new Callback<List<CryptoModel>>() ile bu isteği asenkron olarak gönderirsiniz.
 Bu, isteğin arka planda çalışacağı ve API'den yanıt geldiğinde Callback metotlarının çağrılacağı anlamına gelir.

 3. onResponse metodu, API'den başarılı bir yanıt aldığınızda çağrılır.
 Bu metod içinde, API yanıtındaki verileri almak için response.body() kullanırsınız.
 Bu, API yanıtındaki JSON verilerini List<CryptoModel> türündeki responseList değişkenine dönüştürürsünüz.

 4. cryptoModels adlı bir ArrayList oluşturur ve bu liste, responseList verilerini içerir.
  Bu, API yanıtındaki verileri saklamak için kullanılan yerel bir liste olur.
 */



/*
İlk olarak, Gson kütüphanesinden bir Gson nesnesi oluşturulur.
Gson, JSON verilerini Java nesnelerine çevirmek (deserialization) ve Java nesnelerini JSON verilerine çevirmek (serialization) için kullanılır.
setLenient() metodunun çağrılması, Gson'un daha esnek bir ayrıştırma davranışına izin verir,
bu da JSON verilerinin küçük hatalar veya uyumsuzluklar içerdiği durumlarda daha iyi çalışmasını sağlar.
create() metodu, Gson nesnesini oluşturur.

Ardından, Retrofit kütüphanesinden bir Retrofit nesnesi oluşturulur.
 Retrofit, RESTful API ile iletişim kurmayı kolaylaştıran bir kütüphanedir.
  Retrofit.Builder sınıfı, bu Retrofit nesnesini yapılandırmak için kullanılır.

baseUrl(BASE_URL) metodu ile temel URL belirtilir.
 Bu, iletişim kurulacak API'nin kök URL'sini temsil eder.
  BASE_URL, API'nin temel URL'sini içeren bir sabit veya değişken olmalıdır.

addConverterFactory(GsonConverterFactory.create(gson)) metodu, Gson dönüşümünü ekler.
Bu, Retrofit'in API'den gelen JSON verilerini Java nesnelerine dönüştürmesini ve Java nesnelerini JSON verilerine dönüştürmesini sağlar.
 GsonConverterFactory.create(gson) ifadesi, önceki adımda oluşturulan Gson nesnesini kullanarak bir GsonConverterFactory oluşturur.

Son olarak, build() metodu ile Retrofit nesnesi oluşturulur.
Bu, Retrofit nesnesinin yapılandırılmasını tamamlar ve kullanıma hazır hale getirir.
 */