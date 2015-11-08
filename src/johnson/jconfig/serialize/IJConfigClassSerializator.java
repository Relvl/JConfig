package johnson.jconfig.serialize;

import johnson.jconfig.dom.JConfigDomElement;

/**
 * @author Johnson on 08.11.2015.
 */
public interface IJConfigClassSerializator {

	JConfigDomElement serialize(Class<?> clazz) throws IllegalAccessException;
}
