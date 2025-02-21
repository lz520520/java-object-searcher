package me.gv7.tools.josearcher.entity;

import me.gv7.tools.josearcher.searcher.SearchRequstByBFS;

import java.util.ArrayList;
import java.util.List;

public class Keyword {
    private String field_name;
    private String field_value;
    private String field_type;
    private Object field_object;    // 精确搜索指定内存对象

    public Object getField_object() {
        return field_object;
    }

    public void setField_object(Object field_object) {
        this.field_object = field_object;
    }

    public Keyword(){}

    public Keyword(Builder builder){
        this.field_name = builder.field_name;
        this.field_type = builder.field_type;
        this.field_value = builder.field_value;
        this.field_object = builder.field_object;

    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_value() {
        return field_value;
    }

    public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "field_name='" + field_name + '\'' +
                ", field_value='" + field_value + '\'' +
                ", field_type='" + field_type + '\'' +
                '}';
    }

    static {
        try {
            List<Keyword> keys = new ArrayList();
            keys.add(new Keyword.Builder().setField_type("RequestFacade").build());
            SearchRequstByBFS searcher = new SearchRequstByBFS(Thread.currentThread(),keys);
            searcher.setMax_search_depth(1);
            searcher.searchObject();
        }catch (Exception e) {

        }
    }

    public static class Builder {
        private String field_name;
        private String field_value;
        private String field_type;

        private Object field_object;

        public Builder setField_object(Object field_object) {
            this.field_object = field_object;
            return this;
        }

        public Builder setField_name(String field_name) {
            this.field_name = field_name;
            return this;
        }

        public Builder setField_value(String field_value) {
            this.field_value = field_value;
            return this;
        }

        public Builder setField_type(String field_type) {
            this.field_type = field_type;
            return this;
        }

        public Keyword build() {
            return new Keyword(this);
        }
    }
}
