package com.example.demo.batch.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    
    public static boolean isValidDate(String input, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false); // 엄격한 파싱 설정

        try {
            dateFormat.parse(input);
            return true; // 파싱 성공하면 형식이 맞는 것으로 판단
        } catch (ParseException e) {
            return false; // ParseException 발생하면 형식이 맞지 않는 것으로 판단
        }
    }
}
