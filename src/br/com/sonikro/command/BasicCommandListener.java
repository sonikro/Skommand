package br.com.sonikro.command;

public class BasicCommandListener implements ICommandListener{

	@Override
	public void onCommand(ICommand command) {
		try {
			command.execute();
			command.onSuccess();
		} catch (Exception e) {
			command.rollback(e);
		}
	}

}
