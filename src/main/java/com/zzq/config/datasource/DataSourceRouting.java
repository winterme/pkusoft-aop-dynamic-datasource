package com.zzq.config.datasource;

public class DataSourceRouting {

    public static final String LICM_DATASOURCE_1 = "licmDataSource";
    public static final String LICM_DATASOURCE_2 = "licmDataSource2";

    private static final ThreadLocal<String> context = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return DataSourceRouting.LICM_DATASOURCE_2;
        }
    };

    public static String get(){
        return context.get();
    }

    public static void set(String dataSourceName){
        context.set(dataSourceName);
    }

    public static void remove(){
        context.remove();
    }

}
