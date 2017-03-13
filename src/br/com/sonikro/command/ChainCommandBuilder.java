package br.com.sonikro.command;

import java.util.ArrayList;

public class ChainCommandBuilder {
	private ChainCommand chainCommand;
	private ICommandListener mListener;
	private ArrayList<BaseCommand> commands = new ArrayList<BaseCommand>();
	
	public ChainCommandBuilder setListener(ICommandListener listener)
	{
		this.mListener = listener;
		return this;
	}
	
	public ChainCommandBuilder add(BaseCommand command)
	{
		commands.add(command);
		return this;
	}
	
	public ChainCommand build() throws Exception
	{
		
		CommandBuilder builder = new CommandBuilder();
		return (ChainCommand) builder.setCommandClass(ChainCommand.class)
			   .setListener(mListener)
			   .initializeWith(commands)
			   .build();
	}
}
