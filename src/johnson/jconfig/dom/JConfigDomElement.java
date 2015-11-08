package johnson.jconfig.dom;

import johnson.jconfig.exceptions.JConfigRException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Johnson on 08.11.2015.
 */
public class JConfigDomElement {
	private final List<JConfigDomElement> elements = new ArrayList<>();
	private final String name;

	private String comment = null;
	private String value = null;
	private boolean valueIsNumber = false;
	private boolean valueIsAttribute = false;

	public JConfigDomElement(String name) {
		this.name = name;
	}

	public boolean isValueIsNumber() {
		return valueIsNumber;
	}

	public void setValueIsNumber(boolean valueIsNumber) {
		this.valueIsNumber = valueIsNumber;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void addElement(JConfigDomElement element) {
		elements.add(element);
		valueIsAttribute = false;
		value = null;
	}

	public List<JConfigDomElement> getElements() {
		return elements;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (!elements.isEmpty()) {
			throw new JConfigRException("Cannot set value to element with subelements");
		}
		this.value = value;
	}

	public boolean isValueAttribute() {
		return valueIsAttribute;
	}

	public void setValueIsAttribute(boolean valueIsAttribute) {
		if (!elements.isEmpty()) {
			throw new JConfigRException("Cannot set value attribute to element with subelements");
		}
		this.valueIsAttribute = valueIsAttribute;
	}
}
