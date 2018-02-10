package com.yichan.gaotezhipei.common.constant;

/**
 * Created by Administrator on 2018/2/8 0008.
 */

public class Constans {
    public final static boolean DEMO_MODE = true;  //true:DEBUG,false:RELEASE

    public final static String CourseModal = "CourseModal";        //ORDERID

    //缓存文件名
    public static final String CONFIGFILE = "SP_CONFIGFILE";

    /**
     * 培训课程模式
     * @author simon
     */
    public enum COURSE_MODAL {
        ONLINE(1), OFFLINE(2);
        private int index;

        COURSE_MODAL(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public static String[] getNameArray(int index) {
            switch (index) {
                case 1 :
                    return new String[] {"线上课程"};
                case 2 :
                    return new String[] {"线下课程"};
            }
            return null;
        }
    };
}
