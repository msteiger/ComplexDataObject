package preprocessing;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import data.ComplexDataContainer;
import data.ComplexDataObject;

public class DoubleConverter implements IPreprocessingRoutine {

	String attribute;
	String targetAttribute;

	// NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
	NumberFormat format = null;

	DecimalFormat decimalFormat = null;

	public DoubleConverter(String attribute, String targetAttribute) {
		this.attribute = attribute;
		this.targetAttribute = targetAttribute;
	}

	public DoubleConverter(String attribute, String targetAttribute, NumberFormat format) {
		this.attribute = attribute;
		this.targetAttribute = targetAttribute;

		this.format = format;
	}

	public DoubleConverter(String attribute, String targetAttribute, Locale locale) {
		this.attribute = attribute;
		this.targetAttribute = targetAttribute;

		this.format = NumberFormat.getInstance(locale);
	}

	public DoubleConverter(String attribute, String targetAttribute, Character decimalSeparator, Character thousandsSeparator) {
		this.attribute = attribute;
		this.targetAttribute = targetAttribute;

		decimalFormat = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator(' ');
		decimalFormat.setDecimalFormatSymbols(symbols);
	}

	public DoubleConverter(String attribute, String targetAttribute, Character decimalSeparator) {
		this.attribute = attribute;
		this.targetAttribute = targetAttribute;

		decimalFormat = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		decimalFormat.setDecimalFormatSymbols(symbols);
	}

	@Override
	public void process(ComplexDataContainer container) {

		// add new attribute to schema
		container.add(targetAttribute, Double.class, Double.NaN);

		// parse
		for (ComplexDataObject complexDataObject : container) {
			Double d = Double.NaN;

			if (format != null)
				try {
					Number number = format.parse(complexDataObject.get(attribute).toString());
					d = number.doubleValue();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			else if (decimalFormat != null) {
				try {
					Number number = decimalFormat.parse(complexDataObject.get(attribute).toString());
					d = number.doubleValue();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else
				try {
					d = Double.parseDouble(complexDataObject.get(attribute).toString());
					complexDataObject.add(attribute, d);
				} catch (Exception e) {
					e.printStackTrace();
				}

			complexDataObject.add(targetAttribute, d);
		}
	}
}
