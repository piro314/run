package com.piro.run.web.beans;

import java.util.Locale;

/**
 * Created by ppirovski on 4/14/15. In Code we trust
 */
public class WebBeanUtils {


    public boolean filterBigger(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }

        if(value == null) {
            return false;
        }
        int filterValue = 0;
        try{
            filterValue =  Integer.valueOf(filterText);
        }
        catch (NumberFormatException e){
            return true;
        }

        return ((Comparable) value).compareTo(filterValue) >= 0;
    }
}
