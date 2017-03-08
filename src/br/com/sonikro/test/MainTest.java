package br.com.sonikro.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.ChainCommand;
import br.com.sonikro.command.ICommand;
import br.com.sonikro.command.ICommandListener;

public class MainTest implements ICommandListener{

	@Override
	public void onCommand(ICommand command) {
		try {
			command.execute();
			command.onSuccess();
		} catch (Exception e) {
			command.rollback(e);
		}
		
	}
	
	public static void main(String[] args) {
		MainTest test = new MainTest();

		Integer integerVar = new Integer(10);

		String stringVar = new String("Hello");
		
		ArrayList<BaseCommand> listOfCommands = new ArrayList<BaseCommand>();
		
		listOfCommands.add(new DummyCommand(test,  integerVar, stringVar));
		listOfCommands.add(new SecondDummyCommand(test));
		
		BaseCommand chainCommand = new ChainCommand(test, listOfCommands);
		chainCommand.dispatch();
		

		List<Object> results = Arrays.asList(chainCommand.getResultObjects());
		System.out.println("Get all results"+results);
	}
}
