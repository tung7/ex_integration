package com.tung7.ex.repository.base.reflect;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 内省机制- 命名规范。目标类的类名+BeanInfo。
 * 内省机制会先在目标类所在的 包中查找信息类(需要实现 BeanInfo接口)。
 * 如果没有找到信息类，就会默认使用SimpleBeanInfo
 * @author Tung
 * @version 1.0
 * @date 2017/9/11.
 * @update
 */
public class UserBeanInfo extends SimpleBeanInfo {
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            return new PropertyDescriptor[]{
                    new PropertyDescriptor("name", User.class),
                    new PropertyDescriptor("age", User.class),
                    new PropertyDescriptor("asian", User.class),
                    new PropertyDescriptor("married", User.class)
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        testPropertyDescriptor();
    }

    private static void testMethodDescriptor() {

    }

    private static void testPropertyDescriptor() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, String> params = new HashMap<String, String>() {{
            put("name", "Tom");
            put("age", "28");
            put("asian", "true");
            put("married", "true");
            put("genius", "150"); //由于信息类没有设置这个genius的PropertyDescriptor, 所以无法通过read/writerMethod控制该属性
        }};

        User u = new User();
        BeanInfo info = Introspector.getBeanInfo(User.class);
        PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : propertyDescriptors) {
            Method readMethod = pd.getWriteMethod();
            if (readMethod == null) {
                System.out.println(pd.getName() + " 属性对象的setter方法不存在！");
                continue;
            }
            Class paramType = pd.getPropertyType();
            System.out.println(paramType.getName());
            invokeSetter(paramType, params.get(pd.getName()), u, readMethod);
        }
        System.out.println(u);
    }
    private static void invokeSetter( Class paramType, String value, User u, Method readMethod)
            throws InvocationTargetException, IllegalAccessException {
        if (String.class.equals(paramType)) {
            readMethod.invoke(u, value);
        } else if (Integer.class.equals(paramType) || int.class.equals(paramType)) {
            readMethod.invoke(u, Integer.parseInt(value));
        } else if (Boolean.class.equals(paramType) || boolean.class.equals(paramType)) {
            readMethod.invoke(u, Boolean.parseBoolean(value));
        }
    }
}
