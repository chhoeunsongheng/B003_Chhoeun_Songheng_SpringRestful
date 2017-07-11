package com.hrd.spring.entities.responses.failure;

import java.util.Collections;
import com.hrd.spring.entities.responses.ResponseHttpStatus;
import com.hrd.spring.entities.responses.ResponseList;

/**
 * 
 * @author Tola
 *	Created Date: 2017/07/03
 *
 */
public  class ResponseListFailure<T> extends ResponseList<T> {
	private Error error;
	
	public ResponseListFailure(String message, boolean status, ResponseHttpStatus error) {
		super.setMessage(message);
		super.setStatus(status);
		super.setData(Collections.emptyList());
		this.setError(new Error(error));
	}

	
	public static class Error {

		private int code;
		private String message;
		
		public Error(ResponseHttpStatus status) {
			super();
			this.code = status.value();
			this.message = status.getReasonPhrase();
		}

		public int getCode() {
			return code;
		}
		
		public void setCode(int code) {
			this.code = code;
		}
		
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
	}
	
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
