package johnson.jconfig.serialize;

import johnson.jconfig.annotations.JConfigComment;
import johnson.jconfig.annotations.JConfigElement;
import johnson.jconfig.annotations.JConfigIgnore;
import johnson.jconfig.dom.JConfigDomElement;
import johnson.jconfig.exceptions.JConfigRException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Johnson on 08.11.2015.
 */
public class JConfigDefaultClassSerializator implements IJConfigClassSerializator {

	private static boolean checkInternalClass(Class<?> clazz) {
		int mod = clazz.getModifiers();
		boolean check = Modifier.isPublic(mod);
		check &= Modifier.isStatic(mod);
		check &= !Modifier.isTransient(mod);
		check &= !Modifier.isFinal(mod);

		check &= clazz.getAnnotation(JConfigIgnore.class) == null;

		return check;
	}

	@Override
	public JConfigDomElement serialize(Class<?> clazz) throws IllegalAccessException {
		if (clazz == null) {
			throw new JConfigRException("Class cannot be null");
		}

		// Имя элемента
		String elementName = clazz.getSimpleName();
		JConfigElement configAnnotation = clazz.getAnnotation(JConfigElement.class);
		if (configAnnotation != null) {
			elementName = configAnnotation.value();
		}

		// Новый элемент
		JConfigDomElement resultElement = new JConfigDomElement(elementName);

		// Комментарий
		JConfigComment commentAnnotation = clazz.getAnnotation(JConfigComment.class);
		if (commentAnnotation != null) {
			resultElement.setComment(commentAnnotation.value());
		}

		// Пробегаем по полям класса и добавляем подходящие к родителю
		IJConfigFieldSerializer fieldSerializer;
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			fieldSerializer = JConfigSerializatorStorage.getInstance().getSerializerForField(field);
			if (fieldSerializer != null) {
				JConfigDomElement fieldElement = fieldSerializer.serialize(field);
				if (fieldElement != null) {
					resultElement.addElement(fieldElement);
				}
			}
		}

		// Пробегаем по внутренним классам и добавляем подходящие к родителю
		IJConfigClassSerializator classSerializer;
		for (Class<?> internalClass : clazz.getDeclaredClasses()) {
			if (checkInternalClass(internalClass)) {
				classSerializer = JConfigSerializatorStorage.getInstance().getSerializerForClass(internalClass);
				if (classSerializer != null) {
					JConfigDomElement classElement = classSerializer.serialize(internalClass);
					resultElement.addElement(classElement);
				}
			}
		}

		return resultElement;
	}
}
