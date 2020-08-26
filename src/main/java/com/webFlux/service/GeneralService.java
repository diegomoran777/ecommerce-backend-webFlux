package com.webFlux.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webFlux.models.Category;
import com.webFlux.models.Product;
import com.webFlux.models.ProductByUserCart;
import com.webFlux.models.Purchase;
import com.webFlux.models.Sale;
import com.webFlux.models.UserProgram;
import com.webFlux.repository.ICategoryRepository;
import com.webFlux.repository.IProductByUserCartRepository;
import com.webFlux.repository.IProductRepository;
import com.webFlux.repository.IPurchaseRepository;
import com.webFlux.repository.ISaleRepository;
import com.webFlux.repository.IUserProgramRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



/**
 * Class that manages the functions of the repositories 
 * @author Diego Moran
 * @version: 1.0
 */
@Service
@Transactional
public class GeneralService implements IService {
	
	@Autowired
	ICategoryRepository repoCategory;
	
	@Autowired
	IProductRepository repoProduct;
	
	@Autowired
	IUserProgramRepository repoUser;
	
	@Autowired
	IProductByUserCartRepository repoProductCart;
	
	@Autowired
	ISaleRepository repoSale;
	
	@Autowired
	IPurchaseRepository repoPurchase;

	/**
	 * Returns all types products
	 * @return Set of types
	 */
	@Override 
	public Flux<String> getAllTypes() {
		return repoProduct.findAll().map(p -> p.getType()).distinct();
	}

	/**
	 * Returns specific types products
	 * @return Set of types
	 * @param categoryId
	 */
	@Override 
	public Flux<String> getTypesByCategory(String categoryId) {
		return repoProduct.findByCategoryId(categoryId)
				.map(p -> p.getType()).distinct();
	}

	/**
	 * Returns a boolean value if can delete a category by id
	 * @return ResponseEntity
	 * @param id
	 */
	@Override 
	public Mono<ResponseEntity<Void>> deleteCategoryById(String id) {
			return repoCategory.deleteById(id)
					.then(Mono.just(ResponseEntity.noContent().build()));
	}

	/**
	 * Returns a boolean value if can delete a product by id
	 * @return ResponseEntity
	 * @param id
	 */
	@Override 
	public Mono<ResponseEntity<Boolean>> deleteProductById(String id) {
			return repoProductCart.deleteByProductId(id)
					.map(p -> ResponseEntity.ok().body(true))
					.doOnSuccess(p -> repoProduct.deleteById(id))
					.defaultIfEmpty(ResponseEntity.ok().body(false));
	}
	
	/**
	 * Returns a boolean value if can delete all product by category id
	 * @return ResponseEntity
	 * @param id
	 */
	@Override 
	public Mono<Boolean> deleteProductsFromCategory(String id) {
		try {
			repoProduct.findByCategoryId(id)
			.flatMap(p -> repoProductCart.deleteByProductId(p.getId()));
			repoProduct.deleteByCategoryId(id);
			return Mono.just(true);
		} catch (Exception e) {
			System.out.println("Fallo borrar productos de una categoria");
			return Mono.just(false);
		}
	}

	/**
	 * Returns a boolean value if can delete a user by id
	 * @return ResponseEntity
	 * @param id
	 */
	@Override 
	public Mono<ResponseEntity<Boolean>> deleteUserById(String id) {
			return repoUser.deleteById(id)
					.then(repoUser.findById(id).map(u -> repoProductCart.deleteByUserName(u.getUserName()))
							.map(p -> ResponseEntity.ok().body(true))
							.defaultIfEmpty(ResponseEntity.ok().body(false)));
	}

	/**
	 * Returns all Categories
	 * @return List of Categories
	 */
	@Override 
	public Flux<Category> findAllCategories() {
		return repoCategory.findAll();
	}

	/**
	 * Returns all products
	 * @return List of products
	 */
	@Override 
	public Flux<Product> findAllProducts() {
		return repoProduct.findAll();
	}

	/**
	 * Returns all users
	 * @return List of users
	 */
	@Override 
	public Flux<UserProgram> findAllUsers() {
		return repoUser.findByOrderByUserName();
	}

