package com.tung7.ex.repository.aop.aspect;

import com.tung7.ex.repository.aop.AuditLog;
import com.tung7.ex.repository.aop.dao.AuditLogDao;
import com.tung7.ex.repository.aop.domain.AuditLogBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */
@Aspect
@Component
public class AuditLogAspect {
    @Autowired
    AuditLogDao auditLogDao;
    /**
     * Service层切点
     */
    @Pointcut("@annotation(com.tung7.ex.repository.aop.AuditLog)")
    public void serviceLogAspect() {

    }

    @AfterThrowing(value = "serviceLogAspect()", throwing="throwable")
    public void doLog(JoinPoint joinPoint, Throwable throwable) {


        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
//            String targetName = joinPoint.getTarget().getClass().getName(); //目标类名
//            Class targetClass = Class.forName(targetName); // 目标类
//            if (!targetClass.isAnnotationPresent(Service.class)) {
//
//
//            }

            //数据库日志
            Map<String, String> rtn = getAnnoArgs(joinPoint);
            AuditLogBean bean =  new AuditLogBean();
            bean.setTerminal(AuditLogBean.Terminal.IOS)
                    .setIp(request.getRemoteAddr())
                    .setOperationTime(System.currentTimeMillis())
                    .setDescription(rtn.get("description"))
                    .setType(rtn.get("rtn"))
                    .setStatus(false);

            auditLogDao.save(bean);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterReturning(value = "serviceLogAspect()", returning = "returnVal")
    public  void doServiceLog(JoinPoint joinPoint, Object returnVal) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取管理员用户信息
        try {
            //数据库日志
            Map<String, String> rtn = getAnnoArgs(joinPoint);
            AuditLogBean bean =  new AuditLogBean();
            bean.setTerminal(AuditLogBean.Terminal.IOS)
                    .setIp(request.getRemoteAddr())
                    .setOperationTime(System.currentTimeMillis())
                    .setDescription(rtn.get("description"))
                    .setType(rtn.get("rtn"))
                    .setStatus(true);

            auditLogDao.save(bean);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 该方法无法识别重载（overload）
     * @param joinPoint
     * @return
     * @throws Exception
     */
    @Deprecated
    private String getServiceDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(AuditLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    private Map<String, String> getAnnoArgs(JoinPoint joinPoint) throws Exception {
        Map<String, String> rtn = new HashMap<>();

        String targetName = joinPoint.getTarget().getClass().getName(); //目标类名
        Class targetClass = Class.forName(targetName); // 目标类
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName(); // 修饰方法名
        Object[] arguments = joinPoint.getArgs(); //修饰方法的参数



        //获取方法参数类型列表
//        int argsLen = arguments.length;
//        Class<?>[] clazzs = new Class<?>[argsLen];
//        if (argsLen > 0) {
//            System.out.println(arguments[0].getClass());
//            int i = 0;
//            for (Object o : arguments) {
//                clazzs[i] = o.getClass();
//                i++;
//            }
//        }
        /*
        * 蛋疼的事情发生了，，，， 参数类型为基本类型int, getClass()得到Integer, 在这里获取Method就NoSuchMethodException
        *  看看SpringMVC RequestMapping源码。。。。解决办法 将Signature强转为MethodSignature
        * */
//        Method targetMethod = targetClass.getMethod(methodName, clazzs);

        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();

        AuditLog annotation = targetMethod.getAnnotation(AuditLog.class);
        String description = annotation.description();
        String type = annotation.type();

        System.out.println("description : " + description + " ==  type : " + type);
        rtn.put("description", description);
        rtn.put("type", type);
        return rtn;
    }
}
