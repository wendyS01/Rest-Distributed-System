package service.broker;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.core.ClientApplication;
import service.core.ClientInfo;
import service.core.Quotation;


/**
 * Implementation of the broker service that uses the Service Registry.
 *
 * @author Rem
 *
 */
@RestController
public class LocalBrokerService {
    private Map<Long, ClientApplication> cache = new HashMap<>();
    Long id = 0L;
    //save the hostname and port of each service in the urls
    List<String> urls= new ArrayList<String> (Arrays.asList("http://auldfellas:8081","http://girlpower:8082","http" +
                    "://dodgydrivers:8083"));
    //get the clientinfo and loop the urls to request quotation service and save the quotations as arraylist
    @RequestMapping(value="/applications",method= RequestMethod.POST)
    public ResponseEntity<ClientApplication> getQuotations(@RequestBody ClientInfo info) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ClientInfo> quotationRequest = new HttpEntity<ClientInfo>(info);
        ArrayList<Quotation> quotations = new ArrayList();

        for (String url :urls) {
            //send the request to each service and add it in the quotations arraylist
            Quotation quotation = restTemplate.postForObject(url + "/quotations", quotationRequest, Quotation.class);
            quotations.add(quotation);
        }
        //create a ClientApplication instance
        ClientApplication clientApplication = new ClientApplication(++id,info,quotations);
        //put the ClientApplication instance in the cache for future get request
        cache.put(id,clientApplication);

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/applications";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        //return ClientApplication
        return new ResponseEntity<>(clientApplication, headers, HttpStatus.CREATED);
    }
    //get the quotations from 3 services by application number
    @RequestMapping(value="/applications/{applicationNumber}",method=RequestMethod.GET)
    public ResponseEntity<ClientApplication> getApplicationByAppNumber(@PathVariable("applicationNumber") Optional<String> applicationNumber) throws URISyntaxException {
        Long applicationNumberLong;
        ClientApplication clientApplication;
        //check if the applicationNumber has been passed in the method
        if(applicationNumber.isPresent()) {
            //parse the applicationNumber into long variable
            applicationNumberLong = Long.parseLong(applicationNumber.get());
        } else {
            return null;
        }
        //check if the cache contains the key applicationNumberLong
        if(cache.containsKey(applicationNumberLong)) {
            //get the clientApplication corresponding to this cache
            clientApplication = cache.get(applicationNumberLong);
        } else {
            return null;
        }
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/quotations/";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        //return the ClientApplication instance
        return new ResponseEntity<ClientApplication>(clientApplication, headers, HttpStatus.CREATED);
    }
    //get the list of ClientApplication
    @RequestMapping(value="/applications",method=RequestMethod.GET)
    public ResponseEntity<ArrayList<ClientApplication>> getApplications () throws URISyntaxException {
        Long applicationNumberLong;
        //create an arraylist to store the ClientApplication object in cache
        ArrayList<ClientApplication> clientApplications = new ArrayList<>();
        //loop through the caches' value
        for(ClientApplication clientApplication: cache.values()) {
            //add the ClientApplication instance is the arraylist
            clientApplications.add(clientApplication);
        }
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/quotations/";
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));
        //return ArrayList of ClientApplication instances
        return new ResponseEntity<ArrayList<ClientApplication>>(clientApplications, headers, HttpStatus.CREATED);
    }
}
