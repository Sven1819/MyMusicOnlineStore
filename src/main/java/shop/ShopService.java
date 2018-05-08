package shop;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.log4j.BasicConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import shop.model.Album;
import shop.model.Titel;

import java.io.FileInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;



public class ShopService {

    public static Map<String, Titel> titels = new HashMap<>();
    public static Map<String, Album> alben = new HashMap<>();
    
    public static DatabaseReference albenRef;
    public static DatabaseReference titelsRef;

    public static void main(String[] args) throws Exception {
    	
    	BasicConfigurator.configure();
    	Storage storage = StorageOptions.getDefaultInstance().getService();
        // Connect to Firebase database
        FileInputStream serviceAccount = new FileInputStream("8.json");
     //   FirebaseOptions options = new FirebaseOptions.Builder()
         //       .setCredentials(GoogleCredentials.fromStream(serviceAccount))
      //          .setDatabaseUrl("https://mymusiconlinestore.firebaseio.com/").build();
    	FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
    			.setDatabaseUrl("http://mymusiconlinestore.firebaseio.com/").build();

    	FirebaseApp.initializeApp(options);
    	albenRef = FirebaseDatabase.getInstance().getReference("alben");
    	titelsRef = FirebaseDatabase.getInstance().getReference("titels");


        // Register change listener on database
    	albenRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot data, String prevChildKey) {
            		Album a = data.getValue(Album.class);
            		alben.put(a.albumId, a);
            		System.out.println("ALBUM added");
            }

            @Override
            public void onChildChanged(DataSnapshot data, String prevChildKey) {
            		Album a = data.getValue(Album.class);
            		alben.put(a.albumId, a);
            		System.out.println("ALBUM changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot data) {
            		Album a = data.getValue(Album.class);
                    alben.remove(a.albumId);
                    System.out.println("ALBUM removed");
            }

            @Override
            public void onChildMoved(DataSnapshot data, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError error) {}
        });
        titelsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot data, String prevChildKey) {
            		Titel t = data.getValue(Titel.class);
            		titels.put(t.titelId, t);
            		System.out.println("TITEL addded");
            }

            @Override
            public void onChildChanged(DataSnapshot data, String prevChildKey) {

            		Titel t = data.getValue(Titel.class);
            		titels.put(t.titelId, t);
            }

            @Override
            public void onChildRemoved(DataSnapshot data) {
            		Titel t = data.getValue(Titel.class);
                    titels.remove(t.titelId);
                    System.out.println("TITEL removed");
                
            }

            @Override
            public void onChildMoved(DataSnapshot data, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        // Start HTTP server
        ResourceConfig rc = new ResourceConfig().packages("shop");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080"), rc, true);
        System.out.println("Hit enter to stop HTTP server.");
        System.in.read();
        server.shutdownNow();
    }
}

