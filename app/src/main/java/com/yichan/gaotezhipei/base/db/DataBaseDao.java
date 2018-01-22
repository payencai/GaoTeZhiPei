package com.yichan.gaotezhipei.base.db;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ckerv on 2018/1/22.
 */

public interface DataBaseDao {
    <T extends RealmObject> void insert(T t);

    <T extends RealmObject> void insertList(List<T> list);

    <T extends RealmObject> void insertAsync(T t, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError);

    <T extends RealmObject> void insertListAsync(T t, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError);

    <T extends RealmObject> void delete(T t);

    <T extends RealmObject> void deteteAll(Class<T> t);

    <T extends RealmObject> void deleteAsync(T t, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError);

    <T extends RealmObject> void deleteAllAsync(Class<T> clazz, Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError);

    <T extends RealmObject> RealmResults<T> queryAll(Class<T> clazz);

    <T extends RealmObject> RealmResults<T> queryAllAsync(Class<T> clazz, RealmChangeListener realmChangeListener);

    <T extends RealmObject> RealmQuery<T> query(Class<T> clazz);

    <T extends RealmObject> void update(T t);

    <T extends RealmObject> void updateAsync(T t,  Realm.Transaction.OnSuccess onSuccess, Realm.Transaction.OnError onError);

    <T extends RealmObject> void close();
}
