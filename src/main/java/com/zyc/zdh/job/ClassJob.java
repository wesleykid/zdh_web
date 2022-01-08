package com.zyc.zdh.job;

import com.zyc.zdh.entity.QuartzJobInfo;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassJob extends JobCommon2{

    private static String jobType = "CLASS";

    public static void run(QuartzJobInfo quartzJobInfo) {

        String softPath="/home/zyc/a.jar";
        URLClassLoader classLoader = null;
        try {
            classLoader = new URLClassLoader(new URL[]{new URL(softPath)},Thread.currentThread().getContextClassLoader());
            Class demo = classLoader.loadClass("com.amx.test.Test");
            Object object = demo.newInstance();
            System.out.println(demo.getMethod("invoke",String.class).invoke(object,new Object[]{"amx"}));
        } catch (MalformedURLException e) {
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(error, e.getCause());
        } catch (InstantiationException e) {
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(error, e.getCause());
        } catch (InvocationTargetException e) {
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(error, e.getCause());
        } catch (NoSuchMethodException e) {
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(error, e.getCause());
        } catch (IllegalAccessException e) {
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(error, e.getCause());
        } catch (ClassNotFoundException e) {
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(error, e.getCause());
        }


    }
}
