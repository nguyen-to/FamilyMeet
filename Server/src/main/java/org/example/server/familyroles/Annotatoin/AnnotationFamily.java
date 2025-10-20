package org.example.server.familyroles.Annotatoin;

import org.example.server.utill.FamilyRoles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationFamily {
    String familyId() default "familyId";
    FamilyRoles[] requiredRoles() default {};
    boolean allowOwnerOnly() default false;
}

