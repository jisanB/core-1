package com.dotcms.rest.api.v1.system.ruleengine;

import com.dotcms.TestBase;
import com.dotcms.repackage.javax.ws.rs.client.Client;
import com.dotcms.repackage.javax.ws.rs.client.Entity;
import com.dotcms.repackage.javax.ws.rs.client.WebTarget;
import com.dotcms.repackage.javax.ws.rs.core.MediaType;
import com.dotcms.repackage.javax.ws.rs.core.Response;
import com.dotcms.repackage.org.apache.commons.httpclient.HttpStatus;
import com.dotcms.repackage.org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import com.dotcms.repackage.org.junit.Test;
import com.dotcms.rest.RestClientBuilder;
import com.dotmarketing.beans.Host;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.portlets.contentlet.business.HostAPI;
import com.dotmarketing.servlets.test.ServletTestRunner;
import com.dotmarketing.util.json.JSONException;
import com.liferay.portal.model.User;

import javax.servlet.http.HttpServletRequest;

import static com.dotcms.repackage.org.junit.Assert.assertTrue;

/**
 * Created by Oscar Arrieta on 9/21/15.
 *
 * Use this test class to write tests against Actionlets Rest endpoint.
 */
public class ActionletResourceTest extends TestBase {

    private HttpServletRequest request;
    private String serverName;
    private Integer serverPort;
    private User user;
    Host defaultHost;
    Client client;

    public ActionletResourceTest(){
        request = ServletTestRunner.localRequest.get();
        serverName = request.getServerName();
        serverPort = request.getServerPort();
        HostAPI hostAPI = APILocator.getHostAPI();

        //Setting the test user
        try{
            user = APILocator.getUserAPI().getSystemUser();
            defaultHost = hostAPI.findDefaultHost(user, false);
        }catch(DotDataException dd){
            dd.printStackTrace();
        }catch(DotSecurityException ds){
            ds.printStackTrace();
        }

        client = RestClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin@dotcms.com", "admin");
        client.register(feature);
    }

    /**
     * For now is just testing the endpoint /api/v1/system/ruleengine/actionlets/ exists and returns 200(OK) status.
     * TODO: after create actionlet endpoint is developed, can test creating and retrieving specific actionlet.
     * @throws JSONException
     */
    @Test
    public void testListAllActionlets() throws JSONException {
        //Client Call.
        WebTarget target = client.target("http://" + serverName + ":" + serverPort + "/api/v1");
        //Response.
        Response response = target.path("/system/ruleengine/actionlets/").request(MediaType.APPLICATION_JSON_TYPE).get();

        //Test if the endpoint exists and returns 200(OK) status.
        assertTrue(response.getStatus() == HttpStatus.SC_OK);
    }
}