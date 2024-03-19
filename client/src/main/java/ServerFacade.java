import Model.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.util.List;

public class ServerFacade {
    private final String serverURL;

    public ServerFacade(String url){
        serverURL = url;
    }

    public Auth register(User user) throws ResponseException{
        var path = "/user";
        return this.makeRequest("POST", path, user, Auth.class);
    }

    public Auth login(User user) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, user, Auth.class);
    }

    public List<Game> list(String authToken) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, authToken, List.class);
    }

    public Game create(String authToken) throws ResponseException {
        var path = "/game";
        return this.makeRequest("POST", path, authToken, Game.class);
    }



    public <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try{
            URL uri = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection)  uri.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        }catch(Exception e){
            throw new ResponseException(500, e.getMessage());
        }
    }
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }
    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException{
        T response = null;
        if (http.getContentLength() > 0){
            try(InputStream respBody = http.getInputStream()){
                InputStreamReader reader = new InputStreamReader(respBody);
                if(responseClass != null){
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }
    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if(!isSuccessful(status)) {
            throw new ResponseException(status, "Error: Couldn't connect to server. " + status);
        }
    }
    private boolean isSuccessful(int statusCode) {
        return (statusCode/100==2);
    }
}
