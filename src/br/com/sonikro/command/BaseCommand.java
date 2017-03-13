package br.com.sonikro.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jboss.logging.Logger;

import br.com.sonikro.exceptions.SkommandException;

import java.lang.annotation.Annotation;

public abstract class BaseCommand implements ICommand, IChainCommand {

	protected ICommandListener mListener;
	protected Exception mException;
	protected static Logger logger = Logger.getLogger(BaseCommand.class);

	/*public BaseCommand(ICommandListener listener,Object... starterObjects) {
		this.mListener = listener;
		setStartObjects(starterObjects);
	}*/

	public void dispatch() {
		mListener.onCommand(this);
	}

	@Override
	public void onSuccess() {
		logger.info("Command " + getClass().getName() + " executed with success.");
	}

	@Override
	public void rollback(Exception exception) {
		mException = exception;
		logger.error("Error executing Command " + getClass().getName(), exception);
	}
	
	public boolean hasErrors()
	{
		if(mException!=null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Exception getException()
	{
		return mException;
	}
	
	public void throwException() throws Exception
	{
		if(mException!=null)
		{
			throw mException;
		}
	}

	@Override
	public void setStartObjects(Object... objects) {
		
		Field[] declaredFields = ReflectionTool.getRecursiveClassFields(getClass());
		
		for(Field field : declaredFields)
		{
			if(field.isAnnotationPresent(CmdStarterVar.class))
			{
				CmdStarterVar annotation = (CmdStarterVar) field.getAnnotation(CmdStarterVar.class);
				for(Object starterObject : objects)
				{
					if(starterObject != null && starterObject.getClass().equals(field.getType()))
					{
						try {
							if(!field.isAccessible())
							{
								field.setAccessible(true);
								field.set(this, starterObject);
								field.setAccessible(false);
							}
							else
							{
								field.set(this, starterObject);
							}
							logger.info(field.getType()+ " successfuly injected into command "+getClass().getSimpleName());
						} catch (IllegalArgumentException e) {
							logger.error(e);
						} catch (IllegalAccessException e) {
							logger.error(e);;
						}
					}
				}
			}
	
				
		}
		
		
	}

	@Override
	public Object[] getResultObjects() {
		Field[] declaredFields = ReflectionTool.getRecursiveClassFields(getClass());
		List<Object> resultList = new ArrayList<Object>();
		for(Field field : declaredFields)
		{
			if(field.isAnnotationPresent(CmdResultVar.class))
			{
				CmdResultVar annotation = (CmdResultVar) field.getAnnotation(CmdResultVar.class);
				//logger.info("Command Get Result Objects: Annotation Found at "+field.getType()+" with name "+annotation.name());
				try {
					Object result;
					if(!field.isAccessible())
					{
						field.setAccessible(true);
						result = field.get(this);
						field.setAccessible(false);
					}
					else
					{
						result = field.get(this);
					}
					resultList.add(result);
				} catch (IllegalArgumentException e) {
					logger.error(e);
				} catch (IllegalAccessException e) {
					logger.error(e);
				}		
			}
		}
			
		return resultList.toArray();		
	}
	
	public Object getResult(String name)
	{
		Field[] declaredFields = ReflectionTool.getRecursiveClassFields(getClass());
		
		for(Field field : declaredFields)
		{
			if(field.isAnnotationPresent(CmdResultVar.class))
			{
				CmdResultVar annotation = (CmdResultVar) field.getAnnotation(CmdResultVar.class);
				//logger.info("Command Get Result : Annotation Found at "+field.getType()+" with name "+annotation.name());
				if(annotation.name().equals(name))
				{
					Object result;
					try {
						if(!field.isAccessible())
						{
							field.setAccessible(true);
							result = field.get(this);
							field.setAccessible(false);
						}
						else
						{
							result = field.get(this);
						}
						logger.info("Returning Result ("+annotation.name()+")");
						return result;
					} catch (IllegalArgumentException e) {
						logger.error(e);
					} catch (IllegalAccessException e) {
						logger.error(e);
					}
				}
							
			}
	
		}
		throw new SkommandException("No result object called "+name);
	}
	
	public List<Object> getResultObjectsList()
	{
		return Arrays.asList(getResultObjects());
	}
	@Override
	public String toString() {
		List<Object> results = Arrays.asList(getResultObjects());
		Iterator<Object> iterator = results.iterator();
		StringBuilder sb = new StringBuilder();
		while(iterator.hasNext())
		{
			sb.append(iterator.next().toString());
		}
		return sb.toString();
	}
}
