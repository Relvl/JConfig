package johnson.jconfig.annotations;

import johnson.jconfig.EJConfigSerializeTypes;
import johnson.jconfig.EJconfigLoadTarget;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Johnson on 07.11.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JConfigClass {
	/** Имя файла или URL загрузки. */
	String value();

	/** Тип сериализации конфигурации. */
	EJConfigSerializeTypes serializeType() default EJConfigSerializeTypes.XML;

	/** Цель загрузки конфига. */
	EJconfigLoadTarget loadTarget() default EJconfigLoadTarget.FILESYSTEM;

}
