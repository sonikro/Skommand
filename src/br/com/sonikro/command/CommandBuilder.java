package br.com.sonikro.command;

public class CommandBuilder {
	private BaseCommand command;
	private Class<? extends BaseCommand> commandClass;
	private Object[] starterObjects;
	private ICommandListener listener;
	
	public CommandBuilder()
	{
		
	}
	
	public CommandBuilder(ICommandListener listener)
	{
		this.listener = listener;
	}
	
	public CommandBuilder setCommandClass(Class<? extends BaseCommand> commandClass)
	{
		this.commandClass = commandClass;
		return this;
	}
	
	public CommandBuilder initializeWith(Object... objects)
	{
		starterObjects = objects;
		return this;
	}
	
	public CommandBuilder setListener(ICommandListener listener)
	{
		this.listener = listener;
		return this;
	}
	
	public BaseCommand build() throws InstantiationException, IllegalAccessException
	{
		command = commandClass.newInstance();
		
		if(starterObjects !=null)
		{
			command.setStartObjects(starterObjects);
		}
		
		command.mListener = listener;
		
		starterObjects = null;
		commandClass = null;
		
		return command;
	}
}
