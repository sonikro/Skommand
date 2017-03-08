package br.com.sonikro.command;

public interface ICommand {
	void execute() throws Exception;
	void onSuccess();
	void rollback(Exception exception);
}
