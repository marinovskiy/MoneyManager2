package alex.moneymanager.utils;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static final DateFormat POST_DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ssZ",
            Locale.UK
    );

    private static final SimpleDateFormat OPERATION_DATE_FORMAT = new SimpleDateFormat(
            "dd.MM.yy",
            Locale.UK
    );

    public static String getRelativeDateTimeString(String dateTime) {
        String result = null;
        try {
            Date date = POST_DATE_FORMAT.parse(dateTime);
            result = DateUtils.getRelativeTimeSpanString(
                    date.getTime(),
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS
            ).toString();

        } catch (ParseException ignore) {
        }
        return result;
    }

    public static String covertDateStr(String dateTime) {
        String result = null;
        try {
            Date date = POST_DATE_FORMAT.parse(dateTime);
            result = OPERATION_DATE_FORMAT.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}