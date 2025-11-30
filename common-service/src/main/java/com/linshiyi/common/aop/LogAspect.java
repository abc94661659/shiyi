package com.linshiyi.common.aop;

import com.linshiyi.common.mq.LogProducer;
import com.linshiyi.common.utils.UserContext;
import com.linshiyi.core.annotation.LogOperation;
import com.linshiyi.core.entity.OperationLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogAspect {

    private final LogProducer logProducer;
    private final ExpressionParser parser = new SpelExpressionParser();
    private static final StandardReflectionParameterNameDiscoverer PARAM_DISCOVERER
            = new StandardReflectionParameterNameDiscoverer();

    @Around("@annotation(com.linshiyi.core.annotation.LogOperation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String resultStatus = "SUCCESS";
        String errorMessage = "";

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            resultStatus = "FAIL";
            errorMessage = e.getMessage();
            throw e;
        } finally {
            recordOperationLog(joinPoint, resultStatus, errorMessage);
        }
    }

    private void recordOperationLog(ProceedingJoinPoint joinPoint, String resultStatus, String errorMessage) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            LogOperation logOperation = signature.getMethod().getAnnotation(LogOperation.class);
            if (logOperation == null) {
                return;
            }

            String rawDesc = logOperation.desc();
            String parsedDesc = rawDesc;

            if (rawDesc != null && rawDesc.contains("#{")) { // 检测模板标记
                MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(
                        this,
                        signature.getMethod(),
                        joinPoint.getArgs(),
                        PARAM_DISCOVERER
                );
                try {
                    parsedDesc = parser.parseExpression(rawDesc, ParserContext.TEMPLATE_EXPRESSION)
                            .getValue(context, String.class);
                } catch (Exception e) {
                    log.warn("SpEL 模板解析失败: {}", rawDesc, e);
                    parsedDesc = rawDesc;
                }
            }

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            OperationLog log = new OperationLog();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                log.setRequestUri(request.getRequestURI());
                log.setRequestMethod(request.getMethod());
                log.setIpAddress(getClientIpAddress(request));
                log.setUserAgent(request.getHeader("User-Agent"));
            }

            log.setOperationType(logOperation.operationType());
            log.setOperationDesc(Objects.requireNonNullElse(parsedDesc, ""));
            log.setResourceType(logOperation.resourceType());
            log.setResultStatus(resultStatus);
            log.setErrorMessage(errorMessage);
            log.setUserId(UserContext.getCurrentUserId());
            log.setUserName(UserContext.getCurrentUsername());

            logProducer.sendLog(log);

        } catch (Exception e) {
            log.warn("操作日志记录异常", e);
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xip = request.getHeader("X-Real-IP");
        if (xip != null && !xip.isEmpty() && !"unknown".equalsIgnoreCase(xip)) {
            return xip;
        }
        String xfor = request.getHeader("X-Forwarded-For");
        if (xfor != null && !xfor.isEmpty() && !"unknown".equalsIgnoreCase(xfor)) {
            int index = xfor.indexOf(",");
            return index != -1 ? xfor.substring(0, index).trim() : xfor.trim();
        }
        return request.getRemoteAddr();
    }
}