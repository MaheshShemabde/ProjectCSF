package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dao.UserRepository;
import com.dao.ProductRepository;
import com.dao.CartRepository;
import com.model.Cart;
import com.model.Login;
import com.model.Product;
import com.model.User;

@Service
public class UserServiceImpl implements UserServiceInt{
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	public User addUser(User user)
	{
		return userRepo.save(user);
	}
	
	public User getUserByEmail(String email)
	{
		return userRepo.findUserByEmail(email);
	}
	
	public User getUserById(int id)
	{
		User user = userRepo.findById(id).get();
		return user;
	}
	
	public Product getProductById(int id)
	{
		Product product = productRepo.findById(id).get();
		return product;
	}

	public Product addProduct(Product product)
	{
		return productRepo.save(product);
	}
	
	public List<Product> getAllProducts()
	{
		return productRepo.findAll();
	}
	
	public List<Product> getProductsByUserId(int id)
	{
		return productRepo.findProductByUserId(id);
	}
	
	public Product updateProduct(Product product)
	{
		Product productDB = productRepo.findById(product.getProductId()).get();
		productDB.setCategory(product.getCategory());
		productDB.setPrice(product.getPrice());
		productDB.setProductName(product.getProductName());
		System.out.println(productDB);
		return productRepo.saveAndFlush(productDB);		
	}
	
	public void deleteProduct(int id)
	{
		productRepo.deleteById(id);
	}

	public String uploadImage(MultipartFile image) throws IOException {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		Path uploadPath = Paths.get("C:/Users/dhkadu/Documents/Practice/Angular/Ecommerce/src/assets/Images/");
		try(InputStream inputStream = image.getInputStream())
		{
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}
		catch(IOException ioe)
		{
			throw new IOException("Could not save image");
		}
		return fileName;
	}
	
	public int login(Login login)
	{
		int result = 0;
		User user = userRepo.findUserByEmail(login.getUserName());
		if(user!=null)
		{
			if(user.getPassword().equals(login.getPassword()))
			{
				result = 1;
			}
			else
				result = 2;
		}
		return result;
	}
	
	public Cart addToCart(Cart cart)
	{
		return cartRepo.save(cart);
	}
	
	public List<Cart> getFromCart(int userId)
	{
		return cartRepo.findCartByUserId(userId);
	}
	
	public void deleteCart(int id)
	{
		cartRepo.deleteById(id);
	}
	
}

