package ua.reverse_geocoding.connection;

/**
 * ISimpleHttpClient
 */

import java.io.IOException;

public interface ISimpleHttpClient {

    public String doHttpGet(String url) throws IOException;

}