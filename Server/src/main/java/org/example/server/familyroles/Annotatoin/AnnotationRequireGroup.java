package org.example.server.familyroles.Annotatoin;

import org.example.server.utill.GroupRoles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationRequireGroup {
    String channelIdParam() default "groupId";
    GroupRoles[] requiredPermissions() default {};
    boolean requireWrite() default false;
    boolean requireManage() default false;
}
