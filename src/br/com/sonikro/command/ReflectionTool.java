package br.com.sonikro.command;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ArrayUtils;

public class ReflectionTool {
	public static Field[] getRecursiveClassFields(Class baseClass)
	{
		Field[] fields = baseClass.getDeclaredFields();
		if(baseClass.getSuperclass() != null)
		{
			Field[] superFields = getRecursiveClassFields(baseClass.getSuperclass());
			return ArrayUtils.addAll(fields, superFields);
		}
		
		return fields;
		
	}
}
