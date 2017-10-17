package com.tung7.ex.repository.base.reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/9/11.
 * @update
 */
public class IntrospectorTest {
    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        User u = new User("Tom", 13, 189, false, true);

        BeanInfo beanInfo = Introspector.getBeanInfo(User.class);

        PropertyDescriptor[] userPds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : userPds) { //有beanInfo的定义，这里是拿不到genius的
            System.out.println("属性名:" +
                    pd.getName() +
                    " , 属性值：" +
                    pd.getReadMethod().invoke(u, null));
        }

        String[] srchPaths = Introspector.getBeanInfoSearchPath();
        System.out.println("Search Path :");
        for (String path : srchPaths) {
            System.out.println(path);
        }

    }
}
