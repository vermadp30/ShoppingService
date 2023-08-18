package com.stickyio.util;

import static com.stickyio.util.CustomerConstants.DATE_PATTERN;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

  public static String getFormattedDate(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
    return formatter.format(date);
  }
}
