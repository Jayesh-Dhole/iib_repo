import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class JDBC_JCN_JavaCompute extends MbJavaComputeNode {

    public void evaluate(MbMessageAssembly inAssembly) throws MbException {
        MbOutputTerminal out = getOutputTerminal("out");

        MbMessage inMessage = inAssembly.getMessage();
        MbMessage outMessage = new MbMessage(); // Create a new output message
        MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly, outMessage);

        try {
            // Add the root element to the output message (using XMLNSC parser)
            MbElement outRoot = outMessage.getRootElement().createElementAsLastChild("XMLNSC");
            MbElement recordsElement = outRoot.createElementAsLastChild(MbElement.TYPE_NAME, "Records", null);

            // Establish the JDBC connection using the connection policy
            Connection conn = getJDBCType4Connection("{JCN_Policy}:jcn", JDBC_TransactionType.MB_TRANSACTION_AUTO);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT NAME, ADDRESS FROM Emp");

            // Iterate over the result set
            while (rs.next()) {
                String name = rs.getString("NAME");
                String address = rs.getString("Address");

                // Create a new Record element for each row
                MbElement recordElement = recordsElement.createElementAsLastChild(MbElement.TYPE_NAME, "Record", null);

                // Add Name and Address to each Record
                recordElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Name", name);
                recordElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Address", address);
            }

            // Close the ResultSet and Statement
            rs.close();
            stmt.close();
            conn.close(); // Close the database connection

        } catch (Exception e) {
            throw new MbUserException(this, "evaluate()", "", "", e.toString(), null);
        }

        // Propagate the output message to the next node
        out.propagate(outAssembly);
    }

    @Override
    public void onPreSetupValidation() throws MbException {
    }

    @Override
    public void onSetup() throws MbException {
    }

    @Override
    public void onStart() throws MbException {
    }

    @Override
    public void onStop(boolean wait) throws MbException {
    }

    @Override
    public void onTearDown() throws MbException {
    }
}
