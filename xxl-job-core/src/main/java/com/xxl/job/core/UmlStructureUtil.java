package com.xxl.job.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.xxl.rpc.remoting.provider.XxlRpcProviderFactory;

public class UmlStructureUtil {

    public static void main(String[] args) {
        soutAttributeStruct(XxlRpcProviderFactory.class, "");
    }

    private static  void soutAttributeStruct(Class oriClass, String space) {
        space = space + " ";
        for (; oriClass != Object.class; oriClass = oriClass.getSuperclass()) {
            //打印该类下面的所有接口信息
            if(oriClass==null){
                return;
            }
            Class[] interfaces = oriClass.getInterfaces();
            soutInterfaceInfo(interfaces,space);
            //属性
            System.out.println("----------------------"+oriClass.getName()+"-------------------------");
            System.out.println("----------------------Field-------------------------");
            Field[] declaredFields = oriClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Class<?> type = field.getType();
                String typeName = type.getName().split("\\.")[type.getName().split("\\.").length - 1];
                int modifiers = field.getModifiers();
                String modifierStr = Modifier.toString(modifiers);
                String fileName = field.getName();
                System.out.println(space + modifierIcon(modifierStr) + " " + fileName + " : " + typeName + "");
            }
            System.out.println("----------------------Method-------------------------");
            //方法
            Method[] declaredMethods = oriClass.getDeclaredMethods();
            soutMethod(declaredMethods,space);
        }

    }

    private static void soutMethod( Method[] declaredMethods,String space){
        //Method[] declaredMethods = oriClass.getDeclaredMethods();
        for (Method declaredMethod:declaredMethods) {
            String name = declaredMethod.getName();
            int modifiers = declaredMethod.getModifiers();
            String modifierStr = Modifier.toString(modifiers);
            String  returnType = declaredMethod.getReturnType().getName().split("\\.")[declaredMethod.getReturnType().getName().split("\\.").length-1];
            Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
            String paramStr = "";
            int var = 0;
            for (Class parameterClass:parameterTypes) {
                String paraName = parameterClass.getName();
                String pName = paraName.split("\\.")[ paraName.split("\\.").length-1];
                paramStr = paramStr+pName+" var"+(var++)+",";
            }
            paramStr = stringIsNotBlank(paramStr)? paramStr.substring(0,paramStr.length()-2):paramStr;
            System.out.println(space + modifierIcon(modifierStr)+" "+ name+"("+paramStr+")"+" : "+returnType );
        }
    }
    private static void soutInterfaceInfo(Class[] interfaceClz,String space){
        if(interfaceClz!=null &&interfaceClz.length>0){
            for (Class cl:interfaceClz){
                Class[] cl2s = cl.getInterfaces();
                if(cl2s!=null &&cl2s.length>0){
                    soutInterfaceInfo(cl2s,space);
                    System.out.println("++++++++++++++++++ Interface "+cl.getName()+"++++++++++++++++++ ");
                    soutInf(cl,space);
                }else {
                    System.out.println("++++++++++++++++++ Interface "+cl.getName()+"++++++++++++++++++ ");
                    soutInf(cl,space);
                }
            }
        }
    }

    private static void soutInf(Class interfaceClz,String space){
        Method[] methods = interfaceClz.getMethods();
        soutMethod(methods,space);
    }

    private static String modifierIcon(String modifierStr) {
        String[] split = modifierStr.split(" ");
        String str ="" ;
        if(split.length>1){
            modifierStr = split[0];
            for (int i = 1;i<split.length;i++){
                str = str+" "+split[i]; //
            }
        }
        String strIcon = "#";
        switch (modifierStr) {
            case "private":
                strIcon = "-";
                break;
            case "protected":
                strIcon = "#";
                break;
            case "public":
                strIcon = "+";
                break;
        }
        return strIcon + str;
    }

    public static boolean stringIsNotBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

}

































