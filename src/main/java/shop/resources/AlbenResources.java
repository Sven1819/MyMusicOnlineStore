
package shop.resources;

import com.google.firebase.database.DatabaseReference;
import shop.ShopService;
import shop.model.Album;

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

@Path("/products")
public class AlbenResources{

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Collection<Album> getAlben() {
		return ShopService.alben.values();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response postProduct(Album album, @Context UriInfo uriInfo) {

		// Product is saved with a new generated id, which is returned in the response location URI
		DatabaseReference ref = ShopService.productsRef.push();
		album.albumId = ref.getKey();
		ref.setValueAsync(album);

		URI uri = uriInfo.getAbsolutePathBuilder().path(album.id).build();

		return Response.created(uri).entity(album).build(); // 201
	}

	@Path("{id}")
	public ProductResource getProduct(@PathParam("id") String id) {
		return new ProductResource(id);
	}

}
