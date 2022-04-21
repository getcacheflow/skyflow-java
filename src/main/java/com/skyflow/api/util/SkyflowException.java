package com.skyflow.api.util;

import com.skyflow.api.entities.SkyflowError;

public final class SkyflowException extends Exception {
  
    private static final long serialVersionUID = 1L;
  
    private int code;
    private SkyflowError error;

    public SkyflowException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.setCode(errorCode.getCode());
    }

    public SkyflowException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getDescription(), cause);
        this.setCode(errorCode.getCode());
    }

    public SkyflowException(int code, String description) {
        super(description);
        this.setCode(code);
    }

    public SkyflowException(int code, String description, Throwable cause) {
        super(description, cause);
        this.setCode(code);
    }

    public SkyflowException(ErrorCode errorCode, SkyflowError error) {
      super(error.getMessage());
      this.setCode(errorCode.getCode());
      this.setError(error);
    }
    
    public SkyflowException(int code, String description, SkyflowError error) {
        super(description);
        this.setCode(code);        
    }

    public int getCode() {
        return code;
    }

    void setCode(int code) {
        this.code = code;
    }

    public SkyflowError getError() {
      return error;
    }

    public void setError(SkyflowError error) {
      this.error = error;
    }

    
    
}
