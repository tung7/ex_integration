package com.tung7.ex.repository.markdown;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Tung on 2016/12/9.
 */
public class MetaInfo {
    public class Keys {
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String TOC = "toc";
        public static final String DATE = "date";
        public static final String SUBTITILE = "subtitle";
        public static final String CATEGORIES = "categories";
        public static final String TAGS = "tags";
        public static final String PHOTOS = "photos";
        public static final String EXCERPTS = "excerpts";
    }


    private String title;
    private String author;
    private Boolean toc;
    private String date;
    private String subtitle;
    private String categories;
    private List<String> tags;
    private String cover;
    private String photo;
    private String excerpts;

    private PropertyDescriptor[] descriptors;

    private void init() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(MetaInfo.class, Object.class);
        descriptors = beanInfo.getPropertyDescriptors();
    }

    public MetaInfo() {
        try {
            init();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getToc() {
        return toc;
    }

    public void setToc(String t) {
        Boolean toc = Boolean.valueOf(t);
        this.toc = toc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String c) {
        this.categories = categories;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(String s) {
        List<String> tags = new ArrayList<>();
        s = s.trim();
        if (s.startsWith("[") && s.endsWith("]")) {
            s = s.substring(1, s.length()-1);
            for (String temp: s.split(",")) {
                tags.add(temp.trim().toUpperCase());
            }
        }
        this.tags = tags;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getExcerpts() {
        return excerpts;
    }

    public void setExcerpts(String excerpts) {
        this.excerpts = excerpts;
    }

    private PropertyDescriptor findPropDescriptor(String name) {
        for (PropertyDescriptor d: descriptors) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    public void put(String key, String value) {
        Class<MetaInfo> metaInfoClass = MetaInfo.class;
        try {
            Field field = metaInfoClass.getDeclaredField(key);
            PropertyDescriptor dd = findPropDescriptor(key);
            if (dd == null) {
                return;
            }
            if (Keys.TAGS.equals(key)) {
                this.setTags(value);
            } else if (Keys.TOC.equals(key)) {
                this.setToc(value);
            } else {
                dd.getWriteMethod().invoke(this, value);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
