package br.com.sonikro.command;

public interface IChainCommand {
	void setStartObjects(Object... object);
	Object[] getResultObjects();
}
