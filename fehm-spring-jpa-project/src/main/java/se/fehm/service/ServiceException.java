package se.fehm.service;

public final class ServiceException extends Exception{

	private static final long serialVersionUID = 6494393679691663773L;

	public ServiceException(String message, Throwable e) {
		super(message, e);
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
