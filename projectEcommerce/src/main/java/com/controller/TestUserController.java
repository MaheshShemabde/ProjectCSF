package com.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.model.Cart;
import com.model.Login;
import com.model.Product;
import com.model.User;
import com.service.UserServiceImpl;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/user")
public class TestUserController {
	
	@Autowired
	UserServiceImpl userService;
	
	@PostMapping("/createUser")
	public User createUser(@RequestBody User user)
	{
		System.out.println("Entered in user controller");
		return userService.addUser(user);
	}
	
	@GetMapping("/getUser/{id}")
	ResponseEntity<User> getUser(@PathVariable("id") int id)
	{
		User user = userService.getUserById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/getProduct/{id}")
	ResponseEntity<Product> getProduct(@PathVariable("id") int id)
	{
		Product product = userService.getProductById(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@GetMapping("/getUserByEmail/{email}")
	ResponseEntity<User> getUserByEmail(@PathVariable("email") String email)
	{
		User user = userService.getUserByEmail(email);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping(value="/createProduct", consumes="multipart/form-data")
	ResponseEntity<?> createProduct(@RequestPart("file") MultipartFile file, @RequestPart("product") Product product) throws IOException
	{
		Product product1 = new Product();
		if(file!=null)
		{
			System.out.println(file.getOriginalFilename());
			product.setImagePath(file.getOriginalFilename());
			product.setPicByte(file.getBytes());
			product1 = userService.addProduct(product);
			return new ResponseEntity<Product>(product1, HttpStatus.OK);
		}
		else
		{
			System.out.println("File is null");
			return new ResponseEntity<String>("File is Null", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping(value="/updateProduct")
	ResponseEntity<?> updateProduct(@RequestBody Product product) throws IOException
	{
		Product product1 = new Product();
		product1 = userService.updateProduct(product);
		return new ResponseEntity<Product>(product1, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllProducts")
	ResponseEntity<List<Product>> getAllProducts()
	{
		List<Product> products = userService.getAllProducts();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("/getProductsByUser/{id}")
	ResponseEntity<List<Product>> getProductsByUser(@PathVariable("id") int id)
	{
		List<Product> products = userService.getProductsByUserId(id);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@PostMapping(value="/uploadImage", consumes="multipart/form-data")
	public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file, Product product) throws IOException
	{
		
		if(file!=null)
		{
			System.out.println(file.getOriginalFilename());
			String fileName = userService.uploadImage(file);
			return new ResponseEntity<String>(fileName, HttpStatus.OK);
		}
		else
		{
			System.out.println("File is null");
			return new ResponseEntity<String>("File is Null", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Integer> login(@RequestBody Login login)
	{
		int result = userService.login(login);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@PostMapping("/addToCart")
	Cart addCart(@RequestBody Cart cart)
	{
		return userService.addToCart(cart);
	}
	
	@GetMapping("/getCartByUser/{id}")
	ResponseEntity<List<Cart>> getCartByUser(@PathVariable("id") int id)
	{
		List<Cart> carts = userService.getFromCart(id);
		return new ResponseEntity<List<Cart>>(carts, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCart/{id}")
	void deleteCart(@PathVariable("id") int id)
	{
		userService.deleteCart(id);
	}
}

