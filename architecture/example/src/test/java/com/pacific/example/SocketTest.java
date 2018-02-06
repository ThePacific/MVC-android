package com.pacific.example;

import org.junit.Test;
import org.mockito.Mockito;

import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SocketTest {

    @Test
    public void mockConnect() {
        Observable
                //开始并发连接服务器
                //delay 模拟连接耗时
                //connectSuccessful 模拟连接过程中是否发生异常
                //name
                .merge(connect(2000, true, "A socket"),
                        connect(1000, true, "B socket"),
                        connect(300, false, "C socket"))
                .flatMap(socket -> {
                    //如果已有可用的连接，释放socket
                    if (connected) {
                        socket.close();
                        return Observable.empty();
                    }
                    connected = true;
                    return Observable.just(socket);
                })
                .observeOn(Schedulers.newThread())
                //尝试60秒后,如果无可用连接，抛出异常
                .timeout(60, TimeUnit.SECONDS)
                .subscribe(socket -> {
                    //在此处，可以操作最快线路的socket连接
                    System.out.println("最快线路是：" + socket.toString());
                }, e -> {
                    if (e instanceof TimeoutException) {
                        System.out.println("尝试60秒后，无可用socket连接 ");
                    } else {
                        e.printStackTrace();
                    }
                });

        //等待多线程执行完成，否则控制台看不到输出结果
        sleep(15 * 1000);
    }

    private volatile boolean connected = false;

    public Observable<Socket> connect(long delay, boolean connectSuccessful, final String name) {
        return Observable
                //开始连接socket
                .fromCallable(() -> {
                    System.out.println(name + " 开始连接服务器，当前线程id--> " + String.valueOf(Thread.currentThread().getId()));
                    sleep(delay);//模拟连接耗时
                    //Mock Socket对象
                    Socket socket = Mockito.mock(Socket.class);
                    Mockito.when(socket.isConnected()).thenReturn(connectSuccessful);
                    Mockito.when(socket.toString()).thenReturn(name);
                    Mockito.when(socket.getPort()).thenReturn((int) delay);
                    if (connectSuccessful) {
                        System.out.println(name + " 连接上服务器，当前线程id--> " + String.valueOf(Thread.currentThread().getId()));
                    }
                    return socket;
                })

                //模拟连接过程中发生异常
                .map(socket -> {
                    if (!socket.isConnected()) {
                        System.out.println(socket.toString() + "------>连接服务器时出错,将重试");
                        throw new UnsupportedOperationException(name);
                    }
                    return socket;
                })

                //发生异常,将重试3次数
                .retry(3)
                //3次重试无果,放弃该链路
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(Schedulers.newThread());
    }

    public static void sleep(long ms) {
        long start = System.currentTimeMillis();
        long duration = ms;
        boolean interrupted = false;
        do {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                interrupted = true;
            }
            duration = start + ms - System.currentTimeMillis();
        } while (duration > 0);

        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}