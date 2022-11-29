import me.gv7.tools.josearcher.entity.Blacklist;
import me.gv7.tools.josearcher.entity.Keyword;
import me.gv7.tools.josearcher.searcher.SearchRequstByBFS;
import me.gv7.tools.josearcher.test.TestClass;
import me.gv7.tools.josearcher.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ar3h
 * @date 2022/11/25 15:44
 */

public class TestAr3hSearch {
    public static void main(String[] args) throws Exception {
        test();


    }

    static void test() throws Exception {
        TestClass testClass = new TestClass();
        Object target = new Object();   // 目标
        testClass.lists.add(target);


        //设置搜索类型包含Request关键字的对象
        List<Keyword> keys = new ArrayList();
//        keys.add(new Keyword.Builder().setField_name("Exception").build());
        keys.add(new Keyword.Builder().setField_object(target).build());    // 使用setField_object精确搜索
        //定义黑名单
        List<Blacklist> blacklists = new ArrayList();
        blacklists.add(new Blacklist.Builder().setField_type("java.io.File").build());
        blacklists.add(new Blacklist.Builder().setField_type("Exception").build());
        blacklists.add(new Blacklist.Builder().setField_name("contextClassLoader").build());
        blacklists.add(new Blacklist.Builder().setField_type("ReferenceQueue$Lock").build());


        //新建一个广度优先搜索Thread.currentThread()的搜索器
        SearchRequstByBFS searcher = new SearchRequstByBFS(testClass, keys);
        // SearchRequstByDFS searcher = new SearchRequstByDFS(Thread.currentThread(),keys);
        // 设置黑名单
        searcher.setBlacklists(blacklists);
        //打开调试模式,会生成log日志
        //        searcher.setIs_debug(true);
        //挖掘深度为10
        searcher.setMax_search_depth(10);
        //设置报告保存位置
        searcher.setReport_save_path("/tmp/");
        searcher.searchObject();
    }

}
