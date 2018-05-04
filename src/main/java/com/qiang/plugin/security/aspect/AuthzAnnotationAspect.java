package com.qiang.plugin.security.aspect;

import annotation.Aspect;
import annotation.Controller;
import com.qiang.plugin.security.annotation.User;
import com.qiang.plugin.security.exception.AuthzException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import proxy.AspectProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 授权注解切面
 * Created by liq on 2018/4/26.
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy {
    /**
     * 定义一个基于授权功能的注解类数组
     */
    private static final Class[] ANNOTATION_CLASS_ARRAY = {User.class};

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        //从目标类和目标方法中获取相应的注解
        Annotation annotation = getAnnotation(cls, method);
        if (annotation != null){
            //判断授权注解的类型，执行相应判断
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(User.class)){
                handleUser();
            }
        }
    }

    private Annotation getAnnotation(Class<?> cls, Method method) {
        //遍历所有的授权类型的注解
        for(Class<? extends Annotation> annotationClass : ANNOTATION_CLASS_ARRAY){
            //目标方法是否带有授权注解
            if (method.isAnnotationPresent(annotationClass)){
                return method.getAnnotation(annotationClass);
            }
            //目标类是否带有授权注解
            if (cls.isAnnotationPresent(annotationClass)){
                return cls.getAnnotation(annotationClass);
            }
        }
        return null;
    }

    private void handleUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null){
            PrincipalCollection principals = currentUser.getPrincipals();
            if (principals == null || principals.isEmpty()){
                throw new AuthzException("当前用户尚未登录");
            }
        }
    }
}
