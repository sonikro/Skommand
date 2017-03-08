package br.com.sonikro.test;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.CmdResultVar;
import br.com.sonikro.command.CmdStarterVar;
import br.com.sonikro.command.ICommandListener;

public class SecondDummyCommand extends BaseCommand{
	@CmdStarterVar
	private String mString;
	
	@CmdResultVar(name="result")
	private String mResult;
	
	public SecondDummyCommand(ICommandListener listener, Object... starterObjects) {
		super(listener, starterObjects);
	}

	@Override
	public void execute() throws Exception {
		mResult = "I got "+mString+" from the previous command";
	}

}
