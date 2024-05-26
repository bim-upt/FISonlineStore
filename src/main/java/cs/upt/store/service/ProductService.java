package cs.upt.store.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import cs.upt.store.DTO.HashedUserDTO;
import cs.upt.store.exceptions.UserIsNotASellerException;
import cs.upt.store.model.Product;
import cs.upt.store.repository.ProductRepository;
import jakarta.validation.constraints.Null;



@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    public Product insertProduct(Product product) throws NoSuchAlgorithmException, NameNotFoundException, UserIsNotASellerException, DataIntegrityViolationException{
        try{
            HashedUserDTO seller = userService.getUser(product.getSeller());
            if(seller.getType() == 0){
                throw new UserIsNotASellerException("Not a seller");
            }
            List<Product> sellerProducts = productRepository.findBySeller(product.getSeller());
            for (int i = 0; i < sellerProducts.size(); i++) {
                //System.out.println(sellerProducts.get(i).getName());
                if(sellerProducts.get(i).getCode().equals(product.getCode())){
                    throw new DataIntegrityViolationException("Seller already has a product with this code");
                }
            }
            return productRepository.insert(product);
        }catch(NameNotFoundException e){
            throw e;
        }catch(Exception e){
            throw e;
        }
    }

    public Product modifyProduct(Product product) throws NotFoundException{
        List<Product> sellerProducts = productRepository.findBySeller(product.getSeller());
        Product found = null;
        for (int i = 0; i < sellerProducts.size(); i++) {
            if(sellerProducts.get(i).getCode().equals(product.getCode())){
                found = sellerProducts.get(i);
                break;
            }
        }
        if(found == null){
            throw new NotFoundException();
        }
        found.setImgs(product.getImgs());
        found.setName(product.getName());
        return productRepository.save(found);
    }
}
