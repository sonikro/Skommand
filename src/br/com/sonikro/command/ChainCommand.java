package br.com.sonikro.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ChainCommand extends BaseCommand{
	@CmdStarterVar @CmdResultVar(name="result")
	private ArrayList<BaseCommand> commands;
	
	private BaseCommand currentCommand;
	private BaseCommand lastExecutedCommand;
	
	public ChainCommand(ICommandListener listener, Object... starterObjects) {
		super(listener, starterObjects);
	}
	
	@Override
	public void execute() throws Exception {
		Iterator<BaseCommand> iterator = commands.iterator();
		lastExecutedCommand = null;
		while(iterator.hasNext())
		{
			currentCommand = iterator.next();
			try {
				if(lastExecutedCommand!=null)
				{
					currentCommand.setStartObjects(lastExecutedCommand.getResultObjects());
				}
				currentCommand.execute();
				lastExecutedCommand = currentCommand;
			} catch (Exception e) {
				rollback(e);
				return;
			}
		}
	}
	
	@Override
	public void rollback(Exception exception) {
		ListIterator<BaseCommand> iterator = commands.listIterator(commands.indexOf(currentCommand)); 	
		while(iterator.hasPrevious())
		{
			BaseCommand command = iterator.previous();
			command.rollback(exception);
		}
	}
	
	@Override
	public void onSuccess() {
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
}
