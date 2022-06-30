package com.wuzx.operate.aspect;

import com.wuzx.operate.annotation.OperationLogging;
import com.wuzx.operate.handler.OperationLogHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;


/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AccessLogAutoConfiguration.java
 * @Description 操作日志切面类
 * @createTime 2022年06月06日 06:06:00
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class OperationLogAspect<T> {

	private final OperationLogHandler<T> operationLogHandler;

	@Around("execution(@(@com.yh.operate.annotation.OperationLogging *) * *(..)) "
			+ "|| @annotation(com.yh.operate.annotation.OperationLogging)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 开始时间
		long startTime = System.currentTimeMillis();

		// 获取目标方法
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		// 获取操作日志注解 备注： AnnotationUtils.findAnnotation 方法无法获取继承的属性
		OperationLogging operationLogging = AnnotatedElementUtils.findMergedAnnotation(method, OperationLogging.class);

		// 获取操作日志 DTO
		Assert.notNull(operationLogging, "operationLogging annotation must not be null!");

		T operationLog = operationLogHandler.buildLog(operationLogging, joinPoint);

		Throwable throwable = null;
		Object result = null;
		try {
			result = joinPoint.proceed();
			return result;
		}
		catch (Throwable e) {
			throwable = e;
			throw e;
		}
		finally {
			// 是否保存响应内容
			boolean isSaveResult = operationLogging.recordResult();
			// 操作日志记录处理
			handleLog(joinPoint, startTime, operationLog, throwable, isSaveResult, result);
		}
	}

	private void handleLog(ProceedingJoinPoint joinPoint, long startTime, T operationLog, Throwable throwable,
			boolean isSaveResult, Object result) {
		try {
			// 结束时间
			long executionTime = System.currentTimeMillis() - startTime;
			// 记录执行信息
			operationLogHandler.recordExecutionInfo(operationLog, joinPoint, executionTime, throwable, isSaveResult,
					result);
			// 处理操作日志
			operationLogHandler.handleLog(operationLog);
		}
		catch (Exception e) {
			log.error("记录操作日志异常：{}", operationLog);
		}
	}

}
