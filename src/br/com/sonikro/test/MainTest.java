package br.com.sonikro.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.ChainCommand;
import br.com.sonikro.command.ChainCommandBuilder;
import br.com.sonikro.command.CommandBuilder;
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
	
	public static void main(String[] args) throws Exception{
		MainTest test = new MainTest();

		Integer integerVar = new Integer(10);

		String stringVar = new String("Hello");
		
		Double doubleVar = new Double(55);
		
		CommandBuilder cmdBuilder = new CommandBuilder(test);
		ChainCommandBuilder chainBuilder = new ChainCommandBuilder();
		
		BaseCommand command1 = cmdBuilder.initializeWith(integerVar, stringVar, doubleVar)
										 .setCommandClass(DummyCommand.class)
										 .build();
		
		BaseCommand command2 = cmdBuilder.setCommandClass(SecondDummyCommand.class)
										 .build();
		
		ChainCommand chainCommand = chainBuilder.setListener(test)
											    .add(command1)
											    .add(command2)
											    .build();
		
		chainCommand.dispatch();
		

		List<Object> results = chainCommand.getResultObjectsList();
		System.out.println("Chain Result:"+chainCommand.getResult("result2"));
		System.out.println("Get all results"+results);
	}
}
