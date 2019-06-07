package com.netcracker.edu.varabey.catalog.springutils.beanpostprocessor;

import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Logged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggedAnnotationBeanPostProcessor implements BeanPostProcessor {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final Map<String, Class> annotatedClazzMap = new HashMap<>();

    protected final Delegate delegate = new Delegate();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final Class<?> originalBeanClazz = bean.getClass();
        final Method[] methods = originalBeanClazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Logged.class)) {
                annotatedClazzMap.put(beanName, originalBeanClazz);
                logger.info("Found @{} for bean {} (clazz={}), will enhance with proxy",
                        Logged.class.getSimpleName(), beanName, originalBeanClazz.getSimpleName());
                break;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        final Class originalBeanClazz = annotatedClazzMap.get(beanName);
        if (originalBeanClazz != null) {
            final Class[] originalBeanClazzInterfaces = originalBeanClazz.getInterfaces();
            boolean useDynamicProxy = Arrays.stream(originalBeanClazzInterfaces)
                    .flatMap(i -> Arrays.stream(i.getMethods()))
                    .count() > 0;
            if (useDynamicProxy) {
                logger.info("JDK Dynamic Proxy used to enhance bean {} annotated with @{}.", beanName, Logged.class.getSimpleName());
                return Proxy.newProxyInstance(bean.getClass().getClassLoader(), originalBeanClazzInterfaces,
                        (proxy, method, args) -> {
                            @SuppressWarnings("unchecked") final Method originalMethod = originalBeanClazz.getMethod(method.getName(), method.getParameterTypes());
                            try {
                                if (originalMethod.isAnnotationPresent(Logged.class)) {
                                    Logged log = originalMethod.getAnnotation(Logged.class);
                                    delegate.logBefore(originalBeanClazz, method, log);
                                    Object retVal;
                                    retVal = method.invoke(bean, args);
                                    delegate.logAfter(originalBeanClazz, method, log);
                                    return retVal;
                                } else {
                                    return method.invoke(bean, args);
                                }
                            } catch (InvocationTargetException e) {
                                throw e.getTargetException();
                            }
                        }
                );
            } else {
                logger.info("SpringCGLib (Subclass) used to enhance bean {} annotated with @{}", beanName, Logged.class.getSimpleName());
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(originalBeanClazz);
                enhancer.setInterfaces(originalBeanClazz.getInterfaces());
                enhancer.setCallback((MethodInterceptor) (/*original class*/ o, /*original method*/ method, /*args*/ args,/*proxy method*/ methodProxy) -> {
                    if (method.isAnnotationPresent(Logged.class)) {
                        Logged log = method.getAnnotation(Logged.class);
                        delegate.logBefore(originalBeanClazz, method, log);
                        Object retVal = methodProxy.invokeSuper(o, args);
                        delegate.logAfter(originalBeanClazz, method, log);
                        return retVal;
                    }
                    return methodProxy.invokeSuper(o, args);
                });
                Object cglibProxy = createCGLibProxyInstance(enhancer, originalBeanClazz);
                final Field[] fields = originalBeanClazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object objectToCopy = ReflectionUtils.getField(field, bean);
                    ReflectionUtils.setField(field, cglibProxy, objectToCopy);
                }
                return cglibProxy;
            }
        }
        return bean;
    }

    protected Object createCGLibProxyInstance(Enhancer enhancer, Class<?> originalBeanClazz) {
        final Class[] parameterTypes = originalBeanClazz.getConstructors()[0].getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] == null) {
                parameterTypes[i] = Logged.class;
            }
        }
        if (parameterTypes.length == 0) {
            return enhancer.create();
        } else {
            return enhancer.create(parameterTypes, new Object[parameterTypes.length]);
        }
    }

    protected class Delegate {
        protected final StringBuilder messageBuilder = new StringBuilder();

        protected void logBefore(Class<?> originalBeanClazz, Method method, Logged log) {
            if (log.startFromNewLine()) {
                logger.info("");
            }
            if (log.messageBefore().isEmpty()) {
                return;
            }
            messageBuilder.setLength(0);
            messageBuilder.append(originalBeanClazz.getSimpleName())
                    .append(" -> ")
                    .append(method.getName())
                    .append(" : ")
                    .append(log.messageBefore());
            logMessage(messageBuilder.toString(), log.level());
        }

        protected void logAfter(Class<?> originalBeanClazz, Method method, Logged log) {
            if (log.messageAfter().isEmpty()) {
                return;
            }
            messageBuilder.setLength(0);
            messageBuilder.append(originalBeanClazz.getSimpleName())
                    .append(" -> ")
                    .append(method.getName())
                    .append(" : ")
                    .append(log.messageAfter());
            logMessage(messageBuilder.toString(), log.level());
        }

        protected void logMessage(String message, LogLevel level) {
            switch (level) {
                case WARN:
                    logger.warn(message);
                    break;
                case DEBUG:
                    logger.debug(message);
                    break;
                case ERROR:
                    logger.error(message);
                    break;
                case FATAL:
                    logger.error("FATAL ERROR! message: " + message);
                    break;
                default:
                    logger.info(message);
                    break;
            }
        }
    }
}