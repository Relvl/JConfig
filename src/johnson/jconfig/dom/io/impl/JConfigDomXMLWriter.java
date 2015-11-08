package johnson.jconfig.dom.io.impl;

import johnson.jconfig.dom.JConfigDomElement;
import johnson.jconfig.dom.io.IJConfigDomWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author Johnson on 08.11.2015.
 */
public class JConfigDomXMLWriter implements IJConfigDomWriter {
	private static final String XML_VERSION = "1.0";

	@Override
	public String writeToString(JConfigDomElement element, boolean isPrettyPrint, Charset charset) {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"").append(XML_VERSION).append("\" encoding=\"").append(charset.displayName()).append("\"?>");
		if (isPrettyPrint) {
			sb.append("\n");
		}

		collectElementsRecursive(element, sb, 0, isPrettyPrint);
		return sb.toString();
	}

	@Override
	public void writeToStream(JConfigDomElement element, OutputStream stream, Charset charset, boolean isPrettyPrint) throws IOException {
		stream.write(writeToString(element, isPrettyPrint, charset).getBytes(charset));
		stream.flush();
	}

	private void collectElementsRecursive(JConfigDomElement element, StringBuilder sb, int level, boolean isPrettyPrint) {
		if (element == null || sb == null) {
			return;
		}
		String tabs = isPrettyPrint ? getTabStringForLevel(level) : "";
		String newLine = isPrettyPrint ? "\n" : "";
		boolean isShortPrint = element.getElements().isEmpty() && element.isValueAttribute();

		if (isPrettyPrint && element.getComment() != null) {
			sb.append(tabs).append("<!-- ").append(element.getComment()).append(" -->").append(newLine);
		}

		sb.append(tabs).append("<").append(element.getName());

		if (isShortPrint) {
			sb.append(" value=\"").append(element.getValue()).append("\" /");
		}
		sb.append(">");

		if (!isShortPrint) {
			if (element.getElements().isEmpty()) {
				sb.append(element.getValue());
			}
			else {
				sb.append(newLine);
				for (JConfigDomElement subElement : element.getElements()) {
					collectElementsRecursive(subElement, sb, level + 1, isPrettyPrint);
				}
				sb.append(tabs);
			}
			sb.append("</").append(element.getName()).append(">");
		}
		sb.append(newLine);
	}

}
