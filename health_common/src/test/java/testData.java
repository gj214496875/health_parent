import com.itheima.utils.DateUtils;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;

public class testData {

    @Test
    public void data() throws Exception {
        LocalDate localDate = LocalDate.now().withDayOfMonth(1);

        String s = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        System.out.println(localDate);
        System.out.println(s);

    }
}
