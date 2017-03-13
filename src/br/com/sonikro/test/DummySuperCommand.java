package br.com.sonikro.test;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.CmdResultVar;
import br.com.sonikro.command.CmdStarterVar;
import br.com.sonikro.command.ICommandListener;

public abstract class DummySuperCommand extends BaseCommand{
	
	@CmdStarterVar @CmdResultVar(name="superDouble")
	private Double mDouble;
	
	
	
}
