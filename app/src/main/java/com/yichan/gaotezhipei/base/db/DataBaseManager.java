package com.yichan.gaotezhipei.base.db;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ckerv on 2018/1/22.
 */

public class DataBaseManager<T extends RealmObject> implements DataBaseDao {

    private Realm mRealm;
    private Context mContext;
    private String mDbName;


    private List<RealmAsyncTask> mTasks = new LinkedList<>();


    public DataBaseManager(Context context, String dbName) {
        this.mContext = context.getApplicationContext();
        mDbName = dbName;
        Realm.init(mContext);
        mTasks = new LinkedList<>();
        mRealm = Realm.getInstance(initRealmConfiguration());
    }

    /**
     * 初始化Realm的Configuraion
     *
     * @return
     */
    private RealmConfiguration initRealmConfiguration() {
        return new RealmConfiguration.Builder().name(mDbName).encryptionKey(new byte[64]).schemaVersion(0).build();

    }

    @Override
    public <T extends RealmObject> void insert(T t) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(t);
        //mRealm.insert(t);
        mRealm.commitTransaction();
    }

    @Override
    public <T extends RealmObject> void insertList(List<T> list) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(list);
        //mRealm.insert(list);
        mRealm.commitTransaction();
    }

    @Override
    public <T extends RealmObject> void insertAsync(final T t, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        RealmAsyncTask mInsertTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(t);
            }
        }, onSuccess, onError);
        mTasks.add(mInsertTask);
    }

    @Override
    public <T extends RealmObject> void insertListAsync(final T t, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        RealmAsyncTask mInsertListTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(t);
            }
        }, onSuccess, onError);
        mTasks.add(mInsertListTask);
    }


    @Override
    public <T extends RealmObject> void delete(T t) {
        mRealm.beginTransaction();
        t.deleteFromRealm();
        //mRealm.delete(t);
        mRealm.commitTransaction();
    }

    @Override
    public <T extends RealmObject> void deteteAll(Class<T> clazz) {
        mRealm.beginTransaction();
        mRealm.where(clazz).findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
    }

    @Override
    public <T extends RealmObject> void deleteAsync(final T t, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        RealmAsyncTask mDeleteTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                t.deleteFromRealm();
            }
        }, onSuccess, onError);
        mTasks.add(mDeleteTask);
    }

    @Override
    public <T extends RealmObject> void deleteAllAsync(final Class<T> clazz, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        RealmAsyncTask mDeleteAllTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.where(clazz).findAll().deleteAllFromRealm();
            }
        }, onSuccess, onError);
        mTasks.add(mDeleteAllTask);
    }


    @Override
    public <T extends RealmObject> RealmResults queryAll(Class<T> clazz) {
        mRealm.beginTransaction();
        RealmResults results = mRealm.where(clazz).findAll();
        mRealm.commitTransaction();
        return results;
    }

    @Override
    public <T extends RealmObject> RealmResults<T> queryAllAsync(Class<T> clazz, final RealmChangeListener realmChangeListener) {
        final RealmResults<T> t = mRealm.where(clazz).findAllAsync();
        t.addChangeListener(new RealmChangeListener<RealmResults<T>>() {
            @Override
            public void onChange(RealmResults<T> element) {
                realmChangeListener.onChange(element);
                t.removeAllChangeListeners();
            }
        });
        return t;
    }


    @Override
    public <T extends RealmObject> RealmQuery<T> query(Class<T> clazz) {
        return mRealm.where(clazz);
    }

    @Override
    public <T extends RealmObject> void update(T t) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(t);
        mRealm.commitTransaction();
    }

    @Override
    public <T extends RealmObject> void updateAsync(final T t, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError) {
        RealmAsyncTask mUpdateTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(t);
            }
        }, onSuccess, onError);
        mTasks.add(mUpdateTask);
    }


    @Override
    public void close() {
        closeSafely();
    }

    private void closeSafely() {
        for (RealmAsyncTask task : mTasks) {
            if (task != null && !task.isCancelled()) {
                task.cancel();
            }
        }
        mTasks.clear();
        mRealm.close();
    }
}
