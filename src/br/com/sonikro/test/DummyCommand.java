package br.com.sonikro.test;

import br.com.sonikro.command.BaseCommand;
import br.com.sonikro.command.CmdResultVar;
import br.com.sonikro.command.CmdStarterVar;
import br.com.sonikro.command.ICommandListener;

public class DummyCommand extends BaseCommand {
	@CmdStarterVar
	private Integer mInt;
	@CmdStarterVar
	private String mString;
	
	@CmdResultVar(name="result")
	private Long mResultLong;
	@CmdResultVar(name="result2")
	private String mOutroResult;
	
	public DummyCommand(ICommandListener listener, Object... starterObjects) {
		super(listener, starterObjects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws Exception {
		System.out.println("Integer: "+mInt+ " , String = "+mString);
		mResultLong = new Long(1);
		mOutroResult = "Outro Resultado";
	}

}	
