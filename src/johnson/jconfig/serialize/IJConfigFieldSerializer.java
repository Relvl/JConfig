package johnson.jconfig.serialize;

import johnson.jconfig.annotations.JConfigComment;
import johnson.jconfig.annotations.JConfigElement;
import johnson.jconfig.dom.JConfigDomElement;

import java.lang.reflect.Field;

/**
 * @author Johnson on 08.11.2015.
 */
public interface IJConfigFieldSerializer {

	JConfigDomElement serialize(Field field) throws IllegalAccessException;

	/** Создает основу элемента, включающую правильное имя и комментарий. */
	default JConfigDomElement createDomElement(Field field) {
		// Имя элемента
		String elementName = field.getName();
		JConfigElement configAnnotation = field.getAnnotation(JConfigElement.class);
		if (configAnnotation != null) {
			elementName = configAnnotation.value();
		}

		// Новый элемент
		JConfigDomElement resultElement = new JConfigDomElement(elementName);

		// Значение в виде аттрибута
		if (configAnnotation != null) {
			resultElement.setValueIsAttribute(configAnnotation.valueIsAttribute());
		}

		// Комментарий
		JConfigComment commentAnnotation = field.getAnnotation(JConfigComment.class);
		if (commentAnnotation != null) {
			resultElement.setComment(commentAnnotation.value());
		}

		return resultElement;
	}
}
