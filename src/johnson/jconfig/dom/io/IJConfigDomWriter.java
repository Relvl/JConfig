package johnson.jconfig.dom.io;

import johnson.jconfig.dom.JConfigDomElement;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author Johnson on 08.11.2015.
 */
public interface IJConfigDomWriter {

	String writeToString(JConfigDomElement element, boolean isPrettyPrint, Charset charset);

	void writeToStream(JConfigDomElement element, OutputStream stream, Charset charset, boolean isPrettyPrint) throws IOException;

	default String getTabStringForLevel(int level) {
		String result = "";
		for (int i = 0; i < level; i++) {
			result += "\t";
		}
		return result;
	}
}
