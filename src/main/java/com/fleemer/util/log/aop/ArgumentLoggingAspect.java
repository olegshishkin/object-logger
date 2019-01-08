package com.fleemer.util.log.aop;

import com.google.common.collect.ImmutableMap;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * An aspect allows to output to the log any method parameters (in particular complex objects). Output format should be defined by the LogFormat
 * enumeration.
 * Example: someMethod(int arg0, @Log AnyType arg1, boolean arg2, @Log({JSON, T}) AnyType arg3)
 */
@Aspect
@Component
public class ArgumentLoggingAspect {
    private static final String LOG_MSG_TEMPLATE = "Parameter {} {} from method {}.{}():{}{}";
    private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentLoggingAspect.class);
    private static final String NEW_LINE = System.lineSeparator();
    private static final Map<LogFormat, Function<Object, String>> CONVERTERS = ImmutableMap.<LogFormat, Function<Object, String>>builder()
            .put(LogFormat.JSON, o -> new XStream(new JsonHierarchicalStreamDriver()).toXML(o))
            .put(LogFormat.TREE, o -> new ObjectTreeBuilder().showNull().asString(o))
            .put(LogFormat.XML, o -> new XStream().toXML(o))
            .build();

    @Before("execution(* *(.., @com.fleemer.util.log.aop.Log (*), ..)))")
    public void log(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String methodName = method.getName();
        Parameter[] params = method.getParameters();
        Object[] args = joinPoint.getArgs();
        IntStream.range(0, args.length)
                .boxed()
                .collect(Collectors.toMap(i -> params[i], i -> args[i]))
                .forEach((param, arg) -> {
                    if (!param.isAnnotationPresent(Log.class)) {
                        return;
                    }
                    String paramType = param.getType().getSimpleName();
                    String paramName = param.getName();
                    Arrays.stream(param.getAnnotation(Log.class).value())
                            .forEach(logFormat -> {
                                String text = CONVERTERS.get(logFormat).apply(arg);
                                LOGGER.info(LOG_MSG_TEMPLATE, paramType, paramName, className, methodName, NEW_LINE, text);
                            });
                });
    }
}
