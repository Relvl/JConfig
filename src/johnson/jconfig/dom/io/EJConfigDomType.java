package johnson.jconfig.dom.io;

import johnson.jconfig.dom.io.impl.JConfigDomJSONWriter;
import johnson.jconfig.dom.io.impl.JConfigDomXMLWriter;

/**
 * @author Johnson on 08.11.2015.
 */
public enum EJConfigDomType {
	XML(new JConfigDomXMLWriter()),
	JSON(new JConfigDomJSONWriter());

	private final IJConfigDomWriter writer;

	EJConfigDomType(IJConfigDomWriter writer) {this.writer = writer;}

	public IJConfigDomWriter getWriter() {
		return writer;
	}
}
