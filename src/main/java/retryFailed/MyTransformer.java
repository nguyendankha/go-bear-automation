package retryFailed;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyTransformer implements IAnnotationTransformer
{
	//This can be used to Apply Retry Logic during Run-Time.
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod)
	{
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
