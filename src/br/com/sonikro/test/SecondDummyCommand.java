package br.com.sonikro.test;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.CmdResultVar;
import br.com.sonikro.command.CmdStarterVar;
import br.com.sonikro.command.ICommandListener;

public class SecondDummyCommand extends DummySuperCommand{
	@CmdStarterVar
	private String mString;
	
	@CmdResultVar(name="lobby")
	private String mResult;
	
	@Override
	public void execute() throws Exception {
		mResult = "I got "+mString+" from the previous command";
	}

}
