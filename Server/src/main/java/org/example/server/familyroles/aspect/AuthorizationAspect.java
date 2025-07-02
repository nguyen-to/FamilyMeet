package org.example.server.familyroles.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.server.dto.FamilyMemberDTO;
import org.example.server.exception.FamilyException;
import org.example.server.exception.OwnershipException;
import org.example.server.familyroles.Annotatoin.AnnotationFamily;
import org.example.server.familyroles.Annotatoin.AnnotationRequireGroup;
import org.example.server.familyroles.Annotatoin.AnnotationRequireOwnership;
import org.example.server.familyroles.authentication.AuthenticationService;
import org.example.server.familyroles.authentication.AuthorizationService;
import org.example.server.service.userservice.CustomUserDetails;
import org.example.server.utill.FamilyRoles;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class AuthorizationAspect {
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    @Pointcut("@annotation(annotationFamily)")
    public void familyAccessPointCut(AnnotationFamily annotationFamily){}
    @Pointcut("@annotation(annotationRequireGroup)")
    public void groupAccessPointCut(AnnotationRequireGroup annotationRequireGroup){}
    @Pointcut("@annotation(annotationRequireOwnership)")
    public void ownershipAccessPointCut(AnnotationRequireOwnership annotationRequireOwnership){}

    @Around("familyAccessPointCut(annotationFamily)")
    public Object checkFamilyAccess(ProceedingJoinPoint joinPoint, AnnotationFamily annotationFamily) throws Throwable {
        CustomUserDetails customUserDetails = authenticationService.getUserEntity();
        Long familyId = extractParameterValue(joinPoint, annotationFamily.familyId());
        try{
            FamilyMemberDTO memberDTO = authorizationService.checkFamilyAccess(familyId,customUserDetails.getUser().getId());
            if(annotationFamily.allowOwnerOnly() && memberDTO.getRole() != String.valueOf(FamilyRoles.OWNER)){
                throw new FamilyException("Only family owner can perform this action");
            }
            if(annotationFamily.requiredRoles().length > 0){
                if(!authorizationService.hasRole(familyId,customUserDetails.getUser().getId(),annotationFamily.requiredRoles())){
                    throw new FamilyException("Required roles are not allowed");
                }
            }
            ThreadLocal<FamilyMemberDTO> memberContext = new ThreadLocal<>();
            memberContext.set(memberDTO);

            try {
                return joinPoint.proceed();
            } finally {
                memberContext.remove();
            }
        }catch (FamilyException e){
            throw e;
        }
    }

    @Around("ownershipAccessPointCut(annotationRequireOwnership)")
    public Object checkOwnership(ProceedingJoinPoint joinPoint, AnnotationRequireOwnership annotationRequireOwnership) throws Throwable {
        CustomUserDetails customUserDetails = authenticationService.getUserEntity();
        Long resourceId = extractParameterValue(joinPoint,annotationRequireOwnership.resourceIdParam());
        try{
            if(!authorizationService.isResourceOwner(customUserDetails.getUser().getId(), resourceId,annotationRequireOwnership.resourceType())){
                throw new OwnershipException("User is not the owner of this resource");
            }
            return joinPoint.proceed();
        }catch (OwnershipException e)
        {
            throw e;
        }

    }

    private Long extractParameterValue(ProceedingJoinPoint joinPoint, String paramName) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equals(paramName)) {
                Object arg = args[i];

                if (arg == null) {
                    return null;
                }

                if (arg instanceof Long) {
                    return (Long) arg;
                }

                throw new IllegalArgumentException("Parameter '" + paramName + "' is not of type Long");
            }
        }

        throw new IllegalArgumentException("Parameter '" + paramName + "' not found in method signature");
    }


}
