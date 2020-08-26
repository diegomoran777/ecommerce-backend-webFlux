package com.webFlux.service;


import org.springframework.http.ResponseEntity;

import com.webFlux.models.Category;
import com.webFlux.models.Product;
import com.webFlux.models.ProductByUserCart;
import com.webFlux.models.Purchase;
import com.webFlux.models.Sale;
import com.webFlux.models.UserProgram;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IService {

	public Flux<String> getAllTypes();
	
	public Flux<String> getTypesByCategory(String categoryId);
	
	public Mono<ResponseEntity<Void>> deleteCategoryById(String id);
	
	public Mono<ResponseEntity<Boolean>> deleteProductById(String id);
	
	public Mono<ResponseEntity<Boolean>> deleteUserById(String id);
	
	public Mono<ResponseEntity<?>> deleteAllProductsCartByUserName(String userName);
	
	public Mono<ResponseEntity<Void>> deleteProductByUserCart(String userName, String productId);
	
	public Mono<ResponseEntity<?>> deleteAllProductsByProductId(String userName, String productId);
	
	public Mono<ResponseEntity<?>> updateUserNameNewOnProductCartList(String userNameNew, String userNameOld);
	
	public Mono<ResponseEntity<?>> updateCategoryNameNewProduct(String categoryNameNew, String categoryNameOld);
	
	public Flux<Product> getProductsByUserCart(String userName);
	
	public Flux<ProductByUserCart> getProductsWithoutAmounts(String userName);
	
	public Mono<ResponseEntity<?>> addProductByUserCart(ProductByUserCart productByUserCart);
	
	public Mono<Boolean> deleteProductsFromCategory(String id);
	
	public Flux<Category> findAllCategories();
	
	public Flux<Category> findByType(String type);
	
	public Flux<Product> findAllProducts();
	
	public Flux<UserProgram> findAllUsers();
	
	public Mono<Boolean> existsUserByName(String userName);
	
	public Mono<Boolean> existsUserByEmail(String email);
	
	public Mono<Category> findCategoryById(String id);
	
	public Mono<Product> findProductById(String id);
	
	public Mono<UserProgram> findUserByUserName(String userName);
	
	public UserProgram findUserByEmail(String email);
	
	public Flux<Product> findProductsByCategoryId(String id);
	
	public Mono<Product> findProductsByCategoryAndProductId(String categoryId,  String id);
	
	public Mono<Category> saveUpdateCategory(Category supplier);
	
	public Mono<Product> saveUpdateProduct(Product product);
	
	public Mono<UserProgram> saveUpdateUser(UserProgram user);
	
	public Flux<Category> getCategoriesByParams(String name, String type);
	
	public Flux<Product> getProductByParamsId(String name, String numberExtern, String type, String id);
	
	public Flux<UserProgram> getUsersByParams(String userName, String role);
	
	public Flux<Purchase> getPurchasesFromUser(String userName);
	
	public Flux<Sale> getSales();
	
	public Mono<ResponseEntity<Void>> deleteSaleById(String id);
	
	public Mono<Sale> saveUpdateSale(Sale sale);
	
	public Flux<Sale> getSalesByParams(String date, String userName, String userMail);
	
	public Mono<ResponseEntity<?>> updateUserNameFromSales(String userNew, String userNameOld);
	
	public Mono<Sale> findSaleBYId(String id);
	
	public Flux<Sale> findByDelivered(boolean delivered);
	
	public Flux<Float> getTotalMonthsByYear(String year);
}
