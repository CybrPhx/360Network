package DiagramParser;

public class UnconnectedNodeException extends Exception
{

	private static final long serialVersionUID = 1L;

	public UnconnectedNodeException(String message) {
		super(message);
	}
}