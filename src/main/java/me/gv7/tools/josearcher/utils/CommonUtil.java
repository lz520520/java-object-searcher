package me.gv7.tools.josearcher.utils;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
    public static String FINAL_FLAG = "[FINAL] ";
    public static String getBanner(){
        String banner = "#############################################################\n" +
                        "   Java Object Searcher v0.01\n" +
                        "   author: c0ny1<root@gv7.me>\n" +
                        "   github: http://github.com/c0ny1/java-object-searcher\n" +
                        "#############################################################\n\n\n";
        return banner;
    }
    public static String genExpress(String log) {
        String[] logs = log.split("--->");

        String idea_express = null;
        for (int i = 0; i < logs.length; i++) {
            logs[i] = logs[i].replace(FINAL_FLAG, "");
            Express express = new Express(logs[i]);
            if (i == 0) {
                idea_express = String.format("((%s)%s)", express.getClassName(), express.getField());
                continue;
            }
            String expressStr;
            Express currentExpress = new Express(logs[i]);
            Express previousExpress = new Express(logs[i - 1]);
            expressStr = String.format("((%s)%s.%s)",currentExpress.getClassName(), idea_express, currentExpress.getField());
            if (previousExpress.getClassName().contains("[Ljava")) {
                expressStr = String.format("((%s)%s%s)",currentExpress.getClassName(), idea_express, currentExpress.getField());
            }
            if (previousExpress.getClassName().contains("Map")) {
                expressStr = String.format("((%s)%s.get(\"%s\"))",currentExpress.getClassName(), idea_express, currentExpress.getField().replace("[", "").replace("]", ""));
            }
            idea_express = expressStr;
        }
        return idea_express;
    }

    public static String genExpress2(String log) {
        /*
            Object obj = Thread.currentThread();
           Object obj1 = CommonUtil.getFieldValue(obj,"threadLocals");
           Object obj2 = ((java.util.Map) obj1).get("table");
           Object obj3 = ((java.util.Map) obj2).get("14");
           Object obj4 = ((java.util.Map) obj3).get("value");
           Object obj5 = CommonUtil.getFieldValue(obj4,"referent");
           Object obj6 = CommonUtil.getFieldValue(obj5,"ce");
        * */
        String[] logs = log.split("--->");
        String objPrefix = "obj";
        String space = "    ";

        String lastObjName = "";
        String idea_express = null;
        Pattern arrayPattern = Pattern.compile("\\[L(.*?);");

        for (int i = 0; i < logs.length; i++) {
            logs[i] = logs[i].replace(FINAL_FLAG, "");
            Express currentExpress = new Express(logs[i]);
            if (i == 0) {
                lastObjName = objPrefix + i;
                idea_express = String.format("\n%sObject %s = %s;\n",space, lastObjName, currentExpress.getField());
                continue;
            }
            String expressStr;
            Express previousExpress = new Express(logs[i - 1]);

            if (arrayPattern.matcher(previousExpress.getClassName()).find()) {
                expressStr = String.format( "// Object %s = java.lang.reflect.Array.get(%s, %s);", objPrefix + i, lastObjName,currentExpress.getField().replace("[", "").replace("]", "") );
                expressStr += String.format( "\n" + space + "Object %s = ((%s)%s)%s;", objPrefix + i,previousExpress.getIdeaClassName(), lastObjName,  currentExpress.getField() );

            } else if (previousExpress.getIdeaClassName().endsWith("Map")) {
                expressStr = String.format("Object %s = ((java.util.Map) %s).get(\"%s\");",objPrefix + i, lastObjName, currentExpress.getField().replace("[", "").replace("]", ""));
            } else {
                expressStr = String.format("Object %s = me.gv7.tools.josearcher.utils.CommonUtil.getFieldValue(%s,\"%s\");", objPrefix + i, lastObjName, currentExpress.getField());
            }
            String  annotation = "// " + currentExpress.getClassName();
            idea_express = String.format("%s%s%s\n%s%s\n", idea_express,space,annotation,space, expressStr);
            lastObjName = objPrefix + i;
        }
        return idea_express;
    }
    public static void write2log(String filename,String content){
        try {
            File file = new File(filename);
            String new_content;
            if (!file.exists()) {
                file.createNewFile();
                new_content = getBanner() + content;
            }else{
                new_content = content;
            }

            //使用true，即进行append file
            FileWriter fileWritter = new FileWriter(file, true);
            fileWritter.write(new_content);
            fileWritter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String getBlank(int n){
        String strTab = "";
        for(int i=0;i<n;i++){
            strTab += " ";
        }
        return strTab;
    }

    public static String getCurrentDate(){
        Date date = new Date();
        String str = "yyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }


    // 反射获取属性
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field f = null;
        if (obj instanceof Field) {
            f = (Field) obj;
        } else {
            Method method = null;
            Class cs = obj.getClass();

            while (cs != null) {
                try {
                    f = cs.getDeclaredField(fieldName);
                    cs = null;
                } catch (Exception var6) {
                    cs = cs.getSuperclass();
                }
            }
        }

        f.setAccessible(true);
        return f.get(obj);
    }
}
