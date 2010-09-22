package org.springframework.social.core;

/**
 * Indicates an HTTP 403 (Forbidden) response from making a call to a social
 * network's API.
 * 
 * In the case of Twitter, this often means that you are attempting to post a
 * duplicate tweet or have reached an update limit.
 * 
 * @author Craig Walls
 */
public class OperationNotPermittedException extends SocialException {
	private static final long serialVersionUID = 1L;

	public OperationNotPermittedException(String message) {
		super(message);
	}

	public OperationNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}

}