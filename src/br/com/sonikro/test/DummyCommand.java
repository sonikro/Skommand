package br.com.sonikro.test;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.CmdResultVar;
import br.com.sonikro.command.CmdStarterVar;
import br.com.sonikro.command.ICommandListener;

public class DummyCommand extends DummySuperCommand {
	@CmdStarterVar
	private Integer mInt;
	@CmdStarterVar
	private String mString;
	
	@CmdResultVar(name="result2")
	private String mResult;
	

	@Override
	public void execute() throws Exception {
		mResult = "(Resultado do DummyCommand)";
	}

}	
