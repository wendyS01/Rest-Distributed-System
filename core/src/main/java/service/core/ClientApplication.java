package service.core;

import java.util.ArrayList;
/**
*Store applicationNumber, clientInfo Object, lists of quotations in ClientApplication
 * Java Bean of ClientApplication
* */
public class ClientApplication {
    private long applicationNumber;
    private ClientInfo clientInfo;
    private ArrayList<Quotation> quotations;

    public ClientApplication(Long applicationNumber,ClientInfo clientInfo,ArrayList<Quotation> quotations) {
        this.applicationNumber = applicationNumber;
        this.clientInfo = clientInfo;
        this.quotations = quotations;
    }

    public ClientApplication() {}

    public long getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(long applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public ArrayList<Quotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(ArrayList<Quotation> quotations) {
        this.quotations = quotations;
    }
}
