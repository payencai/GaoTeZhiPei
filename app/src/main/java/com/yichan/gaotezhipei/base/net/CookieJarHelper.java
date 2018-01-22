package com.yichan.gaotezhipei.base.net;

import android.content.Context;

import com.changelcai.mothership.network.cookie.CookieJarImpl;
import com.changelcai.mothership.network.cookie.store.MemoryCookieStore;
import com.changelcai.mothership.network.cookie.store.PersistentCookieStore;

/**
 * Created by ckerv on 2018/1/22.
 */

public class CookieJarHelper {
    private static CookieJarImpl sCookieJarImpl;

    public static CookieJarImpl createMemoryCookieJar() {
        if (sCookieJarImpl == null) {
            sCookieJarImpl = new CookieJarImpl(new MemoryCookieStore());
        }
        return sCookieJarImpl;
    }

    public static CookieJarImpl createDiskCookieJar(Context context) {
        if (sCookieJarImpl == null) {
            sCookieJarImpl = new CookieJarImpl(new PersistentCookieStore(context));
        }
        return sCookieJarImpl;
    }

    public static CookieJarImpl getCookieJarImpl() {
        if (sCookieJarImpl == null) createMemoryCookieJar();
        return sCookieJarImpl;
    }

    public static void clearCookie(){
        CookieJarHelper.getCookieJarImpl()
                .getCookieStore().removeAll();
    }
}
