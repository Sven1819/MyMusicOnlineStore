
package shop.resources;

import com.google.firebase.database.DatabaseReference;
import shop.ShopService;
import shop.model.Titel;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;

@Path("/titels")
public class TitelsResources {

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Collection<Titel> getTitel() {
		return ShopService.titels.values();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response postTitel(Titel titel, @Context UriInfo uriInfo) {

		// Product is saved with a new generated id, which is returned in the response location URI
		DatabaseReference ref = ShopService.titelsRef.push();
		titel.titelId = ref.getKey();
		ref.setValueAsync(titel);

		URI uri = uriInfo.getAbsolutePathBuilder().path(titel.titelId).build();

		return Response.created(uri).entity(titel).build(); // 201
	}

	@Path("{id}")
	public TitelResources getTitel(@PathParam("id") String id) {
		return new TitelResources(id);
	}

}
