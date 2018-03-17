package com.yichan.gaotezhipei.common;

/**
 * Created by Simon on 2016/5/16.
 */

import android.util.SparseArray;

import com.esharp.android.common.SpecialAsyncTask;
import com.esharp.android.common.SpecialAsyncTaskListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class AsyncTaskManager {

    private static SparseArray<List<SpecialAsyncTask>> taskListSparseArray = new SparseArray<List<SpecialAsyncTask>>();

    public static void executeAsyncTask(int key, SpecialAsyncTask asyncTask, Object... objects) {
        List<SpecialAsyncTask> taskList = taskListSparseArray.get(key);
        if (taskList == null) {
            taskList = new ArrayList<SpecialAsyncTask>();
            taskListSparseArray.put(key, taskList);
        }

        if (!taskList.contains(asyncTask)) {
            taskList.add(asyncTask);
            asyncTask.setSpecialAsyncTaskListener(new SpecialAsyncTaskListener(key) {

                @Override
                public void onFinally(SpecialAsyncTask asyncTask) {
                    removeAsyncTask(this.getSpecialAsyncTaskKey(), asyncTask);
                }

            });
//			asyncTask.execute(objects);
            Executors.newFixedThreadPool(100);
            asyncTask.executeOnExecutor(SpecialAsyncTask.THREAD_POOL_EXECUTOR, objects);
        }

    }

    public static void cancelAsyncTask(int key) {
        List<SpecialAsyncTask> taskList = taskListSparseArray.get(key);
        if (taskList != null) {
            for (SpecialAsyncTask asyncTask : taskList) {
                asyncTask.cancel(true);
            }
            taskList.clear();
            taskListSparseArray.remove(key);
        }
    }

    public static void removeAsyncTask(int key, SpecialAsyncTask asyncTask) {
        List<SpecialAsyncTask> taskList = taskListSparseArray.get(key);
        if (taskList != null && asyncTask != null) {
            asyncTask.cancel(true);
            taskList.remove(asyncTask);
        }
    }

    public static void showAsyncTaskLoading(boolean show) {
        int key = 0;
        int taskListSparseArraySize = taskListSparseArray.size();
        int tempAsyncTaskListSize;
        SpecialAsyncTask tempAsyncTask;
        for (int i = 0; i < taskListSparseArraySize; i++) {
            key = taskListSparseArray.keyAt(i);
            // get the object by the key.
            List<SpecialAsyncTask> tempAsyncTaskList = taskListSparseArray.get(key);
            tempAsyncTaskListSize = tempAsyncTaskList.size();
            for (int j = 0; j < tempAsyncTaskListSize; j++) {
                tempAsyncTask = tempAsyncTaskList.get(j);
                if (show && tempAsyncTask.isShowLoading()) {
                    tempAsyncTask.showLoading();
                } else {
                    tempAsyncTask.closeLoading();
                }
            }
        }
    }

}
