package com.pacific.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private volatile boolean connected = false;
    public static final String TAG = "socket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mergeConnect();
    }

    public void mergeConnect() {
        Observable
                .merge(connect("8.8.8.8", 4444, "A socket"),
                        connect("4.4.4.4", 5555, "B socket"),
                        connect("114.114.114.114", 6666, "C socket"))
                .flatMap(socket -> {
                    //如果已有可用的连接，释放socket
                    if (connected) {
                        socket.close();
                        return Observable.empty();
                    }
                    connected = true;
                    return Observable.just(socket);
                })
                //尝试60秒后,如果无可用连接，抛出异常
                .timeout(60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socket -> {
                    //在此处，可以操作最快线路的socket连接
                    Log.e(TAG, "最快线路是：" + socket.toString());
                }, e -> {
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
                });
    }

    public Observable<Socket> connect(String host, int port, String name) {
        return Observable
                //开始连接socket
                .fromCallable(() -> {
                    Log.e(TAG, name + " 开始连接服务器，当前线程id--> " + String.valueOf(Thread.currentThread().getId()));
                    return new Socket(host, port);
                })
                //发生异常,将重试3次数
                .retry(3)
                //3次重试无果,放弃该链路
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(Schedulers.newThread());
    }

}