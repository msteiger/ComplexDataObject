package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.ComplexDataContainer;
import data.ComplexDataObject;
import preprocessing.DoubleConverter;
import tools.ComplexDataObjectFactory;

public class PreprocessingTest {
	public static void main(String[] args) {

		ComplexDataObject a = ComplexDataObjectFactory.createObject("Att A", new Double(2.0), "Att B", "1,2");
		ComplexDataObject b = ComplexDataObjectFactory.createObject("Att A", new Double(3.0), "Att B", "3,3");

		List<ComplexDataObject> objects = new ArrayList<>(Arrays.asList(a, b));

		ComplexDataContainer complexDataContainer = new ComplexDataContainer(objects);

		DoubleConverter doubleConverter = new DoubleConverter("Att B", "Double representation of Att B", ',', '.');
		doubleConverter.process(complexDataContainer);

		complexDataContainer.remove("Att B");

		for (ComplexDataObject complexDataObject : complexDataContainer) {
			System.out.println(complexDataObject);
		}
	}
}
