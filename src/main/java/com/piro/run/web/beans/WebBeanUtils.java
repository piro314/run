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

    public boolean filterBiggerKm(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }

        if(value == null) {
            return false;
        }

        double filterValue = 0;
        double rowValue = 0;
        try{
            filterValue =  1000*Double.valueOf(filterText);
            rowValue = ((Number)value).doubleValue();
        }
        catch (NumberFormatException e){
            return true;
        }

        return rowValue >= filterValue;
    }

    public boolean filterBiggerPercent(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }

        if(value == null) {
            return false;
        }

        if(filterText.endsWith("%")){
            filterText = filterText.substring(0,filterText.length()-1);
        }

        double filterValue = 0;
        double rowValue = 0;
        try{
            filterValue =  Double.valueOf(filterText)/100;
            rowValue = ((Number)value).doubleValue();
        }
        catch (NumberFormatException e){
            return true;
        }

        return rowValue >= filterValue;
    }

}
