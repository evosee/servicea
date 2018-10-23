package com.example.demo.servicea.controller.zuul;

public class RelationIdContextHolder {
    private static final ThreadLocal<String> relation = new InheritableThreadLocal<>();
    public static String get(){
        return relation.get();
    }
    public static void set(String value){
         relation.set(value);
    }
}