	/**
	 * Returns a Category by id
	 * @return Category
	 * @param id
	 */
	@Override 
	public Mono<Category> findCategoryById(String id) {
		return repoCategory.findById(id);
	}

	/**
	 * Returns a product by id
	 * @return Product
	 * @param id
	 */
	@Override 
	public Mono<Product> findProductById(String id) {
		return repoProduct.findById(id);
	}

	/**
	 * Returns a userProgram by userName
	 * @return UserProgram
	 * @param userName
	 */
	@Override 
	public Mono<UserProgram> findUserByUserName(String userName) {
		return repoUser.findByUserName(userName);
	}

	/**
	 * Returns a list of products by categoryId
	 * @return Product list
	 * @param id
	 */
	@Override 
	public Flux<Product> findProductsByCategoryId(String id) {
		return repoProduct.findByCategoryId(id);
	}
	
	/**
	 * Returns a product by categoryId and productId
	 * @return Product
	 * @param categoryId
	 * @param id
	 */
	@Override 
	public Mono<Product> findProductsByCategoryAndProductId(String categoryId,  String id) {
		return repoProduct.findByCategoryIdAndId(categoryId, id);
	}

	/**
	 * Returns a category which will be deleted
	 * @return Category
	 * @param category
	 */
	@Override 
	public Mono<Category> saveUpdateCategory(Category category) {
		return repoCategory.save(category);
	}

	/**
	 * Returns a product which will be deleted
	 * @return Product
	 * @param product
	 */
	@Override 
	public Mono<Product> saveUpdateProduct(Product product) {
		return repoProduct.save(product);
	}

	/**
	 * Returns a userProgram which will be deleted
	 * @return UserProgram
	 * @param user
	 */
	@Override
	public Mono<UserProgram> saveUpdateUser(UserProgram user) {
		return repoUser.save(user);
	}

	/**
	 * Returns a category list by parameters
	 * @return Category list
	 * @param name
	 */
	@Override 
	public Flux<Category> getCategoriesByParams(String name, String type) {
		return repoCategory.searchByParam(name, type);
	}

	/**
	 * Returns a product list by parameters
	 * @return Product list
	 * @param name
	 * @param numberExtern
	 * @param type
	 * @param categoryId
	 */
	@Override 
	public Flux<Product> getProductByParamsId(String name, String numberExtern, String type, String categoryId) {
		return repoProduct.searchByThreeParams(name, numberExtern, type, categoryId);
	}
	
	/**
	 * Returns a userProgram list by parameters
	 * @return userProgram list
	 * @param userName
	 * @param role
	 */
	@Override
	public Flux<UserProgram> getUsersByParams(String userName, String role) {
		return repoUser.searchByTwoParams(userName, role);
	}

	/**
	 * Verify if exists a user by userName
	 * @return Boolean
	 * @param userName
	 */
	@Override
	public Mono<Boolean> existsUserByName(String userName) {
		return Mono.just(repoUser.existsUserProgramByUserName(userName));
	}
	
	/**
	 * Verify if exists a user by email
	 * @return Boolean
	 * @param email
	 */
	@Override
	public Mono<Boolean> existsUserByEmail(String email) {
		return Mono.just(repoUser.existsUserProgramByEmail(email));
	}	

	/**
	 * Get category by type
	 * @return List
	 * @param type
	 */
	@Override
	public Flux<Category> findByType(String type) {
		return repoCategory.findByType(type);
	}

