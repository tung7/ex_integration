package com.tung7.ex.repository.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/25.
 * @update
 */

public class DescriptionHandlers {

@RequestMapping("val")
    public String getDelRoleDescription(Object... args) {
        System.out.println(args[0]);
      return "getDelRoleDescription";
    }

    public static  void go() throws NoSuchMethodException {
        Class<DescriptionHandlers> clazz = DescriptionHandlers.class;
        Method m = clazz.getMethod("getDelRoleDescription",  new Class[]{Object[].class});
        System.out.println(Object[].class.getName());
        RequestMapping annotation = m.getAnnotation(RequestMapping.class);
        System.out.println("asdas: "  + annotation.value()[0]);

//        Method m = clazz.getMethod("getDelRoleDescription", String.class);
        try {
            int s1 = 12312332;
            String s2 = "123";
            Object[] params = new Object[]{s1, s2};
            m.invoke(new DescriptionHandlers(), new Object[]{params});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public String getMethodNameFromAnno (String vl) throws NoSuchMethodException{
        Pattern pattern =  Pattern.compile("\\{\\{(\\w+)\\}\\}");
        Matcher matcher = pattern.matcher(vl);
//        Matcher matcher = pattern.matcher("{{getDelRoleDescri}}");
        if (matcher.find()) {
            System.out.println("yes");
            return  matcher.group(1);
        } else {
            System.out.println("no");
            return "no";
        }
    }

    public static void main(String[] args)   {
        Long dd = new Long(123L);
        List<Long> list = new ArrayList<Long>(){
            {
                add(123L);
                add(121233L);
                add(123321L);
                add(123321231L);
            }
        };

//        System.out.println(dd.getClass().getName());
//        System.out.println(Long[].class.equals(sdd.getClass()));

        String fIdStrs= StringUtils.join(list.toArray(new Long[0]), ",");
        System.out.println(fIdStrs);
    }
}
