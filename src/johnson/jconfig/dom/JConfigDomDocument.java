package johnson.jconfig.dom;

import johnson.jconfig.dom.io.EJConfigDomType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Johnson on 08.11.2015.
 */
public class JConfigDomDocument {
	private EJConfigDomType[] types = {EJConfigDomType.XML}; // FIXME Позволить добавить кастомный врайтер
	private boolean isPrettyPrint = true;

	private JConfigDomElement rootElement;

	public void setRootElement(JConfigDomElement rootElement) {
		this.rootElement = rootElement;
	}

	/** Устанавливает "красивую" отрисовку (с табуляциями, переносами строк и комментариями). */
	public void setPrettyPrint(boolean isPrettyPrinted) {
		this.isPrettyPrint = isPrettyPrinted;
	}

	public void setTypes(EJConfigDomType... types) {
		this.types = types;
	}

	public void build(String content){

	}



	public void writeToStreams(IJConfigDomStreamWriterCallback callback) throws IOException {
		for (EJConfigDomType type : types) {
			type.getWriter().writeToStream(rootElement, callback.write(), Charset.forName("UTF-8"), isPrettyPrint);
		}
	}

	public void writeToFiles(String fileName) throws IOException {
		for (EJConfigDomType type : types) {
			try (FileOutputStream fos = new FileOutputStream(fileName + "." + type.name().toLowerCase())) {
				type.getWriter().writeToStream(rootElement, fos, Charset.forName("UTF-8"), isPrettyPrint);
			}
		}
	}
}
