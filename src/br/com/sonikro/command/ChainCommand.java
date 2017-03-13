package br.com.sonikro.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import br.com.sonikro.exceptions.SkommandException;

public class ChainCommand extends BaseCommand{
	@CmdStarterVar @CmdResultVar(name="commands")
	private ArrayList<BaseCommand> commands;
	
	private BaseCommand currentCommand;
	private BaseCommand lastExecutedCommand;
	
	
	@Override
	public void execute() throws Exception {
		Iterator<BaseCommand> iterator = commands.iterator();
		lastExecutedCommand = null;
		while(iterator.hasNext())
		{
			currentCommand = iterator.next();
			if(lastExecutedCommand!=null)
			{
				currentCommand.setStartObjects(lastExecutedCommand.getResultObjects());
			}
			currentCommand.execute();
			lastExecutedCommand = currentCommand;
		}
	}
	
	@Override
	public void rollback(Exception exception) {
		super.rollback(exception);
		ListIterator<BaseCommand> iterator = commands.listIterator(commands.indexOf(currentCommand)+1); 	
		while(iterator.hasPrevious())
		{
			BaseCommand command = iterator.previous();
			command.rollback(exception);
		}
	}
	
	@Override
	public void onSuccess() {
		super.onSuccess();
		Iterator<BaseCommand> iterator = commands.iterator();
		while(iterator.hasNext())
		{
			currentCommand = iterator.next();
			try {
				currentCommand.onSuccess();
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
	
	@Override
	public Object getResult(String name) {
		List<BaseCommand> resultObjects = (List<BaseCommand>) getResultObjectsList().get(0);
		
		ListIterator<BaseCommand> iterator = resultObjects.listIterator(resultObjects.size());
		while(iterator.hasPrevious())
		{
			BaseCommand command = iterator.previous();
			try {
				Object result = command.getResult(name);
				return result;
			} catch (SkommandException e) {
				//Ignore
			}

		}
		
		throw new SkommandException("No result object caled "+name+" inside chain commands");
	}
}
