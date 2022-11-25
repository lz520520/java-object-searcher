import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import me.gv7.tools.josearcher.searcher.SearchRequstByBFS;
import me.gv7.tools.josearcher.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lz520520
 * @date 2022/11/22 9:34
 */

public class Test520Search {
    public static void main(String[] args) {
        test();


    }
    static void test2() {
        new SearchRequstByBFS(Thread.currentThread(), new Keyword.Builder().setField_name("single").build()).searchObject();
    }

    static void parseResult2Express() {
        String express = CommonUtil.genExpress2("TargetObject = {java.lang.Thread} \n" +
                "   ---> threadLocals = {java.lang.ThreadLocal$ThreadLocalMap} \n" +
                "    ---> table = {class [Ljava.lang.ThreadLocal$ThreadLocalMap$Entry;} \n" +
                "     ---> [14] = {java.lang.ThreadLocal$ThreadLocalMap$Entry} \n" +
                "      ---> value = {java.lang.ref.SoftReference} \n" +
                "       ---> referent = {java.lang.StringCoding$StringEncoder} \n" +
                "        ---> ce = {sun.nio.cs.ext.DoubleByte$Encoder} \n" +
                "        ---> [FINAL] MAX_SINGLEBYTE = {java.lang.Integer}");
        System.out.println(express);
    }

    static void getvalue() {

        // TargetObject.threadLocals.get("table").get("14").get("value").referent.ce.MAX_SINGLEBYTE
       try {
           Object obj = Thread.currentThread();
           Object obj1 = CommonUtil.getFieldValue(obj,"threadLocals");
           Object obj2 = ((java.util.Map) obj1).get("table");
           Object obj3 = ((java.util.Map) obj2).get("14");
           Object obj4 = ((java.util.Map) obj3).get("value");
           Object obj5 = CommonUtil.getFieldValue(obj4,"referent");
           Object obj6 = CommonUtil.getFieldValue(obj5,"ce");





       }catch (Exception e){

       }

    }
    static void  test() {
        //设置搜索类型包含Request关键字的对象
        List<Keyword> keys = new ArrayList();
        keys.add(new Keyword.Builder().setField_name("single").build());
//定义黑名单
        List<Blacklist> blacklists = new ArrayList();
        blacklists.add(new Blacklist.Builder().setField_type("java.io.File").build());
        blacklists.add(new Blacklist.Builder().setField_type("Exception").build());
        blacklists.add(new Blacklist.Builder().setField_name("contextClassLoader").build());
//        blacklists.add(new Blacklist.Builder().setField_type("ReferenceQueue$Lock").build());


//新建一个广度优先搜索Thread.currentThread()的搜索器
        SearchRequstByBFS searcher = new SearchRequstByBFS(Thread.currentThread(),keys);
// SearchRequstByDFS searcher = new SearchRequstByDFS(Thread.currentThread(),keys);
// 设置黑名单
        searcher.setBlacklists(blacklists);
//打开调试模式,会生成log日志
        searcher.setIs_debug(true);
//挖掘深度为20
        searcher.setMax_search_depth(10);
//设置报告保存位置
        searcher.setReport_save_path(".");
        searcher.searchObject();
    }
}
