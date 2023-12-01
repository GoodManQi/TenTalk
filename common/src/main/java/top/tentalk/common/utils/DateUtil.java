package top.tentalk.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 增加小时数
     *
     * @param date  时间
     * @param hours 需要增加的小时数
     * @return 增加后的时间
     */
    public static Date addHours(Date date, int hours) {
        // 如果date或hours为空，返回null
        if (date == null || hours == 0) {
            return null;
        }
        // 创建一个Calendar对象，用于操作日期
        Calendar calendar = Calendar.getInstance();
        // 设置Calendar对象的时间为date
        calendar.setTime(date);
        // 在Calendar对象的小时字段上增加hours
        calendar.add(Calendar.HOUR, hours);
        // 返回Calendar对象的时间，即date增加hours小时后的时间
        return calendar.getTime();
    }


}
