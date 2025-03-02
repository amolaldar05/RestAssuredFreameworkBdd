package resources;

public enum ApiResources {

	addPlaceAPI("/maps/api/place/add/json"),
	getPlaceAPI("/maps/api/place/get/json"),
	deletePlaceAPI("/maps/api/place/delete/json"),
	logInAPI("/api/ecom/auth/login"),
	addProductAPI("/api/ecom/product/add-product"),
	deleteProductAPI("/api/ecom/product/delete-product/");
	
	private String resource;

	ApiResources(String resource) {
		this.resource=resource;
	}
	
	public String getResource() {
		return resource;
	}
}
