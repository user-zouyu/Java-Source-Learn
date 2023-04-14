package com.zouyu.common;

/**
 * @author ZouYu 2023/4/9 19:46
 * @version 1.0.0
 */
public class TimeUtils {
    public static class SECONDS {
        public static void sleep(long seconds) {
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static class MILLISECONDS {
        public static void sleep(long milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
