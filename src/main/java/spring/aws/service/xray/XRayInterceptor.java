package spring.aws.service.xray;

import com.amazonaws.xray.spring.aop.BaseAbstractXRayInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP with Spring https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-aop-spring.html
 *
 * @author Yuriy Tumakha
 */
@Aspect
@Component
//public class XRayInterceptor extends AbstractXRayInterceptor - requires org.springframework.data.repository.Repository
public class XRayInterceptor extends BaseAbstractXRayInterceptor {


    @Override
    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && bean(*Controller)")
    public void xrayEnabledClasses() {
        // Body is empty as only annotation is used
    }

}
