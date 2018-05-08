package shop.resources;

import com.google.firebase.database.DatabaseReference;
import shop.ShopService;
import shop.model.Product;

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

public class ProductResource {

	String id;

	public ProductResource(String id) {
		this.id = id;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getProduct() {
		Product product = ShopService.products.get(id);
		if (product == null) {
			return Response.status(404).build(); // 404
		}
		return Response.ok(product).build();
	}

	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response putProduct(Product product, @Context UriInfo uriInfo) {
		
		boolean exists = ShopService.products.get(id) != null;
		if (!exists) {
			return Response.status(404).build(); // 404
		} else {
			product.id = id;
			ShopService.productsRef.child(id).setValueAsync(product);
			return Response.noContent().build(); // 204
		}
	}

	@DELETE
	public Response deleteProduct() {
		// Delete product with id provided in the URI
		ShopService.productsRef.child(id).removeValueAsync();
		return Response.noContent().build(); // 204
	}

}
