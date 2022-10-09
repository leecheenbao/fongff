package fongff.util;

public class CommonUtil {

    /* 判斷是不是空值或空字串 */
    public static boolean isNull(Object object) {
        if (object == null) {
            return false;
        }
        if (object.equals("")) {
            return false;
        }
        return true;
    }
}
