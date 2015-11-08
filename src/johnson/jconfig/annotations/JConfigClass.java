package johnson.jconfig.annotations;

import johnson.jconfig.EJconfigLoadTarget;
import johnson.jconfig.dom.io.EJConfigDomType;

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
	String fileName();

	/** Типы сериализации конфигурации. */
	EJConfigDomType[] serializeTypes() default {EJConfigDomType.XML};

	/** Цель загрузки конфига. */
	EJconfigLoadTarget target() default EJconfigLoadTarget.FILESYSTEM;

	/** "Человекочитаемый" вывод результата сохранения. */
	boolean isPrettyPrinting() default true;

	/** Вывод результата сохранения так же в System.out */
	boolean systemOut() default false;

}
