/**
 * @author lz520520
 * @date 2022/11/23 12:48
 */

package me.gv7.tools.josearcher.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Express {
    private String field;
    private String className;
    private String ideaClassName;

    public String getField() {
        return field;
    }

    public String getClassName() {
        return className;
    }


    public String getIdeaClassName() {
        return this.ideaClassName;
    }

    public Express(String data) {
        String[] datas = data.split(" = ");
        this.field = datas[0].trim();
        this.className = datas[1].trim();


        this.ideaClassName = this.className.replace("$", ".");
        if (this.ideaClassName.length() > 1) {
            this.ideaClassName = this.ideaClassName.substring(1,this.ideaClassName.length()-1);
            Pattern p = Pattern.compile("\\[L(.*?);");
            Matcher m = p.matcher(this.ideaClassName);
            if (m.find()) {
                this.ideaClassName = m.group(1)+"[]";
            }
        }
    }
}
