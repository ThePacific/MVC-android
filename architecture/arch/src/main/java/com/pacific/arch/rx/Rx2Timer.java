package com.pacific.arch.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class Rx2Timer {

    private Disposable disposable;
    private long take;
    private long period;
    private long initialDelay;
    private TimeUnit unit;
    private Action onComplete;
    private Consumer<Long> onEmit;
    private Consumer<Throwable> onError;
    private long pauseTake = 0l;
    private long resumeTake = 0l;
    private boolean isPause = false;
    private boolean isStarted = false;

    private Rx2Timer(Builder builder) {
        take = builder.take;
        period = builder.period;
        initialDelay = builder.initialDelay;
        unit = builder.unit;
        onComplete = builder.onComplete;
        onEmit = builder.onEmit;
        onError = builder.onError;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * is in pause state
     */
    public boolean isPause() {
        return isPause;
    }

    /**
     * restart timer , all pause states are cleaned
     */
    public Rx2Timer restart() {
        stop();
        return start();
    }

    /**
     * start timer
     */
    public Rx2Timer start() {
        if (isPause) {
            return restart();
        }
        if (disposable == null || disposable.isDisposed()) {
            disposable = Observable.interval(initialDelay, period, unit)
                    .subscribeOn(Schedulers.single())
                    .take(take + 1)
                    .map((aLong -> {
                        pauseTake = aLong;
                        return take - aLong;
                    }))
                    .doOnSubscribe((i) -> isStarted = true)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if (onEmit != null) {
                            onEmit.accept(aLong);
                        }
                    }, e -> {
                        if (onError != null) {
                            onError.accept(e);
                        }
                    }, () -> {
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    });
        }
        return this;
    }

    /**
     * stop timer and all pause states are cleaned
     */
    public void stop() {
        if (disposable != null) {
            disposable.dispose();
        }
        if (isPause) {
            reset();
        }
    }

    /**
     * pause timer
     */
    public void pause() {
        if (isPause || !isStarted) {
            return;
        }
        stop();
        isPause = true;
        resumeTake += pauseTake;
    }

    /**
     * resume timer
     */
    public void resume() {
        if (!isPause) {
            return;
        }
        isPause = false;
        if (disposable == null || disposable.isDisposed()) {
            disposable
                    = Observable.interval(initialDelay, period, unit)
                    .subscribeOn(Schedulers.single())
                    .take(take + 1 - resumeTake)
                    .map(aLong -> {
                        pauseTake = aLong;
                        return take - aLong - resumeTake;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if (onEmit != null) {
                            onEmit.accept(aLong);
                        }
                    }, e -> {
                        reset();
                        if (onError != null) {
                            onError.accept(e);
                        }
                    }, () -> {
                        reset();
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    });
        }
    }

    public void reset() {
        isPause = false;
        resumeTake = 0l;
        pauseTake = 0l;
        isStarted = false;
    }

    public static final class Builder {

        private long take = 60;
        private long period = 1;
        private long initialDelay = 0;
        private TimeUnit unit = TimeUnit.SECONDS;
        private Action onComplete;
        private Consumer<Long> onEmit;
        private Consumer<Throwable> onError;

        Builder() {
        }

        /**
         * counting number , default value is 60
         *
         * @param take take value
         */
        public Builder take(int take) {
            this.take = take;
            return this;
        }

        /**
         * period, default value is 1
         *
         * @param period period value
         */
        public Builder period(int period) {
            this.period = period;
            return this;
        }

        /**
         * delay to begin , default value is 0
         *
         * @param initialDelay delay value
         */
        public Builder initialDelay(int initialDelay) {
            this.initialDelay = initialDelay;
            return this;
        }

        /**
         * time unit , default value is TimeUnit.SECONDS
         *
         * @param unit unit value
         */
        public Builder unit(TimeUnit unit) {
            this.unit = unit;
            return this;
        }

        public Builder onComplete(Action onComplete) {
            this.onComplete = onComplete;
            return this;
        }

        public Builder onEmit(Consumer<Long> onEmit) {
            this.onEmit = onEmit;
            return this;
        }

        public Builder onError(Consumer<Throwable> onError) {
            this.onError = onError;
            return this;
        }

        public Rx2Timer build() {
            return new Rx2Timer(this);
        }
    }
}