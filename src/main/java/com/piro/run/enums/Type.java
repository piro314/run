package com.piro.run.enums;

/**
 * Created by ppirovski on 4/27/15. In Code we trust
 */
public enum Type {
    RUN(1),
    BIKE(2);

    private final int value;

    public int getValue() {
        return value;
    }

    private Type(int value){
        this.value = value;
    }

    public static Type fromInt(int value){

        for(Type t : values()){
            if(t.getValue() == value){
                return t;
            }
        }
        throw new IllegalStateException("no such Type: "+ value);

    }
}
