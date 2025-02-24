import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbGlobalMap;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;

public class Access_Global_Cache_JavaCompute extends MbJavaComputeNode {

    public void evaluate(MbMessageAssembly inAssembly) throws MbException {
        MbOutputTerminal out = getOutputTerminal("out");

        MbMessage inMessage = inAssembly.getMessage();
        MbMessageAssembly outAssembly = null;
        try {
            // Create a new message without copying the input to avoid duplicating the <Employee> block.
            MbMessage outMessage = new MbMessage();
            outAssembly = new MbMessageAssembly(inAssembly, outMessage);
            
            // ----------------------------------------------------------
            // Extract the input ID
            MbElement InputRoot = inMessage.getRootElement().getLastChild();
            MbElement InObj = InputRoot.getFirstElementByPath("Employee");
            String ID = InObj.getFirstElementByPath("ID").getValueAsString();
            String val = null;
            
            // Retrieve the value from global cache
            MbGlobalMap map = MbGlobalMap.getGlobalMap("MyMap");
            val = (String) map.get(ID);

            // Create the output XML structure
            MbElement outputRoot = outMessage.getRootElement().createElementAsLastChild(MbXMLNSC.PARSER_NAME); 
            MbElement OutResp = outputRoot.createElementAsFirstChild(MbXMLNSC.FOLDER, "Response", null);
            OutResp.createElementAsFirstChild(MbXMLNSC.FIELD, "ID", ID);
            OutResp.createElementAsFirstChild(MbXMLNSC.FIELD, "Name", val);
            
            // ----------------------------------------------------------
        } catch (MbException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // Re-throw exception for further handling
            throw new MbUserException(this, "evaluate()", "", "", e.toString(), null);
        }
        // Propagate the newly created message to the 'out' terminal
        out.propagate(outAssembly);
    }

    // onPreSetupValidation method for validation
    @Override
    public void onPreSetupValidation() throws MbException {
    }

    // onSetup method for initialization tasks
    @Override
    public void onSetup() throws MbException {
    }

    // onStart method called when the message flow is started
    @Override
    public void onStart() throws MbException {
    }

    // onStop method called when the message flow is stopped
    @Override
    public void onStop(boolean wait) throws MbException {
    }

    // onTearDown method to release any cached data or endpoints
    @Override
    public void onTearDown() throws MbException {
    }
}
