package johnson.jconfig.dom.io.impl;

import johnson.jconfig.dom.JConfigDomElement;
import johnson.jconfig.dom.io.IJConfigDomWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author Johnson on 08.11.2015.
 */
public class JConfigDomJSONWriter implements IJConfigDomWriter {

	private static boolean generateValidJsonOnly = false;

	public static void setGenerateValidJsonOnly(boolean generateValidJsonOnly) {
		JConfigDomJSONWriter.generateValidJsonOnly = generateValidJsonOnly;
	}

	@Override
	public String writeToString(JConfigDomElement element, boolean isPrettyPrint, Charset charset) {
		StringBuilder sb = new StringBuilder("{").append(isPrettyPrint ? "\n" : "");
		collectElementsRecursive(element, sb, 1, isPrettyPrint);
		sb.append(isPrettyPrint ? "\n" : "").append("}");
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

		// Комментарий. Экспериментальная функция, стандарт JSON не разрешает их!
		if (isPrettyPrint && element.getComment() != null && !generateValidJsonOnly) {
			sb.append(tabs).append("/* ").append(element.getComment()).append("*/").append(newLine);
		}

		// Имя элемента
		sb.append(tabs).append("\"").append(element.getName()).append("\": ");

		// Значение (если односложное)
		if (element.getElements().isEmpty()) {
			sb.append(element.isValueIsNumber() ? "" : "\"").append(element.getValue()).append(element.isValueIsNumber() ? "" : "\"");
		}

		// Значение (если есть вложенные элементы)
		else {
			sb.append("{").append(newLine);

			for (int i = 0; i < element.getElements().size(); i++) {
				collectElementsRecursive(element.getElements().get(i), sb, level + 1, isPrettyPrint);
				// Если это не последний элемент - обязательно запятую
				if (i < element.getElements().size() - 1) {
					sb.append(",");
				}
				sb.append(newLine);
			}

			sb.append(tabs).append("}");
		}
	}
}
