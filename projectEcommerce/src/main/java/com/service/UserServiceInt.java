package com.service;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.model.Cart;
import com.model.Login;
import com.model.Product;
import com.model.User;

public interface UserServiceInt {
	public User addUser(User user);
	
	public User getUserByEmail(String email);
	
	
	public User getUserById(int id);
	
	
	public Product getProductById(int id);


	public Product addProduct(Product product);
	
	
	public List<Product> getAllProducts();
	
	public List<Product> getProductsByUserId(int id);
	
	public Product updateProduct(Product product);
	
	
	public void deleteProduct(int id);
	

	public String uploadImage(MultipartFile image) throws IOException;
	
	public int login(Login login);
	
	public Cart addToCart(Cart cart);
	
	
	public List<Cart> getFromCart(int userId);
	
	
	public void deleteCart(int id);
	
}
