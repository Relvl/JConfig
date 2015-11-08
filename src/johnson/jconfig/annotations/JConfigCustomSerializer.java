package johnson.jconfig.annotations;

import johnson.jconfig.serialize.IJConfigFieldSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Johnson on 07.11.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface JConfigCustomSerializer {
	Class<? extends IJConfigFieldSerializer> value();
}
