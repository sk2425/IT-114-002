import java.io.Serializable;
public class PayloadMilestone1 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6625037986217386003L;
	private String message;
	public void setMessage(String s) {
		this.message = s;
	}
	public String getMessage() {
		return this.message;
	}
	
	private PayloadTypeMilestone1 payloadType;
	public void setPayloadType(PayloadTypeMilestone1 pt) {
		this.payloadType = pt;
	}
	public PayloadTypeMilestone1 getPayloadType() {
		return this.payloadType;
	}
	
	private int number;
	public void setNumber(int n) {
		this.number = n;
	}
	public int getNumber() {
		return this.number;
	}
	@Override
	public String toString() {
		return String.format("Type[%s], Number[%s], Message[%s]",
					getPayloadType().toString(), getNumber(), getMessage());
	}
}