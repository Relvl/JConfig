package johnson.jconfig.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Johnson on 07.11.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JConfigElement {
	/** Имя переменной. */
	String value();

	/** Записывать ли значение в виде аттрибута элемента? */
	boolean valueIsAttribute() default false;
}
