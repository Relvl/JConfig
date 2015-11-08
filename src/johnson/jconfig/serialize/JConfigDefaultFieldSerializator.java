package johnson.jconfig.serialize;

import johnson.jconfig.annotations.JConfigIgnore;
import johnson.jconfig.dom.JConfigDomElement;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Johnson on 08.11.2015.
 */
public class JConfigDefaultFieldSerializator implements IJConfigFieldSerializer {

	private static boolean chechField(Field field) {
		int mod = field.getModifiers();
		boolean check = Modifier.isPublic(mod);
		check &= Modifier.isStatic(mod);
		check &= !Modifier.isTransient(mod);
		check &= !Modifier.isFinal(mod);

		check &= field.getAnnotation(JConfigIgnore.class) == null;

		return check;
	}

	@Override
	public JConfigDomElement serialize(Field field) throws IllegalAccessException {
		field.setAccessible(true);
		if (!chechField(field)) {
			return null;
		}

		// Новый элемент
		JConfigDomElement resultElement = createDomElement(field);
		if (Number.class.isAssignableFrom(field.getType())) {
			resultElement.setValueIsNumber(true);
		}

		// FIXME базовые классы и плавающая точка
		resultElement.setValue(field.get(null).toString());
		return resultElement;
	}
}
