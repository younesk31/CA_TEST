package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MapDTO;
import dtos.UserDTO;
import entities.MapInfo;
import entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import facades.MapFacade;
import facades.UserFacade;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.persistence.annotations.CompositeMember;
import utils.EMF_Creator;
import utils.HttpUtils;

@Path("info")
public class DemoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;
    private UserFacade facade = UserFacade.getUserFacade(EMF);
    private MapFacade mapFacade = MapFacade.getMapFacade(EMF);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Path("randomuser")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRandomUser() throws IOException {
        String catFact = HttpUtils.fetchData("https://randomuser.me/api/");
        return catFact;
    }

    @GET
    @Path("crypto")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCrypto() throws IOException {
        String catFact = HttpUtils.fetchData("https://api.coindesk.com/v1/bpi/currentprice.json");
        return catFact;
    }

    @GET
    @Path("catfact")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCatFacts() throws IOException {
        String catFact = HttpUtils.fetchData("https://catfact.ninja/fact");
        return catFact;
    }

    // 406 for URL lortet virker ikke, aner det ikke mere
//    @GET
//    @Path("apex")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getApex() throws IOException {
//        String apex_json = HttpUtils.fetchData("https://api.mozambiquehe.re/maprotation?version=2&auth=neoWHFfqrUVApKdUZXUe");
//        System.out.println(apex_json);
//
////        System.out.println(gson.toJson(apex_json));
////        MapDTO mapDTO = gson.fromJson(apex_json, MapDTO.class);
////        mapDTO = mapFacade.getMapInfo(mapDTO);
//
//        return apex_json;
//    }


    String thisuserrole = "";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        if (securityContext.isUserInRole("user")) {
            thisuserrole = "user";
        }
        return "{\"msg\": \"Hello: " + thisuser + "   -   Role: " + thisuserrole + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        if (securityContext.isUserInRole("admin")) {
            thisuserrole = "admin";
        }
        return "{\"msg\": \"Hello: " + thisuser + "   -   Role: " + thisuserrole + "\"}";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin/createUser")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(String newUser) {
        UserDTO userDTO = gson.fromJson(newUser, UserDTO.class);
        userDTO = facade.createUser(userDTO);

        return gson.toJson(userDTO);
    }

    @DELETE
    @Path("admin/deleteUser/{name}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("name") String name) {
        UserDTO userDTO = facade.deleteUser(name);

        return "Deleted user " + gson.toJson(userDTO);
    }
}