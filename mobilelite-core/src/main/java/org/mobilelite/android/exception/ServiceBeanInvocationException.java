package org.mobilelite.android.exception;

public class ServiceBeanInvocationException extends Exception {

	private static final long serialVersionUID = 4399817291898515146L;

	public ServiceBeanInvocationException(Throwable e) {
		super(e);
	}

	public ServiceBeanInvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceBeanInvocationException(String message) {
		super(message);
	}
	
}
