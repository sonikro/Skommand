package br.com.sonikro.test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.BasicCommandListener;
import br.com.sonikro.command.ReflectionTool;

public class TestReflectionTool{

	@Test
	public void testRecursiveFields() {
		/*BaseCommand command = new DummyCommand(new BasicCommandListener(), new Double(99), new Integer(10), new String("Hello"));
		command.dispatch();*/
		Field[] fields = ReflectionTool.getRecursiveClassFields(DummyCommand.class);
		System.out.println("Fields for "+DummyCommand.class);
		for(Field field : fields)
		{
			System.out.println(field.getType()+" "+field.getName());
		}
		
	}

}
