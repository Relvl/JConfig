package johnson.jconfig.dom;

import java.io.OutputStream;

/**
 * @author Johnson on 08.11.2015.
 */
@FunctionalInterface
public interface IJConfigDomStreamWriterCallback {
	OutputStream write();
}
