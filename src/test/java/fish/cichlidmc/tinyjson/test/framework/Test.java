package fish.cichlidmc.tinyjson.test.framework;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
	String file() default "";
	boolean fails() default false;
}
