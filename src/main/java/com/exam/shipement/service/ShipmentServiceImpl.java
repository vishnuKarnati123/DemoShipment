package com.exam.shipement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.shipement.dto.OrderDetailsDto;
import com.exam.shipement.entities.OrderDetails;
import com.exam.shipement.entities.Product;
import com.exam.shipement.exception.ShipmentException;
import com.exam.shipement.repository.OrderRepository;
import com.exam.shipement.repository.ProductRepository;


@Service
public class ShipmentServiceImpl implements ShipmentService{

	@Autowired
	OrderRepository orderRepository;
	
	
	@Autowired 
	ProductRepository productRepository;
	
	@Autowired
	ModelMapper mapper;
	
	Logger log=LoggerFactory.getLogger(ShipmentServiceImpl.class);
	
	public ResponseEntity<String> getShipmentStatus(int orderId) {
		
		try {
			log.info("Entered in getShipmentStatus Method in service class");
			log.debug("OrderId :{}",orderId);
			Optional<OrderDetails> orderDetails=orderRepository.findById(orderId);
				int todayDate=LocalDate.now().getDayOfMonth();
				int deliveryDate=orderDetails.get().getDeliverydate().getDayOfMonth();
				int remainingDays=deliveryDate-todayDate;
				String status=null;
				if(remainingDays==0) 
				{
					  
					status="Hii your orderid: "+orderDetails.get().getOrderid() +" \n will  be delivered today: "+orderDetails.get().getDeliverydate();
					orderDetails.get().setShipmentstatus("By Today");
					orderDetails.get().setShipmentDate(orderDetails.get().getDeliverydate());
					orderRepository.save(orderDetails.get());
					log.debug("Shipment Status response:{}",status);
					log.info("Exited  getShipmentStatus Method in service class");
					 return new ResponseEntity<String>(status,HttpStatus.OK);
					 
				} 
				else if(remainingDays>=1)
				{
					orderDetails.get().setShipmentDate(orderDetails.get().getDeliverydate().plusDays(2));
					orderDetails.get().setShipmentstatus("In Progress");
					orderRepository.save(orderDetails.get());
					status="Hii your orderid: "+orderDetails.get().getOrderid() +" \n will  be delivered on: "+orderDetails.get().getShipmentDate();
					log.debug("Shipment Status response:{}",status);
					log.info("Exited  getShipmentStatus Method in service class");
					return new ResponseEntity<String>(status,HttpStatus.OK);
				}
				else
				{
					orderDetails.get().setShipmentstatus("Done");
					orderRepository.save(orderDetails.get());
					status="Hii your orderid: "+orderDetails.get().getOrderid() +" \n delivery done on: "+orderDetails.get().getDeliverydate();
					log.debug("Shipment Status response:{}",status);
					log.info("Exited  getShipmentStatus Method in service class");
					return new ResponseEntity<String>(status,HttpStatus.OK);
					
				}
				
		
		}
			catch(NoSuchElementException exception)
			{
				log.error("ordernot found in getShipmentStatus Method in service class");
				throw new NoSuchElementException("shipment orderid not present");
			}
		catch (Exception exception) {
			// TODO Auto-generated catch block
			log.error("Error in getShipmentStatus Method in service class");
			throw new ShipmentException("Something wrong int getshipment method will get back to you");
		}
		
	}

    public String addShipmentStatus(OrderDetailsDto orderDetailsDto)	
    {
    

		try {
			log.info("Entered in addShipmentStatus Method in service class");
			log.debug("OrderId :{}",orderDetailsDto.getOrderid());
			OrderDetails order = mapper.map(orderDetailsDto,OrderDetails.class);
			order=orderRepository.save(order);
			
			List<Product> products = order.getProducts();
			for(Product product:products)
			{
				Product p=new Product();
				p.setProductid(product.getProductid());
				p.setProductname(product.getProductname());
				p.setPrice(product.getPrice());
				p.setCategory(product.getCategory());
				p.setOrderDetails(order); 
				System.out.println(productRepository.save(p));		
				
				
			}
			log.info("Shipment status added");
			return "Order Added";
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			log.error("Error in add Shipment method in service layer");
			throw new ShipmentException("Something wrong to create shipment will get back to you");
		}
    }
    
    
	public String updateShipmentStatus(OrderDetailsDto order) {
		
		try {
			log.info("Entered in updateShipmentStatus Method in service class");
			log.debug("OrderId :{}",order.getOrderid());
			OrderDetails orderDetails=mapper.map(order,OrderDetails.class);
			int orderid=orderDetails.getOrderid();
			Optional<OrderDetails> optionalorderDetails=orderRepository.findById(orderid);
				optionalorderDetails.get().setOrderdate(orderDetails.getOrderdate());
				optionalorderDetails.get().setDeliverydate(orderDetails.getDeliverydate());
				optionalorderDetails.get().setShipmentstatus(orderDetails.getShipmentstatus());
				optionalorderDetails.get().setShipmentDate(orderDetails.getShipmentDate());
				optionalorderDetails.get().setStatus(orderDetails.getStatus());
				orderRepository.save(optionalorderDetails.get());
				 for (Product prod : orderDetails.getProducts())
				 {
					 Optional<Product> prd = Optional.ofNullable(productRepository.findByProductname(prod.getProductname())); 
					 if (prd.isPresent()) 
					 { 
						 System.out.println(prd.get());
					     prd.get().setProductname(prod.getProductname());
					    prd.get().setCategory(prod.getCategory());
					    prd.get().setPrice(prod.getPrice()); 	
					    prd.get().setOrderDetails(orderDetails);
					   System.out.println(productRepository.save(prd.get()));
					    
					  }
					 else
					 {
						 prod.setOrderDetails(orderDetails);
						 
						System.out.println(productRepository.save(prod));  
					 }
				}
				 log.info("Shipment status updated");
			     return "Shipment Updated";
			
		}
			catch(NoSuchElementException exception)
			{
				log.error("Orderid not present");
				throw new NoSuchElementException("update request shipment orderid not present");
			}
		catch (Exception exception) {
			// TODO Auto-generated catch block
			log.error("Error in update Shipment method in service layer");
			throw new ShipmentException("Something wrong in updating shipment will get back to you");
		}
	}


	@Override
	public String deleteStatus(int orderId)
	{
		try {
			log.info("Entered in deleteShipmentStatus Method in service class");
			log.debug("OrderId :{}",orderId);
			Optional<OrderDetails> optional = orderRepository.findById(orderId);
			
				OrderDetails orderDetails = optional.get();
				List<Product> product = orderDetails.getProducts();
				for (Product p : product) {
					if (p.getPrice() == 1000) {
						orderDetails.setStatus("Deleted");
						orderRepository.save(orderDetails);
						break;
					}
				}
				log.info("Delted Shipment status ");
				return "order status deleted";
			
		}catch(NoSuchElementException exception)
			{
			log.error("orderId not present");
				throw new NoSuchElementException("delete request shipment orderid not present");
			}
		 catch (Exception exception) {
			// TODO Auto-generated catch block
			 log.error("Error in delete Shipment method in service layer");
			throw new ShipmentException("Something wrong in deleting shipment will get back to you");
		}
	}

	

	


	
}
