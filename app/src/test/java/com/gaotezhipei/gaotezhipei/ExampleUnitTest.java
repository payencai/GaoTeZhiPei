package com.gaotezhipei.gaotezhipei;

import com.yichan.gaotezhipei.common.util.GsonUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");



        System.out.print(GsonUtil.GsonString(list));

    }
}