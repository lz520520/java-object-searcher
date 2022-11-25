package me.gv7.tools.josearcher.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestClass extends TestBaseClass {
    public TestBaseClass base;
    public List<Object> lists = new ArrayList();
    public String[] strs = new String[]{"1","2","3"};
    public Map<String,Object> map = new HashMap<String,Object>();

    public TestClass(){
        base = new TestBaseClass();
        EntityTest entity = new EntityTest();
        entity.setId(1);
        entity.setName("xxxx");
        lists.add(entity);
        lists.add(new Object[0]);
        lists.add(new Object[0]);

        map.put("aaa",new Object[0]);
        map.put("bbb",new Object[0]);
        map.put("ccc",new Object[0]);
    }
}
