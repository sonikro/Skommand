package br.com.sonikro.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.ICommand;
import br.com.sonikro.command.ICommandListener;

public class MainTest implements ICommandListener{

	@Override
	public void onCommand(ICommand command) {
		try {
			command.execute();
			command.onSuccess();
		} catch (Exception e) {
			command.rolback(e);
		}
		
	}
	
	public static void main(String[] args) {
		MainTest test = new MainTest();

		Integer integerVar = new Integer(10);

		String stringVar = new String("Hello");
		
		BaseCommand command = new DummyCommand(test,  integerVar, stringVar);
		command.dispatch();
		
		Long result = (Long) command.getResult("result");
		System.out.println("Get specific result : "+result);
		List<Object> results = Arrays.asList(command.getResultObjects());
		System.out.println("Get all results"+results);
	}
}
