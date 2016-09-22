# MVC-Android
A pure MVC base library on android . Using it , your code can be very clean in MVC pattern .
<p>
[ ![Download](https://api.bintray.com/packages/thepacific/maven/mvc/images/download.svg) ](https://bintray.com/thepacific/maven/mvc/_latestVersion)
<p>
![](https://github.com/ThePacific/MVC-android/blob/master/art/mvc.png)

# Features
* Activity and Fragment as controllers
* Make MVC pattern more clean

# Example
This sample has a RecyclerView,Toolbar and SwipRefreshLayout
![](https://github.com/ThePacific/MVC-android/blob/master/art/preview.gif)
## Controller(Activity/Fragment)
```java
public class StartActivity extends Activity<StartModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        model = new StartModel(new StartView(this));
        model.onCreate();
    }

    public RecyclerAdapter<Item> getAdapter() {
        return model.getAdapter();
    }

    public void fetchItems(){
        model.fetchItems();
    }

    public void loadMoreItems(){
        model.loadMoreItems();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        model.onUserInteraction();
    }

    @Override
    public void onBackPressed() {
        if (!model.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
```

## Model
```java
public class StartModel extends ActivityModel<StartView> {

    private RecyclerAdapter<Item> adapter;

    public StartModel(StartView view) {
        super(view);
        adapter = new RecyclerAdapter<Item>(view.getController(), R.layout.item) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, Item item) {
                helper.setText(R.id.txt_name, item.getName());
            }
        };
    }

    @Override
    public void onCreate() {
        /** Override it when you have special stuff to deal with */
        super.onCreate();
    }
    public RecyclerAdapter<Item> getAdapter() {
        return adapter;
    }

    public void onUserInteraction() {
        view.onUserInteraction();
    }

    public boolean onBackPressed() {
        return view.onBackPressed();
    }


    public void loadMoreItems() {
        Observable.just(6)
                .compose(view.getController().<Integer>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, List<Item>>() {
                    @Override
                    public List<Item> call(Integer integer) {
                        List<Item> items = new ArrayList();
                        int count = adapter.getSize();
                        for (int i = 0; i < integer; i++) {
                            Item item = new Item("This is the " + (count + i) + "th item");
                            items.add(item);
                        }
                        return items;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        adapter.addAll(items);
                        view.setRefreshing(false);
                    }
                });
    }

    public void fetchItems() {
        Observable.just(4)
                .compose(view.getController().<Integer>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .map(new Func1<Integer, List<Item>>() {
                    @Override
                    public List<Item> call(Integer integer) {
                        List<Item> items = new ArrayList();
                        for (int i = 0; i < integer; i++) {
                            Item item = new Item("This is the " + i + "th item");
                            items.add(item);
                        }
                        return items;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Item>>() {
                    @Override
                    public void call(List<Item> items) {
                        adapter.replaceAll(items);
                    }
                });

    }
}
```

## View
```java
public class StartView extends ActivityView<StartActivity> {

    // Also , you can use ButterKnife
    //@BindView(R.id.toolbar) Toolbar toolbar
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private final int NONE_BACK_VALUE = 6;
    private final int SUPPORT_BACK_VALUE = 4;
    private final int MIDDLE_BACK_VALUE = 2;
    private final int PRIMARY_BACK_VALUE = 0;
    private boolean supportBack = false;
    private int pressedBackCount = PRIMARY_BACK_VALUE;

    public StartView(StartActivity activity) {
        super(activity);
        //ButterKnife.bind(this,activity);
    }

    @Override
    protected void findView() {
        toolbar = retrieveView(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.app_title);

        activity.setSupportActionBar(toolbar);
        recyclerView = retrieveView(R.id.rv_item);
        swipeRefreshLayout = retrieveView(R.id.srl);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new HorizontalItemDecoration.Builder(activity)
                .color(R.color.accent)
                .size(1)
                .showLastDivider()
                .build());
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activity.loadMoreItems();
            }
        });
    }

    @Override
    protected void setAdapter() {
        recyclerView.setAdapter(activity.getAdapter());
    }

    @Override
    protected void initialize() {
        activity.fetchItems();
    }

    public void setRefreshing(final boolean refreshing) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refreshing);
            }
        });
    }

    public void onUserInteraction() {
        if (supportBack) {
            supportBack = false;
        }
        if (pressedBackCount < NONE_BACK_VALUE) {
            pressedBackCount++;
        }
    }

    public boolean onBackPressed() {
        if (!supportBack && pressedBackCount != SUPPORT_BACK_VALUE) {
            supportBack = true;
            pressedBackCount = MIDDLE_BACK_VALUE;
            Snackbar.make(toolbar, R.string.snack_close, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.close, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
```

# Maven
```groory
compile 'com.github.thepacific:mvc:0.0.1'
```

# Dependencies
```groovy
compile 'com.android.support:appcompat-v7:24.2.1'
compile 'io.reactivex:rxjava:1.2.0'
compile 'io.reactivex:rxandroid:1.2.1'
compile 'com.trello:rxlifecycle:0.8.0'
compile 'com.trello:rxlifecycle-components:0.8.0'
```
