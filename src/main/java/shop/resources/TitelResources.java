package shop.resources;

import com.google.firebase.database.DatabaseReference;
import shop.ShopService;
import shop.model.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class TitelResources {

	String id;

	public TitelResources(String id) {
		this.id = id;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getTitel() {
		Titel product = ShopService.titels.get(id);
		if (product == null) {
			return Response.status(404).build(); // 404
		}
		return Response.ok(product).build();
	}

	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response putTitel(Titel titel, @Context UriInfo uriInfo) {
		
		boolean exists = ShopService.titels.get(id) != null;
		if (!exists) {
			return Response.status(404).build(); // 404
		} else {
			titel.titelId = id;
			ShopService.titelsRef.child(id).setValueAsync(titel);
			return Response.noContent().build(); // 204
		}
	}

	@DELETE
	public Response deleteTitel() {
		// Delete product with id provided in the URI
		ShopService.titelsRef.child(id).removeValueAsync();
		return Response.noContent().build(); // 204
	}
}
