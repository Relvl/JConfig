package johnson.jconfig.serialize;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Johnson on 08.11.2015.
 */
public class JConfigSerializatorStorage {
	private static final Map<Class<?>, IJConfigClassSerializator> classSerializers = new HashMap<>();
	private static final IJConfigClassSerializator classDefaultSerializer = new JConfigDefaultClassSerializator();

	private static final Map<Class<?>, IJConfigFieldSerializer> fieldSerializers = new HashMap<>();
	private static final IJConfigFieldSerializer fieldDefaultSerializer = new JConfigDefaultFieldSerializator();

	private JConfigSerializatorStorage() {
	}

	public static JConfigSerializatorStorage getInstance() {
		return SingletonHolder.instance;
	}

	// FIXME custom serializers!

	public IJConfigClassSerializator getSerializerForClass(Class<?> clazz) {
		if (!classSerializers.containsKey(clazz)) {
			return classDefaultSerializer;
		}
		return classSerializers.get(clazz);
	}

	public IJConfigFieldSerializer getSerializerForField(Field field) {
		if (!fieldSerializers.containsKey(field.getType())) {
			return fieldDefaultSerializer;
		}
		return fieldSerializers.get(field.getType());
	}

	private static class SingletonHolder {
		private static final JConfigSerializatorStorage instance = new JConfigSerializatorStorage();
	}
	
}
