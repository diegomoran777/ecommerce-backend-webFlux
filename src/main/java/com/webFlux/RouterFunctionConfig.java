package com.webFlux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webFlux.handler.CategoryHandler;
import com.webFlux.handler.ImageHandler;
import com.webFlux.handler.ProductCartHandler;
import com.webFlux.handler.ProductHandler;
import com.webFlux.handler.SaleHandler;
import com.webFlux.handler.UserHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> routesProductCart(ProductCartHandler handler) {
		return route(POST("api/cartuser/v2/cart-products"), handler::getProductByCartUser)
				.andRoute(POST("api/cartuser/v2/cart-products-length"), handler::getProductsWithoutAmounts)
				.andRoute(POST("api/cartuser/v2/delete-product"), handler::deleteProductFromCart)
				.andRoute(POST("api/cartuser/v2/delete-productsid"), handler::deleteAllProductsByProductIdFromCart)
				.andRoute(POST("api/cartuser/v2/delete-products"), handler::deleteProductsFromCart)
				.andRoute(POST("api/cartuser/v2/add-product"), handler::addProductsToCart)
				.andRoute(POST("api/cartuser/v2/update-new-username"), handler::addProductsToCart);
	} 
	
	@Bean
	public RouterFunction<ServerResponse> routesCategory(CategoryHandler handler) {
		return route(POST("api/category/v2/categories"), handler::getCategories)
				.andRoute(GET("api/category/v2/categories-by-type/{type}"), handler::getCategoriesByType)
				.andRoute(POST("api/category/v2/getcategoryid"), handler::getCategoryById)
				.andRoute(POST("api/category/v2/get-category"), handler::getCategory)
				.andRoute(DELETE("api/category/v2/category-products/{id}"), handler::deleteCategoryProductsById)
				.andRoute(DELETE("api/category/v2/category-images/{id}"), handler::deleteCategoryImagesById)
				.andRoute(POST("api/category/v2/save-update"), handler::saveUpdateCategory)
				.andRoute(POST("api/category/v2/search-by-params"), handler::getCategoriesByParams);
	}
	
	@Bean
	public RouterFunction<ServerResponse> routesImage(ImageHandler handler) {
		return route(GET("api/image/v2/images"), handler::getImages)
				.andRoute(POST("api/image/v2/getimageid"), handler::getImageById)
				.andRoute(DELETE("api/image/v2/{id}"), handler::deleteImageById)
				.andRoute(POST("api/image/v2/bycategoryid"), handler::getImagesByCategoryID)
				.andRoute(POST("api/image/v2/bycategory-imageid"), handler::getImagesByCategoryAndImageId)
				.andRoute(POST("api/image/v2/save-update"), handler::saveUpdateImage)
				.andRoute(GET("api/image/v2/types"), handler::getAllTypes)
				.andRoute(GET("api/image/v2/types/{id}"), handler::getTypesById)
				.andRoute(POST("api/image/v2/search-by-paramsid"), handler::getImagesByParamsId)
				.andRoute(POST("api/image/v2/update-new-categoryname"), handler::updateNewCategoryNameOnLists);
				
	}
	
	@Bean
	public RouterFunction<ServerResponse> routesProduct(ProductHandler handler) {
		return route(GET("api/product/v2/products"), handler::getProducts)
				.andRoute(POST("api/product/v2/getproductid"), handler::getProductById)
				.andRoute(DELETE("api/product/v2/{id}"), handler::deleteProductById)
				.andRoute(POST("api/product/v2/bycategoryid"), handler::getProductsByCategoryID)
				.andRoute(POST("api/product/v2/bycategory-productid"), handler::getProductsByCategoryAndProductId)
				.andRoute(POST("api/product/v2/save-update"), handler::saveUpdateProduct)
				.andRoute(GET("api/product/v2/types"), handler::getAllTypes)
				.andRoute(GET("api/product/v2/types/{id}"), handler::getTypesById)
				.andRoute(POST("api/product/v2/search-by-paramsid"), handler::getProductsByParamsId)
				.andRoute(POST("api/product/v2/update-new-categoryname"), handler::updateNewCategoryNameOnLists);
	}
	
	@Bean
	public RouterFunction<ServerResponse> routesProduct(SaleHandler handler) {
		return route(GET("api/sale/v2/sales"), handler::getSales)
				.andRoute(POST("api/sale/v2/get-sale"), handler::getSaleById)
				.andRoute(DELETE("api/sale/v2/{id}"), handler::deleteSaleById)
				.andRoute(POST("api/sale/v2/save-update"), handler::saveUpdateSale)
				.andRoute(POST("api/sale/v2/search-by-params"), handler::getSalesByParams)
				.andRoute(POST("api/sale/v2/update-new-username"), handler::updateNewUserNameOnLists)
				.andRoute(POST("api/sale/v2/get-sales-deliver"), handler::getSalesByDelivered)
				.andRoute(POST("api/sale/v2/get-totals-year"), handler::getTotalsMonthByYear);
	}
	
	@Bean
	public RouterFunction<ServerResponse> routesUser(UserHandler handler) {
		return route(GET("api/user/v2/users"), handler::getUsers)
				.andRoute(POST("api/user/v2/getuser"), handler::getUserByUserName)
				.andRoute(POST("api/user/v2/exists"), handler::existsUser)
				.andRoute(POST("api/user/v2/exists-email"), handler::existsUserByEmail)
				.andRoute(DELETE("api/user/v2/{id}"), handler::deleteUserById)
				.andRoute(POST("api/user/v2/save-update"), handler::saveUpdateUser)
				.andRoute(POST("api/user/v2/search-by-params"), handler::getUsersByParams)
				.andRoute(POST("api/user/v2/purchases"), handler::getPurchassesFromUser)
				.andRoute(POST("api/user/v2/search-by-date"), handler::getPurchasesByParams);
	}
}
