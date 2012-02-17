package org.mobilelite.android;

public class ExecuteAttempt {
	private boolean skipExecute = false;
	private Object data;

	public boolean isSkipExecute() {
		return skipExecute;
	}

	public void setSkipExecute(boolean skipExecute) {
		this.skipExecute = skipExecute;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