	/**
	 * Delete all productCart
	 * @return ResponseEntity
	 * @param userName
	 */
	@Override
	public Mono<ResponseEntity<?>> deleteAllProductsCartByUserName(String userName) {
		try {
			return repoProductCart.deleteByUserName(userName)
			.flatMap(p -> Mono.just(ResponseEntity.ok().body(true)));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.ok().body(false));
		}
	}

	/**
	 * Delete productCart
	 * @return ResponseEntity
	 * @param userName
	 * @param productId
	 */
	@Override
	public Mono<ResponseEntity<Void>> deleteProductByUserCart(String userName, String productId) {
			String id = repoProductCart.findByUserNameAndProductId(userName, productId).get(0).getId();
			return repoProductCart.deleteById(id)
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
	}
	
	/**
	 * Update categoryName from all products by categoryName
	 * @param categoryNameNew
	 * @param categoryNameOld
	 */
	@Override
	public Mono<ResponseEntity<?>> updateCategoryNameNewProduct(String categoryNameNew, String categoryNameOld) {
		try {
			repoProduct.setCatecoryNameNew(categoryNameNew, categoryNameOld);
			return Mono.just(ResponseEntity.ok().body(true));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.ok().body(false));
		}
	}

	/**
	 * Get productsCart by userName
	 * @return List
	 * @param userName
	 */
	@Override
	public Flux<Product> getProductsByUserCart(String userName) {
		List<Product> productsList = new ArrayList<>();
		repoProductCart.findByUserName(userName)
		.map(pc -> repoProduct.findById(pc.getProductId())
				.map(p -> productsList.add(p)));
		
		productsList.forEach(p -> p.setAmount(Collections.frequency(productsList, p)));
		return Flux.fromIterable(productsList).distinct();
	}

	/**
	 * Add productCart
	 * @return ResponseEntity
	 * @param productByUserCart
	 */
	@Override
	public Mono<ResponseEntity<?>> addProductByUserCart(ProductByUserCart productByUserCart) {
		try {
			return repoProductCart.save(productByUserCart)
			.then(Mono.just(ResponseEntity.ok().body(true)));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.ok().body(false));
		}
	}

	/**
	 * Get products
	 * @return List
	 * @param userName
	 */
	@Override
	public Flux<ProductByUserCart> getProductsWithoutAmounts(String userName) {
		return repoProductCart.findByUserName(userName);
	}

	/**
	 * Delete all products By productId
	 * @return ResponseEntity
	 * @param userName
	 * @param productId
	 */
	@Override
	public Mono<ResponseEntity<?>> deleteAllProductsByProductId(String userName, String productId) {
		try {
			return repoProductCart.deleteByProductIdAndUserName(productId, userName)
			.then(Mono.just(ResponseEntity.ok().body(true)));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.ok().body(false));
		}
	}

	@Override
	public Mono<ResponseEntity<?>> updateUserNameNewOnProductCartList(String userNameNew, String userNameOld) {
		try {
			repoProductCart.setUserNameNew(userNameNew, userNameOld);
			return Mono.just(ResponseEntity.ok().body(true));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.ok().body(false));
		}
	}

	@Override
	public UserProgram findUserByEmail(String email) {
		return repoUser.findByEmail(email);
	}

	@Override
	public Flux<Purchase> getPurchasesFromUser(String userName) {
		return repoUser.findByUserName(userName).flatMapIterable(u -> u.getPurchases());
	}

	@Override
	public Flux<Sale> getSales() {
		return repoSale.findAll();
	}

	@Override
	public Mono<ResponseEntity<Void>> deleteSaleById(String id) {
		return repoSale.deleteById(id)
		.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
	}

	@Override
	public Mono<Sale> saveUpdateSale(Sale sale) {
		if(sale.isDelivered()) {
			sale.setDelivered(false);
		} else {
			sale.setDelivered(true);
		}
		return repoSale.save(sale);
	}

	@Override
	public Flux<Sale> getSalesByParams(String date, String userName, String userMail) {
		return repoSale.searchByParams(date, userName, userMail);
	}

	@Override
	public Mono<ResponseEntity<?>> updateUserNameFromSales(String userNameNew, String userNameOld) {
		try {
			repoSale.setUserNameNew(userNameNew, userNameOld);
			return Mono.just(ResponseEntity.ok().body(true));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.ok().body(false));
		}
	}

	@Override
	public Mono<Sale> findSaleBYId(String id) {
		return repoSale.findById(id);
	}

	@Override
	public Flux<Sale> findByDelivered(boolean delivered) {
		return repoSale.findByDelivered(delivered);
	}

	@Override
	public Flux<Float> getTotalMonthsByYear(String year) {
		List<Float> totals = new ArrayList<>(12);
		return repoSale.findAll()
			.filter(s -> s.getDate_approved().startsWith(year))
			.flatMap(s -> {
				int indexMonth = LocalDate.parse(s.getDate_approved().split("T")[0]).getMonthValue() - 1;
				totals.set(indexMonth, totals.get(indexMonth) + s.getTotal());
				return Flux.fromIterable(totals);
			});
	}
	
}

	

